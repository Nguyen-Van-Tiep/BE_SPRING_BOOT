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
@Table(name = "Payment_Method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME_METHOD")
    private String nameMethod;

    @Column(name = "STATUS")
    private String status;

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
}
