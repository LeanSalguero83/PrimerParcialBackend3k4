package com.example.Northwind.controller;

import com.example.Northwind.dto.OrderDetailDTO;
import com.example.Northwind.service.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @GetMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Integer orderId, @PathVariable Integer productId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(orderId, productId));
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetail);
    }

    @PutMapping("/{orderId}/{productId}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(
            @PathVariable Integer orderId,
            @PathVariable Integer productId,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updatedOrderDetail = orderDetailService.updateOrderDetail(orderId, productId, orderDetailDTO);
        return ResponseEntity.ok(updatedOrderDetail);
    }

    @DeleteMapping("/{orderId}/{productId}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Integer orderId, @PathVariable Integer productId) {
        orderDetailService.deleteOrderDetail(orderId, productId);
        return ResponseEntity.noContent().build();
    }
}