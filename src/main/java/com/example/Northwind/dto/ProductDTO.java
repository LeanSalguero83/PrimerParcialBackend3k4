package com.example.Northwind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must be less than 255 characters")
    private String productName;

    private Integer supplierId;
    private Integer categoryId;

    private String quantityPerUnit;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;

    private boolean discontinued;
}