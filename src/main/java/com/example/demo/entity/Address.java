package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FULL_ADDRESS")
    private String fullAddress;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "IS_DEFAULT", nullable = false)
    private Boolean isDefault;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;

    @Column(name = "STREET_CODE")
    private Integer districtID;

    @Column(name = "STREET_NAME")
    private String districtName;

    @Column(name = "WARD_CODE")
    private String wardCode;

    @Column(name = "WARD_NAME")
    private String wardName;

    @Column(name = "CITY_CODE")
    private Integer provinceID;

    @Column(name = "CITY_NAME")
    private String provinceName;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", updatable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @UpdateTimestamp
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "NUMBER_PHONE")
    private String numberPhone;

    @Column(name = "FULL_NAME")
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public String getFulladdress() {
        return this.address + "," + this.provinceName + "," + this.districtName + "," + this.wardName;
    }

}
