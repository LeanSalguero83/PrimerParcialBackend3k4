package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employeeId")
    private Employee employee;

    private LocalDateTime orderDate;
    private LocalDateTime requiredDate;
    private LocalDateTime shippedDate;

    @ManyToOne
    @JoinColumn(name = "shipVia")
    private Shipper shipper;

    @Column(precision = 10, scale = 2)
    private BigDecimal freight;

    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
}