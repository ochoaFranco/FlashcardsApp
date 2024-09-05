package com.Frank.flashcards_app.dto;


import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.model.Deck;
import com.Frank.flashcards_app.model.Difficulty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WordRequestDTO {
    @NotEmpty(message = "Word name must not be empty")
    @Size(max = 30, message = "Word name must not exceed 30 characters")
    private String wordName;
    private Difficulty difficulty;

    @NotEmpty(message = "Meaning must not be empty")
    @Size(max = 300, message = "Word name must not exceed 300 characters")
    private String meaning;
    private Set<Long> categoryList; // IDs of the categories to be assigned
    private Set<Long> deckList; // IDs of the decks to be assigned
}