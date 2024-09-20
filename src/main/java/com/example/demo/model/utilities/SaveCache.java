package com.example.demo.model.utilities;

import com.example.demo.model.response.PaymentResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SaveCache<T> {
    private Map<String, PaymentResponse> paymentMap = new HashMap<>();

    public void savePayment(String code, PaymentResponse response) {
        paymentMap.put(code, response);
    }

    public PaymentResponse getPayment(String code) {
        return paymentMap.get(code);
    }

    public void removePayment(String code) {
        paymentMap.remove(code);
    }

}
