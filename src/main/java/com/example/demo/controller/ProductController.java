package com.example.demo.controller;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Product;
import com.example.demo.model.DTO.*;
import com.example.demo.model.request.*;
import com.example.demo.model.response.ResponseData;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1.0/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/filtered-statistics")
    public List<BestSellingProductDto> getFilteredStatistics(
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "month", required = false) Integer month) {

        // Thực hiện query với điều kiện lọc
        return productRepository.findByProductNameAndMonth(productName, month);
    }

    @GetMapping("/monthly-revenue")
    public List<RevenueData> getMonthlyRevenue() {
        List<Object[]> results = productRepository.findMonthlyRevenue();
        List<RevenueData> revenueDataList = new ArrayList<>();

        for (Object[] result : results) {
            Integer month = (result[0] != null) ? ((Number) result[0]).intValue() : 0;
            Double totalAmount = (result[1] != null) ? ((Number) result[1]).doubleValue() : 0.0;
            if (month != 0) {  // Loại bỏ tháng 0
                revenueDataList.add(new RevenueData(month, totalAmount));
            }
        }

        return revenueDataList;
    }


    @GetMapping("/best-selling-products")
    public List<BestSellingProductDto> getBestSellingProducts(@RequestParam(value = "currentMonth", required = false) int currentMonth) {
        return productRepository.findBestSellingProducts(currentMonth);
    }
    @GetMapping("/export-best-selling")
    public void exportBestSellingProducts(HttpServletResponse response, @RequestParam(value = "currentMonth", required = false) Optional<Integer> currentMonth) throws IOException {
        int month = currentMonth.orElse(0); // Sử dụng giá trị mặc định hoặc xử lý nếu không có giá trị hợp lệ

        if (month > 0 && month <= 12) {
            List<BestSellingProductDto> bestSellingProducts = productRepository.findBestSellingProducts(month);

            // Lấy dữ liệu doanh thu và chuyển đổi từ List<Object[]> thành List<RevenueData>
            List<Object[]> revenueResults = productRepository.findMonthlyRevenue();
            List<RevenueData> revenueDataList = new ArrayList<>();
            for (Object[] result : revenueResults) {
                int monthResult = ((Number) result[0]).intValue(); // Chuyển đổi giá trị tháng từ Object[] thành int
                double totalAmountResult = ((Number) result[1]).doubleValue(); // Chuyển đổi giá trị doanh thu từ Object[] thành double
                revenueDataList.add(new RevenueData(monthResult, totalAmountResult)); // Tạo đối tượng RevenueData và thêm vào danh sách
            }

            Workbook workbook = new XSSFWorkbook();

            // Tạo sheet cho sản phẩm bán chạy
            Sheet bestSellingSheet = workbook.createSheet("Best Selling Products");
            createHeaderRow(bestSellingSheet, new String[]{"Product Name", "Total Sold"});
            addDataRows(bestSellingSheet, bestSellingProducts);

            // Tạo sheet cho doanh thu
            Sheet revenueSheet = workbook.createSheet("Monthly Revenue");
            createHeaderRow(revenueSheet, new String[]{"Month", "Total Revenue"});
            addDataRows(revenueSheet, revenueDataList);

            // Thiết lập header cho file Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=best_selling_products_and_revenue.xlsx");

            // Ghi dữ liệu ra response
            workbook.write(response.getOutputStream());
            workbook.close();
        } else {
            // Xử lý khi month không hợp lệ
            throw new IllegalArgumentException("Tháng không hợp lệ: " + month);
        }
    }

    private void createHeaderRow(Sheet sheet, String[] headers) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        Font headerFont = sheet.getWorkbook().createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void addDataRows(Sheet sheet, List<?> dataList) {
        CellStyle dataStyle = sheet.getWorkbook().createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        int rowNum = 1;
        for (Object data : dataList) {
            Row row = sheet.createRow(rowNum++);
            if (data instanceof BestSellingProductDto) {
                BestSellingProductDto product = (BestSellingProductDto) data;
                row.createCell(0).setCellValue(product.getProductName());
                row.createCell(1).setCellValue(product.getTotalSold());
            } else if (data instanceof RevenueData) {
                RevenueData revenue = (RevenueData) data;
                row.createCell(0).setCellValue(revenue.getMonth());
                row.createCell(1).setCellValue(revenue.getTotalAmount());
            }
            // Apply style to cells
            for (Cell cell : row) {
                cell.setCellStyle(dataStyle);
            }
        }
    }


