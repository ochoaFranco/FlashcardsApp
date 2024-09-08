package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.DeckRequestDTO;
import com.Frank.flashcards_app.dto.DeckResponseDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;

import java.util.List;

public interface IDeckService {
    // Creates a deck
    DeckResponseDTO saveDeck(DeckRequestDTO deckRequestDTO);
    // Get all decks.
    List<DeckResponseDTO> getDecks();
    // Get one deck.
    DeckResponseDTO getDeckById(Long id);
    // Update a deck
    DeckResponseDTO editDeck(Long id, DeckRequestDTO deckRequestDTO);
    // Delete a deck
    void deleteDeck(Long id);
    // Assign a word.
    DeckResponseDTO assignWord(Long deckId, Long wordId);
    // Get all words from a certain deck.
    List<WordResponseDTO> getFlashcards(Long deckId);
}
