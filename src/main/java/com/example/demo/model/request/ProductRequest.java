package com.example.demo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductRequest {
    String name;
    Integer status;
    Date selectedStartDate;
    Date selectedEndDate ;
}
