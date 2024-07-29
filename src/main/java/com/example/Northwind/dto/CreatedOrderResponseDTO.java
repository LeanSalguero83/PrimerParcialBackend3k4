package com.example.Northwind.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreatedOrderResponseDTO {
    private OrderDTO order;
    private List<OrderDetailDTO> orderDetails;
    private int totalProductsOrdered;
}