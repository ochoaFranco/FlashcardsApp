package com.Frank.flashcards_app.mapper;

import com.Frank.flashcards_app.dto.CategoryDTO;
import com.Frank.flashcards_app.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    @Mapping(target = "id", ignore = true)
    CategoryDTO saveCategoryDTOWithoutId(Category category);
}
