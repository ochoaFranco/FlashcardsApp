package com.Frank.flashcards_app.mapper;

import com.Frank.flashcards_app.dto.CategoryDTO;
import com.Frank.flashcards_app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(source = "categoryName", target = "categoryName")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "categoryName", target = "categoryName")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    // Method to map fields from CategoryDTO to Category entity
    @Mapping(source = "categoryName", target = "categoryName")
    void updateCategoryFromDTO(CategoryDTO categoryDTO, @MappingTarget Category category);
}
