package com.example.demo.service.implement;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.entity.*;
import com.example.demo.enums.ErrorCode;
import com.example.demo.enums.OrderEnum;
import com.example.demo.model.request.PayRequest;
import com.example.demo.model.request.PaymentRequest;
import com.example.demo.model.response.PaymentResponse;
import com.example.demo.model.response.UserInfoResponse;
import com.example.demo.model.result.PaymentResult;
import com.example.demo.model.utilities.CommonUtil;
import com.example.demo.model.utilities.Constant;
import com.example.demo.model.utilities.FakeData;
import com.example.demo.model.utilities.SaveCache;
import com.example.demo.repository.*;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentServiceImplement implements PaymentService {
    private final DetailProductRepository detailProductRepository;
    private final ProductRepository productRepository;
    private final SaveCache<PaymentResponse> saveCache;
    private final BillRepository billRepository;
    private final DetailBillRepository detailBillRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CartItemRepository detailCartRepository;
    private final ShippingHistoryRepository shippingHistoryRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final ImageRepository imageRepository;

    @Override
    public PaymentResponse validate(PaymentRequest request, String username) throws BusinessException {
        log.info("validate payment request: {}, username: {}", request, username);

        PaymentResponse response = new PaymentResponse();
        if (request.getType().equals(Constant.PAYMENT_TYPE.PRODUCT)) {
            response = buyProduct(request.getResults());
        } else {
            response = buyCart(request.getCartIds());
        }
        saveCache.savePayment(response.getCode(), response);
        return response;
    }

    private PaymentResponse buyCart(List<Integer> detailCartIds) throws BusinessException {
        if (detailCartIds == null || detailCartIds.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }
        List<CartItem> detailCarts = detailCartRepository.findAllById(detailCartIds);
        if (detailCarts == null || detailCarts.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }
        if (detailCarts.size() != detailCartIds.size()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }

        // Lấy ID của các ProductDetail từ các CartItem
        List<Integer> ids = detailCarts.stream()
                .map(cartItem -> cartItem.getProductDetail().getId())
                .collect(Collectors.toList());

        List<ProductDetail> detailProducts = detailProductRepository.findAllById(ids);
        if (detailProducts == null || detailProducts.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }

        Map<Integer, ProductDetail> mDetailProduct = detailProducts.stream()
                .collect(Collectors.toMap(ProductDetail::getId, detailProduct -> detailProduct));

        BigDecimal price = BigDecimal.ZERO;
        List<PaymentResult> results = new ArrayList<>();
        for (CartItem cartItem : detailCarts) {
            PaymentResult paymentResult = new PaymentResult();
            ProductDetail detailProduct = mDetailProduct.get(cartItem.getProductDetail().getId());

            if (detailProduct != null) {
                // Lấy thông tin size và giá từ ProductDetail
                paymentResult.setSize(detailProduct.getSize().getName());
                paymentResult.setPrice(detailProduct.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                paymentResult.setName(detailProduct.getProduct().getProductName());
                paymentResult.setDetailProductId(detailProduct.getId());
                paymentResult.setQuantity(cartItem.getQuantity());
                List<Image> images = detailProduct.getImageList();

                // Kiểm tra xem có ảnh không và set ảnh đầu tiên
                if (images != null && !images.isEmpty()) {
                    paymentResult.setImage(images.get(0).getUrl()); // Giả sử `setImage` yêu cầu URL
                } else {
                    paymentResult.setImage("default_image_url"); // Set một ảnh mặc định nếu không có ảnh
                }
                // Cộng dồn tổng giá
                price = price.add(paymentResult.getPrice());

                results.add(paymentResult);
            }
        }
        return new PaymentResponse(CommonUtil.generatePaymentCode(), price, BigDecimal.ZERO, results);
    }


    private PaymentResponse buyProduct(List<PaymentResult> results) throws BusinessException {
        if (results == null || results.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }

        // Lấy danh sách ID của ProductDetail từ results
        List<Integer> ids = List.of(results.stream().map(PaymentResult::getDetailProductId).toArray(Integer[]::new));
        List<ProductDetail> detailProducts = detailProductRepository.findAllById(ids);
        if (detailProducts == null || detailProducts.isEmpty()) {
            throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
        }

        // Lấy thông tin Product tương ứng với các ProductDetail
        List<Product> products = productRepository.findByDetailProductIds(ids);
        if (products == null || products.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        BigDecimal price = BigDecimal.ZERO;

        // Duyệt qua từng PaymentResult và tính giá dựa trên ProductDetail
        for (PaymentResult result : results) {
            for (ProductDetail detailProduct : detailProducts) {
                if (detailProduct.getId() == result.getDetailProductId()) {
                    if (result.getId() == 0) {
                        throw new BusinessException(ErrorCode.PAYMENT_EMPTY);
                    }
                    if (result.getId() != detailProduct.getProduct().getId()) {
                        throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
                    }
                    if (detailProduct.getQuantity() < result.getQuantity()) {
                        throw new BusinessException(ErrorCode.PRODUCT_QUANTITY_INVALID);
                    }

                    // Tính toán giá dựa trên ProductDetail
                    result.setPrice(detailProduct.getPrice().multiply(BigDecimal.valueOf(result.getQuantity())));
                    result.setSize(detailProduct.getSize().getName());
                    result.setDetailProductId(detailProduct.getId());

                    // Lấy thông tin từ bảng Product
                    for (Product product : products) {
                        if (detailProduct.getProduct().getId() == product.getId()) {
                            result.setName(product.getProductName());
                            result.setBrand(product.getBrand().getName());
                            result.setId(product.getId());
                            // Có thể lấy thêm thông tin khác nếu cần từ product
                            break;
                        }
                    }

                    // Cộng dồn tổng giá
                    price = price.add(result.getPrice());
                    break;
                }
            }
        }

        // Trả về kết quả thanh toán
        PaymentResponse response = new PaymentResponse(CommonUtil.generatePaymentCode(), price, BigDecimal.ZERO, results);
        return response;
    }


    @Override
    public PaymentResponse getPayment(String code) throws BusinessException {
        log.info("get payment code: {}", code);
        PaymentResponse response = new PaymentResponse();
        response = saveCache.getPayment(code);
        if (response == null) {
            throw new BusinessException(ErrorCode.PAYMENT_NOT_FOUND);
        }

        return response;
    }

    @Override
    public void create(PayRequest request, String username) throws BusinessException {
        Bill bill = new Bill();
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Address address = this.addressRepository.findDefaultAddress(username);
        if (address == null) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
        }
//        CartItem cartItems = new CartItem();
        Bill bill2 = new Bill();
        bill2.setCode(request.getCode());
        bill2.setUser(user);
        bill2.setStatus(Constant.STATUS_PAYMENT.PENDING);
        bill2.setPaymentStatus(Constant.PAYMENT_STATUS.NOT_PAID);
        bill2.setCreatedBy(username);
        bill2.setModifiedBy(username);
        bill2.setCreatedDate(new Date());
        bill2.setModifiedDate(new Date());
        bill2.setRecipientName(user.getFullName());
        bill2.setRecipientPhoneNumber(user.getNumberPhone());
        bill2.setReceiverAddress(address.getFulladdress());
        List<CartItem> list = request.getCartItems();
        int totalQuantity = list.stream().mapToInt(CartItem::getQuantity).sum();
        bill2.setTotal(totalQuantity);
//        bill2.setTotalAmount(request.getFee().add(request.getTotalPricePro()));
        bill2.setPrice(request.getTotalPricePro());
        bill2.setShippingMoney(request.getFee());
        bill = billRepository.save(bill2);
        int billId = bill2.getId();
        List<BillDetail> detailBills = new ArrayList<>();
        for(CartItem cartItem:list){
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setProductDetail(cartItem.getProductDetail());
            billDetail.setQuantity(Long.valueOf(cartItem.getQuantity()));
            billDetail.setPrice(cartItem.getProductDetail().getPrice());
            detailBills.add(billDetail);
            detailBillRepository.save(billDetail);
            detailBillRepository.saveAll(detailBills);
            detailCartRepository.delete(cartItem);
        }
        saveHistory(billId, username);
    }

    private void saveHistory(Integer billId, String username){
        List<ShippingHistory> shippingHistories = FakeData.getParentShippingHistoryTC(username, billId);
        shippingHistories = this.shippingHistoryRepository.saveAll(shippingHistories);
        Map<Integer, ShippingHistory> mShippingHistory = shippingHistories.stream()
                .filter(h -> h.getStatus().equals(OrderEnum.DONE.getValue()))
                .sorted(Comparator.comparing(ShippingHistory::getModifiedDate).reversed())
                .collect(Collectors.toMap(ShippingHistory::getId, shippingHistory -> shippingHistory, (e1, e2) -> e1, LinkedHashMap::new));
        List<ShippingHistory> child = new ArrayList<>();
        for (Map.Entry<Integer, ShippingHistory> entry : mShippingHistory.entrySet()) {
            Integer parentId = entry.getKey();
            child = FakeData.getChildPENDINGTC(username, billId, parentId);
        }
        this.shippingHistoryRepository.saveAll(child);
    }

    @Override
    public PaymentResponse send(PaymentRequest request, String username) throws BusinessException {
        return null;
    }

    @Override
    public UserInfoResponse getUserInfo(String username) throws BusinessException {
        log.info("get user info username: {}", username);
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND,"Tài khoản không tồn tại");
        }
        Address address = this.addressRepository.findDefaultAddress(username);
        if (address == null) {
            throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND,"Địa chỉ không tồn tại");
        }
        UserInfoResponse response = new UserInfoResponse();
        response.setFullName(user.getFullName());
        response.setNumberPhone(user.getNumberPhone());
        response.setAddress(address.getFulladdress());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setDistrictID(address.getDistrictID());
        response.setDistrictName(address.getDistrictName());
        response.setProvinceID(address.getProvinceID());
        response.setProvinceName(address.getProvinceName());
        response.setWardCode(address.getWardCode());
        response.setWardName(address.getWardName());
        return response;
    }
}
