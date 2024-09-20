package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.*;
import com.example.demo.enums.ErrorCode;
import com.example.demo.enums.OrderEnum;
import com.example.demo.model.DTO.ProductDTO;
import com.example.demo.model.DTO.StatisticsDto;
import com.example.demo.model.info.PaginationInfo;
import com.example.demo.model.request.ApproveBillRequest;
import com.example.demo.model.request.GetBillRequest;
import com.example.demo.model.response.BillsResponse;
import com.example.demo.model.response.DetailOrderResponse;
import com.example.demo.model.result.*;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.Constant;
import com.example.demo.model.utilities.FakeData;
import com.example.demo.model.utilities.SercurityUtils;
import com.example.demo.repository.*;
import com.example.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class BillServiceImplement implements BillService {

    private final BillRepository billRepository;
    private final DetailBillRepository detailBillRepository;
    private final ProductRepository productRepository;
    private final DetailProductRepository detailProductRepository;
    private final ShippingHistoryRepository shippingHistoryRepository;

    @Override
    public BillsResponse getCustomerBills(GetBillRequest request, int page, int size, String sortField, String sortType) {
        log.info("getCustomerBills: {}", CommonUtil.beanToString(request));
        BillsResponse response = new BillsResponse();
        List<BillResult> results = new ArrayList<>();
        String status = request.getStatus();
        String keyword = request.getKeyword();
        String username = SercurityUtils.getCurrentUser();
        if (CommonUtil.isNullOrEmpty(keyword)) {
            keyword = "";
        }
        Pageable pageable = null;
        if (CommonUtil.isNullOrEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
            pageable = PageRequest.of(page, size, sort);
        }

        // Lọc hóa đơn theo `username` của khách hàng và trạng thái
        Page<Bill> bills = billRepository.findByCreatedByAndStatus(username,keyword,status,pageable);

        for (Bill bill : bills) {
            BillResult result = new BillResult();
            result.setId(bill.getId());
            result.setCode(bill.getCode());
            result.setCustomerName(bill.getRecipientName());
            result.setNumberPhone(bill.getRecipientPhoneNumber());
            result.setTotal(bill.getTotal());
            result.setStatus(CommonUtil.getStatusVn(bill.getStatus()));
            result.setCreatedBy(bill.getCreatedBy());
            result.setCreatedDate(CommonUtil.date2Str(bill.getCreatedDate()));
            result.setPrice(bill.getPrice());
            result.setNote(bill.getNote());
            result.setModifiedBy(bill.getModifiedBy());
            result.setModifiedDate(CommonUtil.date2Str(bill.getModifiedDate()));
            result.setReceiveDate(CommonUtil.date2Str(bill.getEstimatedDeliveryDate()));
            results.add(result);
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(bills.getNumber(), bills.getSize(), Math.toIntExact(bills.getTotalElements()));
        response.setPagination(paginationInfo);
        response.setBills(results);
        return response;
    }



    @Override
    public BillsResponse getBills(GetBillRequest request, int page, int size, String sortField, String sortType) {
        log.info("getBills: {}", CommonUtil.beanToString(request));
        BillsResponse response = new BillsResponse();
        List<BillResult> results = new ArrayList<>();
        String status = request.getStatus();
        String keyword = request.getKeyword();

        if (CommonUtil.isNullOrEmpty(keyword)) {
            keyword = "";
        }
        Pageable pageable = null;
        if (CommonUtil.isNullOrEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
            pageable = PageRequest.of(page, size, sort);
        }
        Page<Bill> bills = billRepository.searchBills(keyword, status, pageable);
        for (Bill bill : bills) {
            BillResult result = new BillResult();
            result.setId(bill.getId());
            result.setCode(bill.getCode());
            result.setCustomerName(bill.getRecipientName());
            result.setNumberPhone(bill.getRecipientPhoneNumber());
            result.setTotal(bill.getTotal());
            result.setStatus(CommonUtil.getStatusVn(bill.getStatus()));
            result.setCreatedBy(bill.getCreatedBy());
            result.setCreatedDate(CommonUtil.date2Str(bill.getCreatedDate()));
            result.setPrice(bill.getPrice());
            result.setNote(bill.getNote());
            result.setModifiedBy(bill.getModifiedBy());
            result.setModifiedDate(CommonUtil.date2Str(bill.getModifiedDate()));
            result.setReceiveDate(CommonUtil.date2Str(bill.getEstimatedDeliveryDate()));
            results.add(result);
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(bills.getNumber(), bills.getSize(), Math.toIntExact(bills.getTotalElements()));
        response.setPagination(paginationInfo);
        response.setBills(results);
        return response;
    }
    @Override
    public DetailOrderResponse getCustomerOrderDetail(String code) throws BusinessException {
        log.info("getCustomerOrderDetail: {}", code);
        DetailOrderResponse response = new DetailOrderResponse();
        Optional<Bill> optional = billRepository.findByCode(code);
        if (optional.isEmpty()) {
            throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
        }
        Bill bill = optional.get();
        response.setId(bill.getId());
        response.setOrderCode(bill.getCode());
        response.setNumberPhone(bill.getRecipientPhoneNumber());
        response.setFullName(bill.getRecipientName());
        response.setFullAddress(bill.getReceiverAddress());
        response.setStatus(CommonUtil.getStatusVn(bill.getStatus()));
        response.setStatusCode(bill.getStatus());
        response.setPrice(bill.getPrice());
        response.setFee(bill.getShippingMoney());
        response.setSumPrice(bill.getPrice().add(bill.getShippingMoney()));
        response.setReceiveDate(CommonUtil.date2Str(bill.getEstimatedDeliveryDate()));

        // Lấy chi tiết sản phẩm
        List<SubOrderResult> subOrderResults = new ArrayList<>();
        List<BillDetail> detailBills = detailBillRepository.findByBillId(bill.getId());
        for (BillDetail detailBill : detailBills) {
            SubOrderResult subOrderResult = new SubOrderResult();
            subOrderResult.setProductName(detailBill.getProductDetail().getProduct().getProductName());
            subOrderResult.setQuantity(detailBill.getQuantity());
            subOrderResult.setPrice(detailBill.getPrice());
            subOrderResult.setSize(detailBill.getProductDetail().getSize().getName());
            subOrderResult.setProductImage(detailBill.getProductDetail().getImageList().get(0).getUrl());
            subOrderResults.add(subOrderResult);
        }
        response.setProducts(subOrderResults);

        return response;
    }

    @Override
    public DetailOrderResponse detail(String code) throws BusinessException {
        log.info("detail: {}", code);
        DetailOrderResponse response = new DetailOrderResponse();
        Optional<Bill> optional = billRepository.findByCode(code);
        if (optional.isEmpty()) {
            throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
        }
        Bill bill = optional.get();
        response.setId(bill.getId());
        response.setOrderCode(bill.getCode());
        response.setNumberPhone(bill.getRecipientPhoneNumber());
        response.setFullName(bill.getRecipientName());
        response.setUsername(bill.getCreatedBy());
        response.setFullAddress(bill.getReceiverAddress());
        response.setStatus(CommonUtil.getStatusVn(bill.getStatus()));
        response.setStatusCode(bill.getStatus());
        response.setPrice(bill.getPrice());
        response.setFee(bill.getShippingMoney());
        response.setSumPrice(bill.getPrice().add(bill.getShippingMoney()));
        response.setReceiveDate(CommonUtil.date2Str(bill.getEstimatedDeliveryDate()));
        List<SubOrderResult> subOrderResults = new ArrayList<>();
        List<BillDetail> detailBills = detailBillRepository.findByBillId(bill.getId());
        log.info("Processing BillDetail for Bill ID: {}", bill.getId());
        System.out.println("---------------------Omygot-----------------------" + detailBills);
        for (BillDetail detailBill : detailBills) {
            SubOrderResult subOrderResult = new SubOrderResult();
            subOrderResult.setProductName(detailBill.getProductDetail().getProduct().getProductName());
            subOrderResult.setQuantity(detailBill.getQuantity());
            subOrderResult.setPrice(detailBill.getPrice());
            subOrderResult.setProductImage(detailBill.getProductDetail().getImageList().get(0).getUrl());
            subOrderResult.setSize(detailBill.getProductDetail().getSize().getName());
            subOrderResults.add(subOrderResult);
        }
        response.setProducts(subOrderResults);
        response.setGenerals(getOrderGeneralResults(bill.getId()));
        response.setDetails(getOrderDetailResults(bill.getId()));
        return response;
    }

    @Transactional
    @Override
    public boolean init(ApproveBillRequest request, String username) throws BusinessException {
        System.out.println("init:---------------------{}" + CommonUtil.beanToString(request));
        Optional<Bill> optional = billRepository.findById(request.getId());
        if (optional.isEmpty()) {
            throw new BusinessException(ErrorCode.BILL_NOT_FOUND);
        }
        Bill bill = optional.get();
        ShippingHistory history = this.shippingHistoryRepository.findFirstByBillIdAndParentIdIsNullAndStatusOrderByModifiedDateAsc(bill.getId(), OrderEnum.PENDING.getValue());
        if (history == null) {
            throw new BusinessException(ErrorCode.SHIPPING_HISTORY_NOT_FOUND); // Thay đổi thành mã lỗi phù hợp
        }
        List<ShippingHistory> histories = new ArrayList<>();
        switch (request.getStatus()) {
            case Constant.STATUS_PAYMENT.WAITING:
                if (!bill.getStatus().equals(Constant.STATUS_PAYMENT.PENDING)) {
                    throw new BusinessException(ErrorCode.BILL_INVALID_STATUS);
                }
                bill.setStatus(Constant.STATUS_PAYMENT.WAITING);
                histories = FakeData.getChildPAYMENTTC(username, bill.getId(), history.getId());
                List<BillDetail> detailBills = detailBillRepository.findByBillId(bill.getId());
                for (BillDetail detailBill : detailBills) {
                    ProductDetail productDetail = detailProductRepository.getById(detailBill.getProductDetail().getId());
                    productDetail.setQuantity(productDetail.getQuantity() - detailBill.getQuantity());
                    detailProductRepository.save(productDetail); // Lưu lại sau khi trừ số lượng
                }
                break;
            case Constant.STATUS_PAYMENT.TRANSPORTING:
                if (!bill.getStatus().equals(Constant.STATUS_PAYMENT.WAITING)) {
                    throw new BusinessException(ErrorCode.BILL_INVALID_STATUS);
                }
                histories = FakeData.getChildSHIPPING(username, bill.getId(), history.getId());
                bill.setStatus(Constant.STATUS_PAYMENT.TRANSPORTING);
                break;
            case Constant.STATUS_PAYMENT.FINISH:
                if (!bill.getStatus().equals(Constant.STATUS_PAYMENT.TRANSPORTING)) {
                    throw new BusinessException(ErrorCode.BILL_INVALID_STATUS);
                }
                histories = FakeData.getChildRECEIVED(username, bill.getId(), history.getId());
                bill.setStatus(Constant.STATUS_PAYMENT.FINISH);
                bill.setPaymentStatus(Constant.PAYMENT_STATUS.PAID);
                bill.setDateOfPayment(new Date());
                this.shippingHistoryRepository.updateStatusByBillId(bill.getId(), OrderEnum.DONE.getValue());
                break;
            case Constant.STATUS_PAYMENT.REJECT:
                if (!bill.getStatus().equals(Constant.STATUS_PAYMENT.WAITING)) {
                    throw new BusinessException(ErrorCode.BILL_INVALID_STATUS);
                }
            case Constant.STATUS_PAYMENT.ROLLBACK:
                if (bill.getStatus().equals(Constant.STATUS_PAYMENT.WAITING)) {
                    this.rollbackShippingHistory(bill.getId(), 2);
                    bill.setStatus(Constant.STATUS_PAYMENT.PENDING);
                }
                break;
            default:
                bill.setStatus(Constant.STATUS_PAYMENT.CANCEL);
        }
        bill.setModifiedBy(username);
        if (bill.getPaymentStatus().equals(Constant.PAYMENT_STATUS.PAID)) {
            bill.setPaymentStatus(Constant.PAYMENT_STATUS.SUCCESS);
        }
        if (bill.equals(Constant.STATUS_PAYMENT.REJECT) || bill.equals(Constant.STATUS_PAYMENT.CANCEL)) {
            rollbackProduct(bill.getId());
        }
        history.setStatus(OrderEnum.DONE.getValue());
        history.setModifiedBy(username);
        history.setModifiedDate(LocalDateTime.now());
        this.shippingHistoryRepository.save(history);
        this.shippingHistoryRepository.saveAll(histories);
        this.billRepository.save(bill);
        return true;
    }


    public void rollbackShippingHistory(Integer billId, int level) {
        int deletedRows = shippingHistoryRepository.deleteByBillIdAndLevelGreaterThanEqual(billId, level);
        System.out.println("Number of deleted rows: " + deletedRows);
    }


    @Override
    public BillsResponse getBills(String status, String billCode, int page, int size, String sortField, String sortType, String username) {
        log.info("getBills: {}", CommonUtil.beanToString(status));
        BillsResponse response = new BillsResponse();
        List<BillResult> results = new ArrayList<>();
        if (CommonUtil.isNullOrEmpty(billCode)) {
            billCode = "";
        }
        Pageable pageable = null;
        if (CommonUtil.isNullOrEmpty(sortField)) {
            pageable = PageRequest.of(page, size);
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(sortType), sortField);
            pageable = PageRequest.of(page, size, sort);
        }
        Page<Bill> bills = billRepository.getAll(billCode, username, status, pageable);
        for (Bill bill : bills) {
            BillResult result = new BillResult();
            result.setId(bill.getId());
            result.setCode(bill.getCode());
            result.setCustomerName(bill.getRecipientName());
            result.setNumberPhone(bill.getRecipientPhoneNumber());
            result.setTotal(bill.getTotal());
            result.setStatus(CommonUtil.getStatusVn(bill.getStatus()));
            result.setCreatedBy(bill.getCreatedBy());
            result.setCreatedDate(CommonUtil.date2Str(bill.getCreatedDate()));
            result.setPrice(bill.getPrice());
            result.setNote(bill.getNote());
            result.setModifiedBy(bill.getModifiedBy());
            result.setModifiedDate(CommonUtil.date2Str(bill.getModifiedDate()));
            result.setReceiveDate(CommonUtil.date2Str(bill.getEstimatedDeliveryDate()));
            results.add(result);
        }
        PaginationInfo paginationInfo = CommonUtil.getPaginationInfo(bills.getNumber(), bills.getSize(), Math.toIntExact(bills.getTotalElements()));
        response.setPagination(paginationInfo);
        response.setBills(results);
        return response;
    }

    private List<OrderGeneralResult> getOrderGeneralResults(Integer id) {
        List<ShippingHistory> histories = this.shippingHistoryRepository.findByBillIdAndParentIdIsNullOrderByModifiedDateAsc(id);
        List<OrderGeneralResult> orderGeneralResults = new ArrayList<>();
        int i = 0;
        for (ShippingHistory history : histories) {
            OrderGeneralResult orderGeneralResult = new OrderGeneralResult();
            orderGeneralResult.setStatus(history.getStatus().equals(OrderEnum.DONE.getValue()) ? Constant.STATUS_HISTORY.COMPLETED : Constant.STATUS_HISTORY.WAITING);
            switch (i) {
                case 0:
                    orderGeneralResult.setIcon(Constant.ICON.ORDER);
                    break;
                case 1:
                    orderGeneralResult.setIcon(Constant.ICON.PAYMENT);
                    break;
                case 2:
                    orderGeneralResult.setIcon(Constant.ICON.TRANSPORTING);
                    break;
                case 3:
                    orderGeneralResult.setIcon(Constant.ICON.SUCCESS);
                    break;
                case 4:
                    orderGeneralResult.setIcon(Constant.ICON.DONE);
                    break;
                default:
                    orderGeneralResult.setIcon(Constant.ICON.PENDING);
            }
            orderGeneralResult.setStepCode(history.getId());
            orderGeneralResult.setStepName(history.getTitle());
            orderGeneralResult.setTimeStep(CommonUtil.date2Str(history.getModifiedDate()));
            orderGeneralResult.setUsername(SercurityUtils.getCurrentUser());
            orderGeneralResults.add(orderGeneralResult);
            i++;
        }
        return orderGeneralResults;
    }

    private List<OrderDetailResult> getOrderDetailResults(Integer id) {
        List<ShippingHistory> histories = this.shippingHistoryRepository.findByBillIdAndStatusOrderByModifiedDateDesc(id, OrderEnum.DONE.getValue());
        List<OrderDetailResult> orderDetailResults = new ArrayList<>();
        int i = 0;
        for (ShippingHistory history : histories) {
            OrderDetailResult orderDetailResult = new OrderDetailResult();
            if (history.getParentId() == null) {
                switch (i) {
                    case 0:
                        orderDetailResult.setIcon(Constant.ICON.ORDER);
                        break;
                    case 1:
                        orderDetailResult.setIcon(Constant.ICON.PAYMENT);
                        break;
                    case 2:
                        orderDetailResult.setIcon(Constant.ICON.TRANSPORTING);
                        break;
                    case 3:
                        orderDetailResult.setIcon(Constant.ICON.SUCCESS);
                        break;
                    case 4:
                        orderDetailResult.setIcon(Constant.ICON.DONE);
                        break;
                    default:
                        orderDetailResult.setIcon(Constant.ICON.PENDING);
                }
                i++;
            } else {
                orderDetailResult.setIcon(Constant.ICON.TASK);
            }
            orderDetailResult.setStepCode(history.getId() + "");
            orderDetailResult.setStepName(history.getTitle());
            orderDetailResult.setStepDate(CommonUtil.date2Str(history.getModifiedDate()));
            orderDetailResult.setDescription(history.getContent());
            orderDetailResult.setUsername(SercurityUtils.getCurrentUser());
            orderDetailResults.add(orderDetailResult);
        }
        return orderDetailResults;
    }

    private void rollbackProduct(int billId) {
        List<BillDetail> detailBills = detailBillRepository.findByBillId(billId);
        List<Product> products = new ArrayList<>();
        List<ProductDetail> detailProducts = new ArrayList<>();
        for (BillDetail detailBill : detailBills) {
            ProductDetail detailProduct = detailProductRepository.getById(detailBill.getProductDetail().getId());
            detailProduct.setQuantity(detailProduct.getQuantity() - detailBill.getQuantity());
            detailProducts.add(detailProduct);
        }
        this.detailProductRepository.saveAll(detailProducts);
        productRepository.saveAll(products);
    }

    public StatisticsDto getStatisticsByDay() {
        StatisticsResult result = billRepository.getStatisticsByDay();
        return mapToDto(result);
    }

    public StatisticsResult getWeeklyStatistics() {
        List<Object[]> results = billRepository.getStatisticsByWeek();
        if (results.isEmpty()) {
            return new StatisticsResult();
        }

        Object[] result = results.get(0); // Chỉ có một dòng dữ liệu
        BigDecimal sumTotalAmount = BigDecimal.valueOf(((Number) result[1]).doubleValue());
        return new StatisticsResult(
                ((Number) result[0]).longValue(), // COUNT(*)
                sumTotalAmount,
                ((Number) result[2]).longValue(), // SUM(CASE WHEN STATUS = 'F' THEN 1 ELSE 0 END)
                ((Number) result[3]).longValue(), // SUM(CASE WHEN STATUS = 'C' THEN 1 ELSE 0 END)
                ((Number) result[4]).longValue()  // SUM(CASE WHEN STATUS = 'R' THEN 1 ELSE 0 END)
        );
    }


    public StatisticsDto getStatisticsByMonth() {
        List<Object[]> results = billRepository.getStatisticsByMonth();
        if (results.isEmpty()) {
            return new StatisticsDto();
        }

        Object[] result = results.get(0); // Chỉ có một dòng dữ liệu
        BigDecimal sumTotalAmount = BigDecimal.valueOf(((Number) result[1]).doubleValue());
        StatisticsResult statisticsResult = new StatisticsResult(
                ((Number) result[0]).longValue(), // COUNT(*)
                sumTotalAmount,
                ((Number) result[2]).longValue(), // SUM(CASE WHEN status = 'F' THEN 1 ELSE 0 END)
                ((Number) result[3]).longValue(), // SUM(CASE WHEN status = 'C' THEN 1 ELSE 0 END)
                ((Number) result[4]).longValue()  // SUM(CASE WHEN status = 'R' THEN 1 ELSE 0 END)
        );
        return mapToDto(statisticsResult);
    }


    public StatisticsDto getStatisticsByYear() {
        List<Object[]> results = billRepository.getStatisticsByYear();
        if (results.isEmpty()) {
            return new StatisticsDto();
        }

        Object[] result = results.get(0); // Chỉ có một dòng dữ liệu
        BigDecimal sumTotalAmount = BigDecimal.valueOf(((Number) result[1]).doubleValue());
        StatisticsResult statisticsResult = new StatisticsResult(
                ((Number) result[0]).longValue(), // COUNT(*)
                sumTotalAmount,
                ((Number) result[2]).longValue(), // SUM(CASE WHEN status = 'F' THEN 1 ELSE 0 END)
                ((Number) result[3]).longValue(), // SUM(CASE WHEN status = 'C' THEN 1 ELSE 0 END)
                ((Number) result[4]).longValue()  // SUM(CASE WHEN status = 'R' THEN 1 ELSE 0 END)
        );
        return mapToDto(statisticsResult);
    }

    private StatisticsDto mapToDto(StatisticsResult result) {
        StatisticsDto dto = new StatisticsDto();

        // Kiểm tra độ dài mảng trước khi truy cập
        dto.setTotalBills(result.getTotalBills());
        dto.setTotalAmount(result.getTotalAmount());
        dto.setSuccessOrders(result.getSuccessOrders());
        dto.setCanceledOrders(result.getCanceledOrders());
        dto.setReturnedOrders(result.getReturnedOrders());
        //dto.setRevenue(result.getRevenue());

        return dto;
    }
    //+-----------------------------------------+//
    public List<ProductDTO> getTopSellingProductsByDay() {
        return billRepository.findTopSellingProductsByDay();
    }

    public List<ProductDTO> getTopSellingProductsByWeek(Date startDate, Date endDate) {
        return billRepository.findTopSellingProductsByWeek(startDate, endDate);
    }

    public List<ProductDTO> getTopSellingProductsByMonth() {
        return billRepository.findTopSellingProductsByMonth();
    }

    public List<ProductDTO> getTopSellingProductsByYear() {
        return billRepository.findTopSellingProductsByYear();
    }

    public List<ProductDTO> getTopSellingProductsByCustomRange(Date startDate, Date endDate) {
        return billRepository.findTopSellingProductsByCustomRange(startDate, endDate);
    }
}
