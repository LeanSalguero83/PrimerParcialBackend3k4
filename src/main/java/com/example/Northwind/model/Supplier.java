package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    @Column(nullable = false)
    private String companyName;

    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;

    @Column(columnDefinition = "TEXT")
    private String homePage;
}