package com.example.demo.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private int status;
    private long processDuration;
    private String responseTime;
    private String path;
    @JsonInclude(Include.NON_NULL)
    private String clientTime;
    private String clientMessageId;
    private String errorMessage;
    private String detailMessage;
    private String errorCode;
}
