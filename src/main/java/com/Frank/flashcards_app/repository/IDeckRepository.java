package com.Frank.flashcards_app.repository;

import com.Frank.flashcards_app.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeckRepository extends JpaRepository<Deck, Long> {
    boolean existsByDeckName(String name);
}
