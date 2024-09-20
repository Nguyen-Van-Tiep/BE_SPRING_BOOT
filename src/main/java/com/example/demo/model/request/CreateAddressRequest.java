package com.example.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {

    private int id;

    private Long userID;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[1-9]|84[1-9])([0-9]{8,9})$", message = "Số điện thoại không hợp lệ")
    private String numberPhone;

    @JsonProperty("DistrictID")
    private Integer districtID;

    @NotBlank(message = "Huyện không được để trống")
    @JsonProperty("DistrictName")
    private String districtName;

    @JsonProperty("WardCode")
    private String wardCode;

    @NotBlank(message = "Xã không được để trống")
    @JsonProperty("WardName")
    private String wardName;

    @JsonProperty("ProvinceID")
    private Integer provinceID;

    @NotBlank(message = "Tỉnh/Thành không được để trống")
    @JsonProperty("ProvinceName")
    private String provinceName;

    private String fullAddress;

    private String addressType;

    private Boolean isDefault = false;

    public String getFulladdress() {
        return this.address + "," + this.provinceName + "," + this.wardName + "," + this.districtName;
    }

}
