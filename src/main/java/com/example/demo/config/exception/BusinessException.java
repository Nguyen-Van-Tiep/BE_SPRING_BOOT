package com.example.demo.config.exception;

import com.example.demo.model.BaseErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends Exception{
    private final BaseErrorCode errorCode;
    private final String detailMessage;

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public BusinessException(Throwable cause, BaseErrorCode errorCode){
        super(cause);
        this.errorCode= errorCode;
        this.detailMessage = "";
    }

    public BusinessException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.detailMessage = message;
    }

    public BusinessException(BaseErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.detailMessage = "";
    }
    @Override
    public String getMessage() {
        return detailMessage;
    }
}
