package com.Frank.flashcards_app.repository;

import com.Frank.flashcards_app.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWordRepository extends JpaRepository<Word, Long> {
    // Find words that starts with the given prefix.
    List<Word> findBywordNameStartingWithIgnoreCase(String prefix);
}
