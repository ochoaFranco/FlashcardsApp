package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.WordDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordService implements IWordService {

    @Override
    public WordDTO saveWord(WordDTO wordDTO) {
        return null;
    }

    @Override
    public List<WordDTO> getWords() {
        return List.of();
    }

    @Override
    public WordDTO getWordById(Long id) {
        return null;
    }

    @Override
    public WordDTO editWord(Long id, WordDTO wordDTO) {
        return null;
    }

    @Override
    public void deleteWord(Long id) {

    }
}
