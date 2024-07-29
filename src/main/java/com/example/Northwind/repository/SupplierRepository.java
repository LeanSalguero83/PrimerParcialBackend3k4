package com.example.Northwind.repository;

import com.example.Northwind.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findByCompanyNameIgnoreCase(String companyName);
}