package com.example.Northwind.service;

import com.example.Northwind.dto.ProductDTO;
import com.example.Northwind.dto.ProductStockDTO;
import com.example.Northwind.exception.EntityAlreadyExistsException;
import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.model.Product;
import com.example.Northwind.model.Category;
import com.example.Northwind.model.Supplier;
import com.example.Northwind.repository.ProductRepository;
import com.example.Northwind.repository.CategoryRepository;
import com.example.Northwind.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Integer id) {
        return productRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productRepository.findByProductNameIgnoreCase(productDTO.getProductName()).isPresent()) {
            throw new EntityAlreadyExistsException("Product", "name", productDTO.getProductName());
        }
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
        updateProductFromDTO(product, productDTO);
        return convertToDTO(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductName(product.getProductName());
        dto.setSupplierId(product.getSupplier() != null ? product.getSupplier().getSupplierId() : null);
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getCategoryId() : null);
        dto.setQuantityPerUnit(product.getQuantityPerUnit());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setUnitsInStock(product.getUnitsInStock());
        dto.setUnitsOnOrder(product.getUnitsOnOrder());
        dto.setReorderLevel(product.getReorderLevel());
        dto.setDiscontinued(product.isDiscontinued());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier", dto.getSupplierId()));
            product.setSupplier(supplier);
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category", dto.getCategoryId()));
            product.setCategory(category);
        }
        product.setQuantityPerUnit(dto.getQuantityPerUnit());
        product.setUnitPrice(dto.getUnitPrice());
        product.setUnitsInStock(dto.getUnitsInStock());
        product.setUnitsOnOrder(dto.getUnitsOnOrder());
        product.setReorderLevel(dto.getReorderLevel());
        product.setDiscontinued(dto.isDiscontinued());
        return product;
    }

    private void updateProductFromDTO(Product product, ProductDTO dto) {
        product.setProductName(dto.getProductName());
        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new EntityNotFoundException("Supplier", dto.getSupplierId()));
            product.setSupplier(supplier);
        } else {
            product.setSupplier(null);
        }
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category", dto.getCategoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }
        product.setQuantityPerUnit(dto.getQuantityPerUnit());
        product.setUnitPrice(dto.getUnitPrice());
        product.setUnitsInStock(dto.getUnitsInStock());
        product.setUnitsOnOrder(dto.getUnitsOnOrder());
        product.setReorderLevel(dto.getReorderLevel());
        product.setDiscontinued(dto.isDiscontinued());
    }

    @Transactional(readOnly = true)
    public List<ProductStockDTO> getProductsBySupplierCategoryAndStock(Integer supplierId, Integer categoryId, Integer stockMin) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId);
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category", categoryId);
        }

        List<Object[]> results = productRepository.findProductsBySupplerAndCategoryWithStockInfo(supplierId, categoryId, stockMin);

        return results.stream()
                .map(this::mapToProductStockDTO)
                .collect(Collectors.toList());
    }

    private ProductStockDTO mapToProductStockDTO(Object[] result) {
        ProductStockDTO dto = new ProductStockDTO();
        dto.setProductId((Integer) result[0]);
        dto.setProductName((String) result[1]);
        dto.setStockFuturo(((Number) result[2]).intValue());
        dto.setUnitPrice((BigDecimal) result[3]);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsForOrder(Integer supplierId, Integer categoryId, Integer stockRequired) {
        // Verificar existencia de proveedor y categoría
        if (!supplierRepository.existsById(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId);
        }
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category", categoryId);
        }

        // Usar una consulta específica en lugar de filtrar todos los productos
        return productRepository.findProductsForOrder(supplierId, categoryId, stockRequired);
    }
}