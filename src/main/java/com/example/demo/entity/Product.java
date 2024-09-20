package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STYLE_ID", referencedColumnName = "id")
    private Style style;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "id")
    private Material material;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SOLE_ID", referencedColumnName = "id")
    private Sole sole;
    @Column(name = "DESCRIPTION")
    private String description;


    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "MODIFIED_DATE")
    @UpdateTimestamp
    private Date modifiedDate;

    @Column(name = "STATUS")
    private Integer status;

//    @Column(name = "CODE")
//    private String code;

    @OneToMany(mappedBy = "product")
//    @JsonManagedReference
    List<ProductDetail> productDetailList;
}
