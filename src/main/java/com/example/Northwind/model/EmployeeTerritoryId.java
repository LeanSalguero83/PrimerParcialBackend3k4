package com.example.Northwind.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class EmployeeTerritoryId implements Serializable {
    private Integer employeeId;
    @Column(length = 20)
    private String territoryId;
}