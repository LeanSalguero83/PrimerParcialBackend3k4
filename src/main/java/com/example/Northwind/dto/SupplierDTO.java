package com.example.Northwind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDTO {
    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must be less than 255 characters")
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
    private String homePage;
}