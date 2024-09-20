package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.Image;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductDetail;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.DTO.BestSellingProductDto;
import com.example.demo.model.DTO.ProductSalesDTO;
import com.example.demo.model.DTO.RevenueDTO;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.*;
import com.example.demo.model.response.ProductResponse;
import com.example.demo.model.response.ProductShowCustomResponse;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.SercurityUtils;
import com.example.demo.repository.ProductDetailsRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductSpecifications;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Autowired
    ImageServiceImpl imageService;

    @Override
    public List<ProductSalesDTO> getTop10Products() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Object[]> results = productRepository.findTop10Product(pageable);

        List<ProductSalesDTO> topProducts = new ArrayList<>();
        for (Object[] result : results) {
            Integer productId = (Integer) result[0]; // productId
            String productName = (String) result[1]; // productName
            Long salesCount = ((Number) result[2]).longValue(); // totalQuantity
            // Kiểm tra kiểu dữ liệu và chuyển đổi nếu cần
            BigDecimal totalRevenue;
            Object revenueObj = result[3]; // totalRevenue
            if (revenueObj instanceof BigDecimal) {
                totalRevenue = (BigDecimal) revenueObj;
            } else if (revenueObj instanceof Double) {
                totalRevenue = BigDecimal.valueOf((Double) revenueObj);
            } else {
                throw new ClassCastException("Unsupported type for totalRevenue");
            }

            topProducts.add(new ProductSalesDTO(productId, productName, salesCount, totalRevenue));
        }
        return topProducts;
    }
    @Override
    public List<RevenueDTO> getRevenueByDayMonthYear() {
        List<Object[]> results = productRepository.findRevenueByDayMonthYear();
        List<RevenueDTO> revenueList = new ArrayList<>();

        for (Object[] result : results) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            Integer day = (Integer) result[2];
            BigDecimal totalRevenue = BigDecimal.valueOf((Double) result[3]);;

            revenueList.add(new RevenueDTO(year, month, day, totalRevenue));
        }

        return revenueList;
    }

    @Override
    public List<RevenueDTO> getRevenueByMonthYear() {
        List<Object[]> results = productRepository.findRevenueByMonthYear();
        List<RevenueDTO> revenueList = new ArrayList<>();

        for (Object[] result : results) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            BigDecimal totalRevenue = BigDecimal.valueOf((Double) result[2]);

            revenueList.add(new RevenueDTO(year, month, totalRevenue));
        }

        return revenueList;
    }

    @Override
    public List<RevenueDTO> getRevenueByYear() {
        List<Object[]> results = productRepository.findRevenueByYear();
        List<RevenueDTO> revenueList = new ArrayList<>();

        for (Object[] result : results) {
            Integer year = (Integer) result[0];
            BigDecimal totalRevenue = BigDecimal.valueOf((Double) result[1]);

            revenueList.add(new RevenueDTO(year, totalRevenue));
        }

        return revenueList;
    }

    @Override
    public ProductResponse getProducts(ProductRequest request, int page, int size) throws BusinessException {
        List<Product> allPro = productRepository.findAll(Sort.by(Sort.Direction.fromString("desc"), "id"));
        if (CommonUtil.isNullOrEmpty(request.getName())) {
            request.setName(""); // nếu request.getName() không có giá trị thì gán bằng giá trị rỗng
        }
        List<Product> filteredPro = allPro.stream()
                .filter(product -> product.getProductName().contains(request.getName())).collect(Collectors.toList());
        int start = Math.min(page * size, filteredPro.size());
        int end = Math.min((page + 1) * size, filteredPro.size());
        List<Product> pagedPro = filteredPro.subList(start, end);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(pagedPro);

        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, filteredPro.size());
        productResponse.setPagination(paginationInfo);
        return productResponse;
    }

    @Override
    public AddProductRequest saveProduct(AddProductRequest productRequest) throws BusinessException {
        String user = SercurityUtils.getCurrentUser();
        String productName = productRequest.getProductName().trim().toLowerCase();
        if(productRepository.existsByProductNameIgnoreCase(productName)){
            throw new BusinessException(ErrorCode.PRODUCTNAME_ALREADY_EXISTS,"Tên sản phẩm đã tồn tại");
        }
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setBrand(productRequest.getBrand());
        product.setSole(productRequest.getSole());
        product.setStyle(productRequest.getStyle());
        product.setMaterial(productRequest.getMaterial());
        product.setCreatedBy(user);
        product.setCreatedDate(productRequest.getCreatedDate());
        product.setModifiedBy(user);
        product.setModifiedDate(productRequest.getModifiedDate());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.getStatus());
        productRepository.save(product);
        // lưu biến thể
        Product productLast = productRepository.getLastProductId();
        List<ProductDetailsRequest> detailsList = productRequest.getProductDetails();
        for(ProductDetailsRequest d : detailsList){
            ProductDetail details = new ProductDetail();
            details.setSize(d.getSize());
            details.setColor(d.getColor());
            details.setProduct(productLast);
            details.setQuantity(d.getQuantity());
            details.setPrice(d.getPrice());
            details.setDescription(d.getDescription());
            details.setStatus(d.getStatus());

            //lưu ảnh của biến thể
            ProductDetail detailTemp = productDetailsRepository.save(details);
            List<ImageRequest> imageList = d.getImages();
            for(ImageRequest i: imageList){
                Image image = new Image();
                image.setUrl("assets/img/product/"+i.getName());
                image.setProductDetail(detailTemp);
                imageService.saveImage(image);
            }
        }
        return productRequest;
    }

    @Override
    public Product updateProduct(Product product) throws BusinessException {
        return productRepository.save(product);
    }

    @Override
    public ProductResponse show(GetProductRequest request, int page, int size) throws BusinessException {

        Specification<Product> spec = Specification.where(ProductSpecifications.hasStatus(1));

        if (request.getBrands() != null && !request.getBrands().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasBrands(request.getBrands()));
        }
        if (request.getStyles()!= null && !request.getStyles().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasStyles(request.getStyles()));
        }
        if (request.getSoles()!= null && !request.getSoles().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasSoles(request.getSoles()));
        }
        if (request.getMaterials()!= null && !request.getMaterials().isEmpty()) {
            spec = spec.and(ProductSpecifications.hasMaterials(request.getMaterials()));
        }
        List<Product> listFilter = productRepository.findAll(spec);
        // Xử lý phân trang
        int start = Math.min(page * size, listFilter.size());
        int end = Math.min((page + 1) * size, listFilter.size());
        List<Product> pagePro = listFilter.subList(start, end);

        // Tạo đối tượng ProductResponse
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProducts(pagePro);

        // Tạo đối tượng PaginationInfo và gán cho productResponse
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(page, size, listFilter.size());
        productResponse.setPagination(paginationInfo);

        return productResponse;
    }


    @Override
    public Product getProduct(Integer id) throws BusinessException {
        return productRepository.findById(id).get();
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product changeStatus(Product product) throws BusinessException {
        productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> getTop4NewestProducts() throws BusinessException {
        return productRepository.findTop4ByStatusOrderByCreatedDateDesc(1);
    }

    @Override
    public List<Product> getTop4BestSellingProducts() {
        Pageable top4 = PageRequest.of(0, 4);
        return productRepository.findTop4BestSellingProducts(top4);
    }

}
