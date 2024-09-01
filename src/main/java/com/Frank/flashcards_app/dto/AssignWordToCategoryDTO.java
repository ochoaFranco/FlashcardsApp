package com.Frank.flashcards_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class AssignWordToCategoryDTO {
    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Word ID is required")
    private Long wordyId;
}
