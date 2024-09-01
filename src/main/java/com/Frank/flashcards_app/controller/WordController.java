package com.Frank.flashcards_app.controller;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.service.IWordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {
    @Autowired
    private IWordService wordService;

    // Create word.
    @PostMapping("/create")
    public ResponseEntity<WordResponseDTO> createWord(@Valid @RequestBody WordRequestDTO wordRequestDTO) {
        WordResponseDTO wordResponseDTO = wordService.saveWord(wordRequestDTO);
        return new ResponseEntity<>(wordResponseDTO, HttpStatus.CREATED);
    }

    // Read all words
    @GetMapping()
    public ResponseEntity<List<WordResponseDTO>> getWords() {
        return new ResponseEntity<>(wordService.getWords(), HttpStatus.OK);
    }

    // Read one category.
    @GetMapping("/{id}")
    public ResponseEntity<WordResponseDTO> getWordById(@PathVariable Long id) {
        return new ResponseEntity<>(wordService.getWordById(id), HttpStatus.OK);
    }

    // update a word by its ID.
    @PutMapping("/edit/{id}")
    public ResponseEntity<WordResponseDTO> editWord(@PathVariable Long id, @Valid @RequestBody WordRequestDTO wordRequestDTO) {
        return new ResponseEntity<>(wordService.editWord(id, wordRequestDTO), HttpStatus.OK);
    }

    // Delete a word by its ID.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWord(@PathVariable Long id) {
        wordService.deleteWord(id);
        return new ResponseEntity<>("Word deleted successfully", HttpStatus.OK);
    }

    // Assign category
    @PatchMapping("/assign-category/{id}")
    public ResponseEntity<WordResponseDTO> assignCategory(@PathVariable Long id, @RequestParam Long categoryId) {
        WordResponseDTO updatedWord = wordService.assignCategory(id, categoryId);
        return new ResponseEntity<>(updatedWord, HttpStatus.OK);
    }

    // Assign deck
    @PatchMapping("/assign-deck/{id}")
    public ResponseEntity<WordResponseDTO> assignDeck(@PathVariable Long id, @RequestParam Long deckId) {
        WordResponseDTO updatedWord = wordService.assignDeck(id, deckId);
        return new ResponseEntity<>(updatedWord, HttpStatus.OK);
    }
}
