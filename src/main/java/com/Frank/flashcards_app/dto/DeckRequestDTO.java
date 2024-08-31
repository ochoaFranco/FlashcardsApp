package com.Frank.flashcards_app.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DeckRequestDTO {
    @NotEmpty(message = "Deck name must not be empty")
    @Column(unique = true, length = 30, nullable = false)
    private String deckName;

    @Size(max = 255, message = "Description should not exceed 255 characters")
    private String description;

    private List<Long> wordIds;
}
