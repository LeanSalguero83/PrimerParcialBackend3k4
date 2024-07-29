package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Territories")
public class Territory {
    @Id
    @Column(length = 20)
    private String territoryId;

    @Column(nullable = false)
    private String territoryDescription;

    @ManyToOne
    @JoinColumn(name = "regionId", nullable = false)
    private Region region;
}