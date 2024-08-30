package com.Frank.flashcards_app.service;


import com.Frank.flashcards_app.dto.CategoryDTO;
import com.Frank.flashcards_app.mapper.CategoryMapper;
import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepo;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        return categoryMapper.categoryToCategoryDTO(categoryRepo.save(category));
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return List.of();
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryDTO editCategory(Long id, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }


}
