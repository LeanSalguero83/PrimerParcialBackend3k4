package com.example.Northwind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    @NotBlank(message = "Category name is required")
    @Size(max = 255, message = "Category name must be less than 255 characters")
    private String categoryName;

    private String description;

}