package com.Frank.flashcards_app.service;
import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;

import java.util.List;

public interface IWordService {
    // Creates a word
    WordResponseDTO saveWord(WordRequestDTO wordRequestDTO);
    // Get all words.
    List<WordResponseDTO> getWords();
    // Get one word.
    WordResponseDTO getWordById(Long id);
    // Update a word
    WordResponseDTO editWord(Long id, WordRequestDTO wordRequestDTO);
    // Delete a word
    void deleteWord(Long id);
    // assign category
    void assignCategory(Long wordId, Long categoryId);
}
