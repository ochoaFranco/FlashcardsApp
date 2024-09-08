package com.Frank.flashcards_app.controller;

import com.Frank.flashcards_app.dto.DeckRequestDTO;
import com.Frank.flashcards_app.dto.DeckResponseDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.service.IDeckService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {
    @Autowired
    IDeckService deckService;

    // Create deck.
    @PostMapping("/create")
    public ResponseEntity<DeckResponseDTO> createDeck(@Valid @RequestBody DeckRequestDTO deckRequestDTO) {
        DeckResponseDTO response = deckService.saveDeck(deckRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read all decks.
    @GetMapping()
    public ResponseEntity<List<DeckResponseDTO>> getDecks() {
        return new ResponseEntity<>(deckService.getDecks(), HttpStatus.OK);
    }

    // Read one deck.
    @GetMapping("/{id}")
    public ResponseEntity<DeckResponseDTO> getDeckById(@PathVariable Long id) {
        return new ResponseEntity<>(deckService.getDeckById(id), HttpStatus.OK);
    }

    // Read all words.
    @GetMapping("/{id}/flashcards")
    public ResponseEntity<List<WordResponseDTO>> getFlashcards(@PathVariable Long id) {
        return new ResponseEntity<>(deckService.getFlashcards(id), HttpStatus.OK);
    }

    // update a deck by its ID.
    @PutMapping("/edit/{id}")
    public ResponseEntity<DeckResponseDTO> editDeck(@PathVariable Long id, @Valid @RequestBody DeckRequestDTO deckRequestDTO) {
        return new ResponseEntity<>(deckService.editDeck(id, deckRequestDTO), HttpStatus.OK);
    }

    // Delete a deck by its ID.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
        return new ResponseEntity<>("Deck deleted successfully", HttpStatus.OK);
    }

    // Assign a word.
    @PatchMapping("/assign-word/{deckId}")
    public ResponseEntity<DeckResponseDTO> assignWord(@PathVariable Long deckId, @RequestParam Long wordId) {
        DeckResponseDTO updatedDeck = deckService.assignWord(deckId, wordId);
        return new ResponseEntity<>(updatedDeck, HttpStatus.OK);
    }
}
