package com.Frank.flashcards_app.repository;

import com.Frank.flashcards_app.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWordRepository extends JpaRepository<Word, Long> {
}
