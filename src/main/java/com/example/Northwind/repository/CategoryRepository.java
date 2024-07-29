package com.example.Northwind.repository;

import com.example.Northwind.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryNameIgnoreCase(String categoryName);

}