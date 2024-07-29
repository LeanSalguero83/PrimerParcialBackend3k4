package com.example.Northwind.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateOrderRequestDTO {
    @NotNull(message = "Supplier ID is required")
    private Integer supplierId;

    @NotNull(message = "Category ID is required")
    private Integer categoryId;

    @NotNull(message = "Stock required is required")
    @Positive(message = "Stock required must be positive")
    private Integer stockRequired;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Shipper ID is required")
    private Integer shipperId;
}