package com.example.Northwind.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductStockDTO {
    private Integer productId;
    private String productName;
    private Integer stockFuturo;
    private BigDecimal unitPrice;
}