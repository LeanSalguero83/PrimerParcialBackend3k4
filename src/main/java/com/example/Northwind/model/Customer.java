package com.example.Northwind.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @Column(length = 5)
    @NotBlank(message = "Customer ID is required")
    @Size(max = 5, message = "Customer ID must be at most 5 characters")
    private String customerId;

    @NotBlank(message = "Company name is required")
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
}