//    @GetMapping("/export-best-selling")
//    public void exportBestSellingProducts(HttpServletResponse response, @RequestParam(value = "currentMonth", required = false) Optional<Integer> currentMonth) throws IOException {
//        System.out.println("---------------------------------------" + currentMonth);
//        int month = currentMonth.orElse(0); // Sử dụng giá trị mặc định hoặc xử lý nếu không có giá trị hợp lệ
//
//        if (month > 0 && month <= 12) {
//            List<BestSellingProductDto> bestSellingProducts = productRepository.findBestSellingProducts(month);
//            List<Object[]> revenueResults = productRepository.findMonthlyRevenue();
//            List<RevenueData> revenueDataList = new ArrayList<>();
//            for (Object[] result : revenueResults) {
//                int monthResult = ((Number) result[0]).intValue(); // Chuyển đổi giá trị tháng từ Object[] thành int
//                double totalAmountResult = ((Number) result[1]).doubleValue(); // Chuyển đổi giá trị doanh thu từ Object[] thành double
//                revenueDataList.add(new RevenueData(monthResult, totalAmountResult)); // Tạo đối tượng RevenueData và thêm vào danh sách
//            }
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Best Selling Products");
//
//            // Tạo tiêu đề cột
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("Product Name");
//            header.createCell(1).setCellValue("Total Sold");
//
//            // Điền dữ liệu vào bảng
//            int rowNum = 1;
//            for (BestSellingProductDto product : bestSellingProducts) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(product.getProductName());
//                row.createCell(1).setCellValue(product.getTotalSold());
//            }
//            Sheet revenueSheet = workbook.createSheet("Monthly Revenue");
//            Row revenueHeader = revenueSheet.createRow(0);
//            revenueHeader.createCell(0).setCellValue("Month");
//            revenueHeader.createCell(1).setCellValue("Total Revenue");
//
//            rowNum = 1;
//            for (RevenueData revenue : revenueDataList) {
//                Row row = revenueSheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(revenue.getMonth());
//                row.createCell(1).setCellValue(revenue.getTotalAmount());
//            }
//            // Thiết lập header cho file Excel
//            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//            response.setHeader("Content-Disposition", "attachment; filename=best_selling_products.xlsx");
//
//            // Ghi dữ liệu ra response
//            workbook.write(response.getOutputStream());
//            workbook.close();
//        } else {
//            // Xử lý khi month không hợp lệ
//            throw new IllegalArgumentException("Tháng không hợp lệ: " + month);
//        }
//    }


    @GetMapping("/top10")
    public List<ProductSalesDTO> getTop10Products() {
        return productService.getTop10Products();
    }

    @GetMapping("/statistical/all")
    public List<RevenueDTO> getStatistical() {
        return productService.getRevenueByDayMonthYear();
    }

    @GetMapping("/statistical/month-year")
    public List<RevenueDTO> getStatistical2() {
        return productService.getRevenueByDayMonthYear();
    }

    @GetMapping("/statistical/year")
    public List<RevenueDTO> getStatistical3() {
        return productService.getRevenueByDayMonthYear();
    }

    @PostMapping("/get-product")
    public ResponseEntity<ResponseData<Object>> getAllPro(@RequestBody ProductRequest request,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.getProducts(request, page, size)));
    }

    @PostMapping("/add-product")
    public ResponseEntity<?> add(@RequestBody AddProductRequest request) throws BusinessException {
        for (ProductDetailsRequest r:request.getProductDetails()){
            for(ImageRequest r2: r.getImages()){
                System.out.println(r2.getName());
            }
        }
        return ResponseEntity.ok(productService.saveProduct(request));
    }

    //Trang sản phẩm
    @PostMapping("/show")
    public ResponseEntity<ResponseData<Object>> show(@RequestBody GetProductRequest request,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.show(request, page, size)));
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<ResponseData<Object>> changeStatus(@RequestBody Product product) throws BusinessException {
        System.out.println("trạng thái gửi từ fe: "+product.getStatus() + "id: " + product.getId());
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.changeStatus(product)));
    }


    //Trang chủ
    @GetMapping("/findTop4New")
    public ResponseEntity<ResponseData<Object>> get() throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.getTop4NewestProducts()));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseData<Object>> getDetail(@PathVariable("id") Integer id) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.getProduct(id)));
    }

    @GetMapping("/top4-selling-products")
    public ResponseEntity<ResponseData<Object>> getTo4p4SellingProducts() throws  BusinessException{
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.getTop4BestSellingProducts()));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseData<Object>> update(@RequestBody Product id) throws BusinessException {
        return ResponseEntity.ok()
                .body(new ResponseData<>().success(productService.updateProduct(id)));
    }
}
