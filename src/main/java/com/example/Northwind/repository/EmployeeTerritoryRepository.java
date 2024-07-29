package com.example.Northwind.repository;

import com.example.Northwind.model.EmployeeTerritory;
import com.example.Northwind.model.EmployeeTerritoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTerritoryRepository extends JpaRepository<EmployeeTerritory, EmployeeTerritoryId> {
}