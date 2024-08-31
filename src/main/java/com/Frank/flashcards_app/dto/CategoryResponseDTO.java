package com.Frank.flashcards_app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CategoryResponseDTO {
    private Long id;
    private String categoryName;
}
