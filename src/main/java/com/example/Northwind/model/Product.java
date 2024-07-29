package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "supplierId")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String quantityPerUnit;

    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    private Integer unitsInStock;
    private Integer unitsOnOrder;
    private Integer reorderLevel;

    @Column(name = "Discontinued", nullable = false, columnDefinition = "ENUM('0', '1') DEFAULT '0'")
    private String discontinued;

    // Método de conveniencia para trabajar con booleanos
    @Transient
    public boolean isDiscontinued() {
        return "1".equals(this.discontinued);
    }

    // Método de conveniencia para establecer el valor
    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued ? "1" : "0";
    }
}