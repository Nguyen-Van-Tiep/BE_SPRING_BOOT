package com.example.demo.config.handler;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.config.middleware.RequestWrapper;
import com.example.demo.model.info.ErrorInfo;
import com.example.demo.model.response.ResponseData;
import com.example.demo.model.utilities.CommonUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@ControllerAdvice
@Log4j2
public class BusinessHandlerException {

    public final ResponseEntity<Object> handleAllExceptionImpl(Exception ex, HttpStatus status,
                                                               RequestWrapper request) {
        if (ex instanceof BusinessException) {
            BusinessException e = (BusinessException) ex;
            log.info("[REST_EXCEPTION_HANDLER][HANDLE_ALL_EXCEPTION_IMPLEMENT][BUSINESS_EXCEPTION] ID: {}, CODE: {}, DURATION: {}",
                    request.getUuid(), e.getErrorCode(), e.getMessage());

            return new ResponseEntity<>(
                    new ResponseData<>().error(e.getErrorCode().getCode(), getMessage(e.getDetailMessage()), getMessage(e.getErrorCode().getMessage())),
                    e.getErrorCode().getHttpStatus());
        }
        else if(ex instanceof BindException){
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : ((BindException) ex).getFieldErrors()) {
                errors.put(error.getField(), getMessage(error.getDefaultMessage()));
            }
            String message = CommonUtil.beanToString(errors);
            return new ResponseEntity<>(
                    new ResponseData<>().error(400, getMessage("is.not.valid"), message),HttpStatus.BAD_REQUEST);
        }
        ErrorInfo errorInfo = new ErrorInfo(status.value(), ex.getMessage(), request.getContextPath(),
                request.getUuid(),null);
        return new ResponseEntity<>(errorInfo, status);
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<Object> handleBusinessException(BusinessException ex, RequestWrapper request) {
        return handleAllExceptionImpl(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

//    @Order(Ordered.HIGHEST_PRECEDENCE + 9999)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleBindException(MethodArgumentNotValidException ex, WebRequest request) {
        return handleAllExceptionImpl(ex, HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handlerException(Exception ex, RequestWrapper request) {
        return handleAllExceptionImpl(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private static final  String BUNDLE_NAME = "application"; // Tên file properties (bỏ đuôi .properties)
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("vi", "VN", "UTF-8"));

    public String getMessage(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            return key;
        }
    }
}
