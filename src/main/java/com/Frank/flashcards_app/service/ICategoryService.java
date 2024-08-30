package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    // Creates category
    CategoryDTO saveCategory(CategoryDTO categoryDTO);
    // Get all categories.
    List<CategoryDTO> getCategories();
    // Get one category.
    CategoryDTO getCategoryById(Long id);
    // Update a category
    CategoryDTO editCategory(Long id, CategoryDTO categoryDTO);
    // Delete a category
    void deleteCategory(Long id);
 }
