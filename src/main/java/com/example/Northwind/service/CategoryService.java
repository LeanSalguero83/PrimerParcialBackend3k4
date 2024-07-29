package com.example.Northwind.service;

import com.example.Northwind.dto.CategoryDTO;
import com.example.Northwind.exception.EntityAlreadyExistsException;
import com.example.Northwind.exception.EntityNotFoundException;
import com.example.Northwind.model.Category;
import com.example.Northwind.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByCategoryNameIgnoreCase(categoryDTO.getCategoryName()).isPresent()) {
            throw new EntityAlreadyExistsException("Category", "name", categoryDTO.getCategoryName());
        }
        Category category = convertToEntity(categoryDTO);
        return convertToDTO(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
        updateCategoryFromDTO(category, categoryDTO);
        return convertToDTO(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category", id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        return category;
    }

    private void updateCategoryFromDTO(Category category, CategoryDTO dto) {
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
    }
}