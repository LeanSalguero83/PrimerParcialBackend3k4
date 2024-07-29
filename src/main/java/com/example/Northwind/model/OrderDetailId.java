package com.example.Northwind.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class OrderDetailId implements Serializable {
    private Integer orderId;
    private Integer productId;
}