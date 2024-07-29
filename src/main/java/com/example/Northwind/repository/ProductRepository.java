package com.example.Northwind.repository;

import com.example.Northwind.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductNameIgnoreCase(String productName);

    @Query(value = "SELECT p.ProductID as productId, p.ProductName as productName, " +
            "(p.UnitsInStock + p.UnitsOnOrder) as stockFuturo, p.UnitPrice as unitPrice " +
            "FROM Products p " +
            "WHERE p.SupplierID = :supplierId " +
            "AND p.CategoryID = :categoryId " +
            "AND (p.UnitsInStock + p.UnitsOnOrder) < :stockMin " +
            "AND p.Discontinued != '1' " +
            "ORDER BY stockFuturo ASC", nativeQuery = true)
    List<Object[]> findProductsBySupplerAndCategoryWithStockInfo(
            @Param("supplierId") Integer supplierId,
            @Param("categoryId") Integer categoryId,
            @Param("stockMin") Integer stockMin);


    @Query(value = "SELECT * FROM Products p " +
            "WHERE p.SupplierId = :supplierId " +
            "AND p.CategoryId = :categoryId " +
            "AND (p.UnitsInStock + p.UnitsOnOrder) < :stockRequired " +
            "AND p.Discontinued = '0'",
            nativeQuery = true)
    List<Product> findProductsForOrder(
            @Param("supplierId") Integer supplierId,
            @Param("categoryId") Integer categoryId,
            @Param("stockRequired") Integer stockRequired
    );
}