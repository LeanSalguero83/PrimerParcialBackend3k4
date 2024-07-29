package com.example.Northwind.service;

import com.example.Northwind.dto.OrderDetailDTO;
import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.model.OrderDetail;
import com.example.Northwind.model.OrderDetailId;
import com.example.Northwind.model.Order;
import com.example.Northwind.model.Product;
import com.example.Northwind.repository.OrderDetailRepository;
import com.example.Northwind.repository.OrderRepository;
import com.example.Northwind.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository,
                              OrderRepository orderRepository,
                              ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDetailDTO getOrderDetailById(Integer orderId, Integer productId) {
        OrderDetailId id = new OrderDetailId();
        id.setOrderId(orderId);
        id.setProductId(productId);
        return orderDetailRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetail", id));
    }

    @Transactional
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        updateOrderDetailFromDTO(orderDetail, orderDetailDTO);
        return convertToDTO(orderDetailRepository.save(orderDetail));
    }

    @Transactional
    public OrderDetailDTO updateOrderDetail(Integer orderId, Integer productId, OrderDetailDTO orderDetailDTO) {
        OrderDetailId id = new OrderDetailId();
        id.setOrderId(orderId);
        id.setProductId(productId);
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetail", id));
        updateOrderDetailFromDTO(orderDetail, orderDetailDTO);
        return convertToDTO(orderDetailRepository.save(orderDetail));
    }

    @Transactional
    public void deleteOrderDetail(Integer orderId, Integer productId) {
        OrderDetailId id = new OrderDetailId();
        id.setOrderId(orderId);
        id.setProductId(productId);
        if (!orderDetailRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderDetail", id);
        }
        orderDetailRepository.deleteById(id);
    }

    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderId(orderDetail.getId().getOrderId());
        dto.setProductId(orderDetail.getId().getProductId());
        dto.setUnitPrice(orderDetail.getUnitPrice());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setDiscount(orderDetail.getDiscount());
        return dto;
    }

    private void updateOrderDetailFromDTO(OrderDetail orderDetail, OrderDetailDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order", dto.getOrderId()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product", dto.getProductId()));

        OrderDetailId id = new OrderDetailId();
        id.setOrderId(dto.getOrderId());
        id.setProductId(dto.getProductId());
        orderDetail.setId(id);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setUnitPrice(dto.getUnitPrice());
        orderDetail.setQuantity(dto.getQuantity());
        orderDetail.setDiscount(dto.getDiscount());
    }
}