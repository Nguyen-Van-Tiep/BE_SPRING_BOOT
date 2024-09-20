package com.example.demo.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.ThreadContext;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haidv
 * @version 1.0
 */
@Setter
@Getter
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = 5552150055173519341L;

    private int code;

    private String message;
    private String msgError;

    //	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+7")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "GMT+7")
    private Date timestamp;

    private String uuid;

    public ResponseData(int value, String s, Object o) {
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private long duration;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;
    private transient T data;

    private static final String START_TIME = "startTime";

    public ResponseData() {
        this.timestamp = new Date();
    }

    public ResponseData<T> success(T data) {
        this.data = data;
        this.code = 0;
        this.message = "Success!";
        this.path = ThreadContext.get("path");
        this.uuid = ThreadContext.get("uuid");
        long start = Long.parseLong(ThreadContext.get(START_TIME));
        this.duration = System.currentTimeMillis() - start;
        return this;
    }

    public ResponseData<T> error(int code, String message) {
        this.code = code;
        this.message = message;
        this.path = ThreadContext.get("path");
        this.uuid = ThreadContext.get("uuid");
        long start = Long.parseLong(ThreadContext.get(START_TIME));
        this.duration = System.currentTimeMillis() - start;
        return this;
    }

    public ResponseData<T> error(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.path = ThreadContext.get("path");
        this.uuid = ThreadContext.get("uuid");
        long start = Long.parseLong(ThreadContext.get(START_TIME));
        this.duration = System.currentTimeMillis() - start;
        return this;
    }

    public ResponseData<T> error(int code, String message, String msgError) {
        this.code = code;
        this.message = message;
        this.msgError = msgError;
        this.path = ThreadContext.get("path");
        this.uuid = ThreadContext.get("uuid");
        long start = Long.parseLong(ThreadContext.get(START_TIME));
        this.duration = System.currentTimeMillis() - start;
        return this;
    }

    public T getData() {
        return data;
    }
}
