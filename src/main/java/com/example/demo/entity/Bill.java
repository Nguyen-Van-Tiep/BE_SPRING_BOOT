package com.example.demo.entity;

import com.example.demo.model.utilities.CommonUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BILL")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SHIPPING_MONEY")
    private BigDecimal shippingMoney;

    @Column(name = "CODE")
    private String code;

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

    @Column(name = "SHIPPING_DATE")
    private Date shippingDate;

    @Column(name = "DATE_OF_PAYMENT")
    private Date dateOfPayment;

    @Column(name = "RECIPIENT_NAME")
    private String recipientName;

    @Column(name = "RECIPIENT_PHONE_NUMBER")
    private String recipientPhoneNumber;

    @Column(name = "QUANTITY")
    private Integer  total;

    @Column(name = "RECEIVER_ADDRESS")
    private String receiverAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CUSTOMER",referencedColumnName = "ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PTTT", referencedColumnName = "ID")
    private PaymentMethod paymentMethod;

    @Column(name = "ESTIMATED_DELIVERY_DATE")
    private Date estimatedDeliveryDate;

    @Column(name = "STATUS_PAYMENT")
    private String paymentStatus;

    @Column(name = "ADDRESS_METHOD")
    private String addressMethod;

    private String note;

    @Column(name="Price")
    private BigDecimal price;

    @PrePersist
    public void prePersist() {
        // Generate code only if it is null or empty
        if (CommonUtil.isNullOrEmpty(this.code)) {
            // Use current timestamp to ensure uniqueness
            long timestamp = System.currentTimeMillis();
            this.code = "PM" + String.format("%06d", timestamp % 1000000);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 3); // Ví dụ: thêm 3 ngày từ ngày hiện tại
        this.estimatedDeliveryDate = calendar.getTime();
    }
}
