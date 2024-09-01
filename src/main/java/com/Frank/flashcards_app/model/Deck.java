package com.Frank.flashcards_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Deck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deckId;

    @NotEmpty
    @Column(unique = true, length = 30, nullable = false)
    private String deckName;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "deck_word",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id")
    )
    private List<Word> wordList;

    @Override
    public String toString() {
        return deckName;
    }
}
