package com.example.demo.enums;

import com.example.demo.model.BaseErrorCode;
import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseErrorCode {
    CHECK_AUTHEN(500, "check.authen", HttpStatus.UNAUTHORIZED),
    LOGIN_FAILED(500, "login.failed", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(400, "user.not.found", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(404, "address.not.found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS(400, "username.already.exists", HttpStatus.BAD_REQUEST),
    PRODUCTNAME_ALREADY_EXISTS(400, "productname.already.exists", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_NOT_MATCH(400, "password.is.not.match", HttpStatus.BAD_REQUEST),
    THE_NEWPASSWORD_MUST_BE_DIFFERENT_FROM_THE_OLDPASSWORD(400,"newpassword.must.be.different.from.oldpassword",HttpStatus.BAD_REQUEST),
    DATE_OF_BIRTH_IS_IN_WRONG_FORMAT(400, "date.of.birth.is.in.wrong.format", HttpStatus.BAD_REQUEST),
    NUMBER_PHONE_ALREADY_EXISTS(400, "numberphone.already.exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(400, "email.already.exists", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS(403, "unauthorized.access", HttpStatus.FORBIDDEN),

    CATEGORY_ALREADY_EXISTS(400, "category.already.exists", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(404, "category.not.found", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(404, "cart.not.found", HttpStatus.NOT_FOUND),
    CART_DETAIL_NOT_FOUND(404, "cart.detail.not.found", HttpStatus.NOT_FOUND),
    UPLOAD_FILE_FAILED(500, "upload.file.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_IS_EMPTY(400, "file.is.empty", HttpStatus.BAD_REQUEST),
    COUNTRY_NOT_BLANK(400, "country.not.blank", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_EXIST(400, "product.name.exist", HttpStatus.BAD_REQUEST),
    BRAND_NAME_IS_EXIST(400, "brand.name.is.exists", HttpStatus.BAD_REQUEST),
    STYLE_NAME_IS_EXIST(400, "style.name.is.exists", HttpStatus.BAD_REQUEST),
    SOLE_NAME_IS_EXIST(400, "sole.name.is.exists", HttpStatus.BAD_REQUEST),
    SIZE_NAME_IS_EXIST(400, "size.name.is.exists", HttpStatus.BAD_REQUEST),
    MATERIAL_NAME_IS_EXIST(400, "material.name.is.exists", HttpStatus.BAD_REQUEST),
    BRAND_NAME_IS_NOT_BLANK(400, "brand.name.is.not.blank", HttpStatus.BAD_REQUEST),
    STYLE_NAME_IS_NOT_BLANK(400, "style.name.is.not.blank", HttpStatus.BAD_REQUEST),
    SOLE_NAME_IS_NOT_BLANK(400, "sole.name.is.not.blank", HttpStatus.BAD_REQUEST),
    SIZE_NAME_IS_NOT_BLANK(400, "size.name.is.not.blank", HttpStatus.BAD_REQUEST),
    MATERIAL_NAME_IS_NOT_BLANK(400, "material.name.is.not.blank", HttpStatus.BAD_REQUEST),
    COLOR_NAME_IS_EXIST(400, "color.name.is.exists", HttpStatus.BAD_REQUEST),
    COLOR_CODE_IS_EXIST(400, "color.code.is.exists", HttpStatus.BAD_REQUEST),
    STYLE_ID_NOT_EXIST(400, "style.id.not.exist", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_NOT_EXIST(400, "product.id.not.exist", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(400, "product.not.found", HttpStatus.BAD_REQUEST),
    PRODUCT_DETAIL_NOT_FOUND(400, "product.detail.not.found", HttpStatus.BAD_REQUEST),
    PAYMENT_EMPTY(400, "payment.empty", HttpStatus.BAD_REQUEST),
    PRODUCT_QUANTITY_INVALID(400, "product.quantity.invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_AVAILABLE(400, "product.not.available", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_FOUND(400, "payment.not.found", HttpStatus.BAD_REQUEST),
    PAYMENT_METHOD_NOT_FOUND(400, "payment.method.not.found", HttpStatus.BAD_REQUEST),
    BILL_NOT_FOUND(400, "bill.not.found", HttpStatus.BAD_REQUEST),
    BILL_INVALID_STATUS(400, "bill.invalid.status", HttpStatus.BAD_REQUEST),
    SIZE_VALUE_INVALID(400, "size.value.invalid", HttpStatus.BAD_REQUEST),
    SIZE_VALUE_MUST_BE_GREATER_THAN_ZERO(400, "size.value.must.be.greater.than.zero", HttpStatus.BAD_REQUEST),
    QUANTITY_NOT_ENOUGH(400, "quantity.not.enough", HttpStatus.BAD_REQUEST),
    DETAIL_CART_ID_NOT_EXIST(400, "detail.cart.not.exist", HttpStatus.BAD_REQUEST),
    SHIPPING_HISTORY_NOT_FOUND(400, "shipping.history.not.found", HttpStatus.BAD_REQUEST),
    FAVORITE_NOT_FOUND(400, "favorite.not.found", HttpStatus.BAD_REQUEST);
    private int code;

    private String message;

    private HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
