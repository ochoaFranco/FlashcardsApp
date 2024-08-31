package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.exception.NotFoundException;
import com.Frank.flashcards_app.model.Word;
import com.Frank.flashcards_app.repository.IWordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WordService implements IWordService {
    @Autowired
    IWordRepository wordRepo;
    @Autowired
    ModelMapper modelMapper;

    // Creates a word.
    @Override
    public WordResponseDTO saveWord(WordRequestDTO wordRequestDTO) {
        Word word = modelMapper.map(wordRequestDTO, Word.class);
        return modelMapper.map(wordRepo.save(word), WordResponseDTO.class);
    }

    // Get all words.
    @Override
    public List<WordResponseDTO> getWords() {
        return wordRepo.findAll()
                .stream()
                .map(word -> modelMapper.map(word, WordResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get one word given by its ID.
    @Override
    public WordResponseDTO getWordById(Long id) {
        Optional<Word> optionalWord = wordRepo.findById(id);
        if (optionalWord.isEmpty())
            throw new NotFoundException("Word not found!");
        return modelMapper.map(optionalWord.get(), WordResponseDTO.class);
    }

    // Update a word.
    @Override
    public WordResponseDTO editWord(Long id, WordRequestDTO wordRequestDTO) {
        Optional<Word> optionalWord = wordRepo.findById(id);
        if (optionalWord.isEmpty())
            throw new NotFoundException("Word not found!");
        Word word = optionalWord.get();
        modelMapper.map(wordRequestDTO, word);
        return modelMapper.map(wordRepo.save(word), WordResponseDTO.class);
    }
    // Delete a word.
    @Override
    public void deleteWord(Long id) {
        Optional<Word> optionalWord = wordRepo.findById(id);
        if (optionalWord.isEmpty())
            throw new NotFoundException("Word not found!");
        wordRepo.deleteById(id);
    }
}
