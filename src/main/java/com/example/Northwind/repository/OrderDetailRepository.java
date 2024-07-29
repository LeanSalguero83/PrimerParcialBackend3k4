package com.example.Northwind.repository;

import com.example.Northwind.model.OrderDetail;
import com.example.Northwind.model.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}