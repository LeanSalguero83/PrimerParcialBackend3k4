package com.example.Northwind.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EmployeeTerritories")
public class EmployeeTerritory {
    @EmbeddedId
    private EmployeeTerritoryId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @ManyToOne
    @MapsId("territoryId")
    @JoinColumn(name = "territoryId")
    private Territory territory;
}