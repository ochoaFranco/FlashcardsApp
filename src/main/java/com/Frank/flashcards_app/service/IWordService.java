package com.Frank.flashcards_app.service;
import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.model.Difficulty;

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
    WordResponseDTO assignCategory(Long wordId, Long categoryId);
    // assign deck
    WordResponseDTO assignDeck(Long wordId, Long deckId);
    // update word's difficulty
    WordResponseDTO updateDifficulty(Long id, Difficulty difficulty);
}
