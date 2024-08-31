package com.Frank.flashcards_app.service;
import com.Frank.flashcards_app.dto.CategoryRequestDTO;
import com.Frank.flashcards_app.dto.CategoryResponseDTO;
import com.Frank.flashcards_app.exception.DuplicateCategoryNameException;
import com.Frank.flashcards_app.exception.NotFoundException;
import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.repository.ICategoryRepository;
import org.modelmapper.ModelMapper;
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
    ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        // check if category already exists.
        if (categoryRepo.existsByCategoryName(category.getCategoryName()))
            throw new DuplicateCategoryNameException("Category already exists");
//        return categoryMapper.categoryToCategoryDTO(categoryRepo.save(category));
        return modelMapper.map(categoryRepo.save(category), CategoryResponseDTO.class);
    }

    // Get all categories.
    @Override
    public List<CategoryResponseDTO> getCategories() {
        return categoryRepo.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get one category given by its ID.
    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        return modelMapper.map(optionalCategory.get(), CategoryResponseDTO.class);
    }

    // Update a category.
    @Override
    public CategoryResponseDTO editCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        Category category = optionalCategory.get();
        modelMapper.map(categoryRequestDTO, category);
        return modelMapper.map(categoryRepo.save(category),CategoryResponseDTO.class);
    }

    // Delete a category.
    @Override
    public void deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        // throw an error if category does not exist.
        if (optionalCategory.isEmpty())
            throw new NotFoundException("Category not found!");
        categoryRepo.deleteById(id);
    }
}
