package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.CategoryRequestDTO;
import com.Frank.flashcards_app.dto.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {
    // Creates category
    CategoryResponseDTO saveCategory(CategoryRequestDTO categoryRequestDTO);
    // Get all categories.
    List<CategoryResponseDTO> getCategories();
    // Get one category.
    CategoryResponseDTO getCategoryById(Long id);
    // Update a category
    CategoryResponseDTO editCategory(Long id, CategoryRequestDTO categoryRequestDTO);
    // Delete a category
    void deleteCategory(Long id);
 }
