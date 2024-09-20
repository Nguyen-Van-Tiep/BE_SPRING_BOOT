package com.example.demo.model.request;

import com.example.demo.entity.CartItem;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PayRequest {
    String code;
    String paymentMethod;
    Integer paymentMethodId;
    Integer addressCode;
    String addressMethod;
    BigDecimal fee;
    BigDecimal  totalPricePro;
    String username;
    Integer  quantity;
    List<CartItem> cartItems;
}
