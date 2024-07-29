package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Regions")
public class Region {
    @Id
    private Integer regionId;

    @Column(nullable = false)
    private String regionDescription;
}