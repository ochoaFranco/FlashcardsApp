package com.Frank.flashcards_app.dto;

import com.Frank.flashcards_app.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WordResponseDTO {
    private Long wordId;
    private String wordName;
    private String meaning;
    private Difficulty difficulty;
    private List<String> categoryNames;
    private List<String> deckNames;
    private LocalDate lastReviewed;
    private LocalDate nextReviewDue;
}
