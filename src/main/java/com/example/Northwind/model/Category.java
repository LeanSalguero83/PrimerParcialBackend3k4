package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String description;
}