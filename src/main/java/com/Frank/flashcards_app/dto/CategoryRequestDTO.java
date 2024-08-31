package com.Frank.flashcards_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CategoryRequestDTO {
    @NotEmpty(message = "Category name must not be empty")
    private String categoryName;
}
