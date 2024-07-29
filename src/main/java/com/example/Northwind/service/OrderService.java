package com.example.Northwind.service;

import com.example.Northwind.dto.CreateOrderRequestDTO;
import com.example.Northwind.dto.CreatedOrderResponseDTO;
import com.example.Northwind.dto.OrderDTO;
import com.example.Northwind.dto.OrderDetailDTO;
import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.exception.InvalidOperationException;
import com.example.Northwind.exception.NoContentException;
import com.example.Northwind.model.*;
import com.example.Northwind.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ShipperRepository shipperRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        EmployeeRepository employeeRepository,
                        ShipperRepository shipperRepository,
                        ProductService productService,
                        OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.shipperRepository = shipperRepository;
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        updateOrderFromDTO(order, orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order", id));
        updateOrderFromDTO(order, orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order", id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setEmployeeId(order.getEmployee().getEmployeeId());
        dto.setOrderDate(order.getOrderDate());
        dto.setRequiredDate(order.getRequiredDate());
        dto.setShippedDate(order.getShippedDate());
        dto.setShipperId(order.getShipper().getShipperId());
        dto.setFreight(order.getFreight());
        dto.setShipName(order.getShipName());
        dto.setShipAddress(order.getShipAddress());
        dto.setShipCity(order.getShipCity());
        dto.setShipRegion(order.getShipRegion());
        dto.setShipPostalCode(order.getShipPostalCode());
        dto.setShipCountry(order.getShipCountry());
        return dto;
    }

    private void updateOrderFromDTO(Order order, OrderDTO dto) {
        if (dto.getOrderDate() == null) {
            throw new InvalidOperationException("Order date is required");
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer", dto.getCustomerId()));
        order.setCustomer(customer);

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", dto.getEmployeeId()));
        order.setEmployee(employee);

        Shipper shipper = shipperRepository.findById(dto.getShipperId())
                .orElseThrow(() -> new EntityNotFoundException("Shipper", dto.getShipperId()));
        order.setShipper(shipper);

        order.setOrderDate(dto.getOrderDate());
        order.setRequiredDate(dto.getRequiredDate());
        order.setShippedDate(dto.getShippedDate());
        order.setFreight(dto.getFreight());
        order.setShipName(dto.getShipName());
        order.setShipAddress(dto.getShipAddress());
        order.setShipCity(dto.getShipCity());
        order.setShipRegion(dto.getShipRegion());
        order.setShipPostalCode(dto.getShipPostalCode());
        order.setShipCountry(dto.getShipCountry());
    }

    @Transactional
    public CreatedOrderResponseDTO createOrderWithDetails(CreateOrderRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer", requestDTO.getCustomerId()));
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee", requestDTO.getEmployeeId()));
        Shipper shipper = shipperRepository.findById(requestDTO.getShipperId())
                .orElseThrow(() -> new EntityNotFoundException("Shipper", requestDTO.getShipperId()));

        List<Product> productsToOrder = productService.getProductsForOrder(
                requestDTO.getSupplierId(),
                requestDTO.getCategoryId(),
                requestDTO.getStockRequired()
        );

        if (productsToOrder.isEmpty()) {
            throw new NoContentException("No products found matching the criteria");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setEmployee(employee);
        order.setOrderDate(LocalDateTime.now());
        order.setRequiredDate(LocalDateTime.now().plusDays(7)); // Example: required date is 7 days from now
        order.setShipper(shipper);
        order.setFreight(BigDecimal.ZERO);
        order.setShipName(customer.getCompanyName());
        order.setShipAddress(customer.getAddress());
        order.setShipCity(customer.getCity());
        order.setShipRegion(customer.getRegion());
        order.setShipPostalCode(customer.getPostalCode());
        order.setShipCountry(customer.getCountry());

        order = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        int totalProductsOrdered = 0;

        for (Product product : productsToOrder) {
            int quantityToOrder = requestDTO.getStockRequired() - (product.getUnitsInStock() + product.getUnitsOnOrder());
            float discount = quantityToOrder >= 100 ? 0.10f : 0f;

            OrderDetail orderDetail = new OrderDetail();
            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setOrderId(order.getOrderId());
            orderDetailId.setProductId(product.getProductId());
            orderDetail.setId(orderDetailId);
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setUnitPrice(product.getUnitPrice());
            orderDetail.setQuantity(quantityToOrder);
            orderDetail.setDiscount(discount);

            orderDetails.add(orderDetail);
            totalProductsOrdered += quantityToOrder;
        }

        orderDetailRepository.saveAll(orderDetails);

        CreatedOrderResponseDTO responseDTO = new CreatedOrderResponseDTO();
        responseDTO.setOrder(convertToDTO(order));
        responseDTO.setOrderDetails(orderDetails.stream().map(this::convertOrderDetailToDTO).collect(Collectors.toList()));
        responseDTO.setTotalProductsOrdered(totalProductsOrdered);

        return responseDTO;
    }

    private OrderDetailDTO convertOrderDetailToDTO(OrderDetail orderDetail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setOrderId(orderDetail.getId().getOrderId());
        dto.setProductId(orderDetail.getId().getProductId());
        dto.setUnitPrice(orderDetail.getUnitPrice());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setDiscount(orderDetail.getDiscount());
        return dto;
    }
}