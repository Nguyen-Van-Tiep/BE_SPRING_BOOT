//package com.example.demo.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "CART_DETAIL")
//public class CartDetail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    private Integer id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "ID_CART",referencedColumnName = "ID")
//    private Cart cart;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "ID_PRODUCTDETAIL",referencedColumnName = "ID")
//    private ProductDetail productDetail;
//
//    @Column(name = "QUANTITY")
//    private Integer quantity;
//
//    @Column(name = "PRICE")
//    private BigDecimal price;
//
//    @CreationTimestamp
//    @Column(name = "CREATED_DATE", updatable = false)
//    private Date createdDate;
//
//    @UpdateTimestamp
//    @Column(name = "MODIFY_DATE")
//    private Date modifiedDate;
//}
