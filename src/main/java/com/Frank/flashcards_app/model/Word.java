package com.Frank.flashcards_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;
    @NotEmpty
    private String meaning;
    private Difficulty difficulty;
    @NotEmpty
    @Column(unique = true, length = 30)
    private String wordName;
    private LocalDateTime lastReviewed;
    private LocalDateTime nextReviewDue;
}
