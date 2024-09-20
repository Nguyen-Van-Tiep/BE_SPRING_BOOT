package com.example.demo.config.middleware;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.output.TeeOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author thangnq.os
 */
@Log4j2
public class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private String uuid;

    public ResponseWrapper(String uuid, HttpServletResponse response) {
        super(response);
        response.setBufferSize(128 * 1024);
        this.uuid = uuid;
    }

    @Override
    public ServletResponse getResponse() {
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                log.info("setWriteListener");
            }

            private TeeOutputStream tee = new TeeOutputStream(ResponseWrapper.super.getOutputStream(), bos);

            @Override
            public void write(int b) throws IOException {
                tee.write(b);
            }
        };
    }

    public byte[] toByteArray() {
        return bos.toByteArray();
    }
}