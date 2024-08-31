package com.Frank.flashcards_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DeckResponseDTO {
    private Long deckId;
    private String deckName;
    private String description;
    private List<WordResponseDTO> words;
}
