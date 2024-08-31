package com.Frank.flashcards_app.mapper;

import com.Frank.flashcards_app.dto.CategoryDTO;
import com.Frank.flashcards_app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    // Method to map fields from CategoryDTO to Category entity
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
