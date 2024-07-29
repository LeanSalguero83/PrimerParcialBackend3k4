package com.example.Northwind.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailDTO {
    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Discount is required")
    @PositiveOrZero(message = "Discount must be zero or positive")
    private Float discount;
}