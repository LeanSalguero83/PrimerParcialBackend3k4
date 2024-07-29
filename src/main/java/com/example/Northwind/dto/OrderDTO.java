package com.example.Northwind.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Integer orderId;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @PastOrPresent(message = "Order date must be in the past or present")
    private LocalDateTime orderDate;

    private LocalDateTime requiredDate;
    private LocalDateTime shippedDate;

    @NotNull(message = "Shipper ID is required")
    private Integer shipperId;

    private BigDecimal freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
}