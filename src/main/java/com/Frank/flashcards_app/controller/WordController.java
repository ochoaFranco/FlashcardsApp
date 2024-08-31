package com.Frank.flashcards_app.controller;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.service.IWordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
