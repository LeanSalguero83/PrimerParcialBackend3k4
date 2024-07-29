package com.example.Northwind.repository;

import com.example.Northwind.model.Territory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerritoryRepository extends JpaRepository<Territory, String> {
}