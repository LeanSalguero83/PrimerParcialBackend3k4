package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "`Order Details`")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float discount;
}