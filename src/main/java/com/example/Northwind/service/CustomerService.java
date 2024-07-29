package com.example.Northwind.service;

import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.model.Customer;
import com.example.Northwind.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(generateCustomerId(customer.getCompanyName()));
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(String id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer", id));

        customer.setCompanyName(customerDetails.getCompanyName());
        customer.setContactName(customerDetails.getContactName());
        customer.setContactTitle(customerDetails.getContactTitle());
        customer.setAddress(customerDetails.getAddress());
        customer.setCity(customerDetails.getCity());
        customer.setRegion(customerDetails.getRegion());
        customer.setPostalCode(customerDetails.getPostalCode());
        customer.setCountry(customerDetails.getCountry());
        customer.setPhone(customerDetails.getPhone());
        customer.setFax(customerDetails.getFax());

        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer", id);
        }
        customerRepository.deleteById(id);
    }

    private String generateCustomerId(String companyName) {
        String prefix = companyName.substring(0, Math.min(2, companyName.length())).toUpperCase();
        String suffix = String.format("%03d", new Random().nextInt(1000));
        return (prefix + suffix).substring(0, Math.min(5, prefix.length() + suffix.length()));
    }
}