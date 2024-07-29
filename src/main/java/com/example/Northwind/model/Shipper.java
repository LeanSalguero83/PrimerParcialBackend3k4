package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Shippers")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shipperId;

    @Column(nullable = false)
    private String companyName;

    private String phone;
}