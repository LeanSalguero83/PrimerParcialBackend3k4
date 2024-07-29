package com.example.Northwind.service;

import com.example.Northwind.dto.SupplierDTO;
import com.example.Northwind.exception.EntityAlreadyExistsException;
import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.model.Supplier;
import com.example.Northwind.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplierDTO getSupplierById(Integer id) {
        return supplierRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Supplier", id));
    }

    @Transactional
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        if (supplierRepository.findByCompanyNameIgnoreCase(supplierDTO.getCompanyName()).isPresent()) {
            throw new EntityAlreadyExistsException("Supplier", "companyName", supplierDTO.getCompanyName());
        }
        Supplier supplier = convertToEntity(supplierDTO);
        return convertToDTO(supplierRepository.save(supplier));
    }

    @Transactional
    public SupplierDTO updateSupplier(Integer id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supplier", id));
        updateSupplierFromDTO(supplier, supplierDTO);
        return convertToDTO(supplierRepository.save(supplier));
    }

    @Transactional
    public void deleteSupplier(Integer id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException("Supplier", id);
        }
        supplierRepository.deleteById(id);
    }

    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO dto = new SupplierDTO();
        dto.setCompanyName(supplier.getCompanyName());
        dto.setContactName(supplier.getContactName());
        dto.setContactTitle(supplier.getContactTitle());
        dto.setAddress(supplier.getAddress());
        dto.setCity(supplier.getCity());
        dto.setRegion(supplier.getRegion());
        dto.setPostalCode(supplier.getPostalCode());
        dto.setCountry(supplier.getCountry());
        dto.setPhone(supplier.getPhone());
        dto.setFax(supplier.getFax());
        dto.setHomePage(supplier.getHomePage());
        return dto;
    }

    private Supplier convertToEntity(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactName(dto.getContactName());
        supplier.setContactTitle(dto.getContactTitle());
        supplier.setAddress(dto.getAddress());
        supplier.setCity(dto.getCity());
        supplier.setRegion(dto.getRegion());
        supplier.setPostalCode(dto.getPostalCode());
        supplier.setCountry(dto.getCountry());
        supplier.setPhone(dto.getPhone());
        supplier.setFax(dto.getFax());
        supplier.setHomePage(dto.getHomePage());
        return supplier;
    }

    private void updateSupplierFromDTO(Supplier supplier, SupplierDTO dto) {
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setContactName(dto.getContactName());
        supplier.setContactTitle(dto.getContactTitle());
        supplier.setAddress(dto.getAddress());
        supplier.setCity(dto.getCity());
        supplier.setRegion(dto.getRegion());
        supplier.setPostalCode(dto.getPostalCode());
        supplier.setCountry(dto.getCountry());
        supplier.setPhone(dto.getPhone());
        supplier.setFax(dto.getFax());
        supplier.setHomePage(dto.getHomePage());
    }
}