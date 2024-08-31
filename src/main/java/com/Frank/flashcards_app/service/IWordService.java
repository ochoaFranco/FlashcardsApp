package com.Frank.flashcards_app.service;
import com.Frank.flashcards_app.dto.WordDTO;
import java.util.List;

public interface IWordService {
    // Creates a word
    WordDTO saveWord(WordDTO wordDTO);
    // Get all words.
    List<WordDTO> getWords();
    // Get one word.
    WordDTO getWordById(Long id);
    // Update a word
    WordDTO editWord(Long id, WordDTO wordDTO);
    // Delete a word
    void deleteWord(Long id);
}
