package com.example.demo.model.result;

import lombok.Data;

@Data
public class BrandResult {
    private int id;
    private String name;
    private String status;
    private String statusCode;
    private String date;
    private String description;
    private String countryCode;
    private String countryName;
    private String logo;
    private int productCount;
    private int stylistCount;
}
