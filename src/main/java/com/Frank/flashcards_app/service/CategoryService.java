package com.Frank.flashcards_app.service;
import com.Frank.flashcards_app.dto.CategoryDTO;
import com.Frank.flashcards_app.exception.DuplicateCategoryNameException;
import com.Frank.flashcards_app.exception.NotFoundException;
import com.Frank.flashcards_app.mapper.CategoryMapper;
import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO I SHOULD CREATE A UTILS PACKAGE AND ADD FUNCTIONALITY FOR FINDING AN ID.

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    ICategoryRepository categoryRepo;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        // check if category already exists.
        if (categoryRepo.existsByName(category.getCategoryName()))
            throw new DuplicateCategoryNameException("Category already exists");
        return categoryMapper.categoryToCategoryDTO(categoryRepo.save(category));
    }

    // Get all categories.
    @Override
    public List<CategoryDTO> getCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(category -> categoryMapper.categoryToCategoryDTO(category))
                .collect(Collectors.toList());
    }

    // Get one category given by its ID.
    @Override
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        return categoryMapper.categoryToCategoryDTO(optionalCategory.get());
    }

    // Update a category.
    @Override
    public CategoryDTO editCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        Category category = optionalCategory.get();
        categoryMapper.updateCategoryFromDTO(categoryDTO, category);
        return categoryMapper.categoryToCategoryDTO(categoryRepo.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        categoryRepo.deleteById(id);
    }
}
