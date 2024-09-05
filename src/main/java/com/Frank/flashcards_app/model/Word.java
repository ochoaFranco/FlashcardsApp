package com.Frank.flashcards_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    private String meaning;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @NotEmpty(message = "Word name must not be empty")
    @Column(unique = true, length = 30, nullable = false)
    private String wordName;
    private LocalDateTime lastReviewed;
    private LocalDateTime nextReviewDue;
    @ManyToMany(mappedBy = "wordList")
    private List<Deck> deckList;
    @ManyToMany
    @JoinTable(
            name = "word_category",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categoryList;
}
