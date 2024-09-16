package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.exception.NotFoundException;
import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.model.Deck;
import com.Frank.flashcards_app.model.Difficulty;
import com.Frank.flashcards_app.model.Word;
import com.Frank.flashcards_app.repository.ICategoryRepository;
import com.Frank.flashcards_app.repository.IDeckRepository;
import com.Frank.flashcards_app.repository.IWordRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordService implements IWordService {
    @Autowired
    IWordRepository wordRepo;
    @Autowired
    ICategoryRepository categoryRepo;
    @Autowired
    IDeckRepository deckRepo;
    @Autowired
    ModelMapper modelMapper;

    // Creates a word.
    @Override
    public WordResponseDTO saveWord(WordRequestDTO wordRequestDTO) {
        Word word = modelMapper.map(wordRequestDTO, Word.class);
        // fetch categories using IDs from the DTO.
        Set<Long> categoryIds = wordRequestDTO.getCategoryList();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> categories = categoryRepo.findAllById(categoryIds);
            word.setCategoryList(new ArrayList<>(new HashSet<>(categories))); // Ensure unique decks
        }
        // fetch decks using IDs from the DTO
        Set<Long> deckIds = wordRequestDTO.getDeckList();
        if (deckIds != null && !deckIds.isEmpty()) {
            List<Deck> decks = deckRepo.findAllById(deckIds);
            word.setDeckList(new ArrayList<>(new HashSet<>(decks))); // Ensure unique decks
        }
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

        Word word = optionalWord.get();
        // remove the word from each deck that contains it.
        word.getDeckList()
                .forEach(deck -> {
                    deck.getWordList().remove(word);
                    deckRepo.save(deck); // actualizo la tabla intermedia.
                });
        wordRepo.deleteById(id);
    }

    // Assign a category to a certain word.
    @Override
    @Transactional
    public WordResponseDTO assignCategory(Long wordId, Long categoryId) {
        Optional<Word> optionalWord = wordRepo.findById(wordId);
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);
        // check for null categories and words.
        if (optionalWord.isEmpty() || optionalCategory.isEmpty())
            throw new NotFoundException("word or category not found!");
        // get entities.
        Word word = optionalWord.get();
        Category category = optionalCategory.get();
        if (word.getCategoryList().contains(category))
            throw new IllegalArgumentException("Category already assigned!");
        // update lists.
        category.getWordList().add(word);
        word.getCategoryList().add(category);
        // updating entities.
        categoryRepo.save(category);
        wordRepo.save(word);
        return modelMapper.map(word, WordResponseDTO.class) ;
    }

    // Assign a deck to a certain word.
    @Override
    @Transactional
    public WordResponseDTO assignDeck(Long wordId, Long deckId) {
        Optional<Word> optionalWord = wordRepo.findById(wordId);
        Optional<Deck> optionalDeck = deckRepo.findById(deckId);
        // check for null decks and words.
        if (optionalWord.isEmpty() || optionalDeck.isEmpty())
            throw new NotFoundException("word or deck not found!");
        // get entities.
        Word word = optionalWord.get();
        Deck deck = optionalDeck.get();
        if (word.getDeckList().contains(deck))
            throw new IllegalArgumentException("Deck already assigned!");
        // update lists.
        deck.getWordList().add(word);
        word.getDeckList().add(deck);
        // updating entities.
        deckRepo.save(deck);
        wordRepo.save(word);
        return modelMapper.map(word, WordResponseDTO.class) ;
    }

    // update the word's difficulty.
    @Override
    public WordResponseDTO updateDifficulty(Long id, Difficulty difficulty) {
        Optional<Word> optionalWord = wordRepo.findById(id);
        if (optionalWord.isEmpty()) {
            throw new NotFoundException("Word not found!");
        }
        Word word = optionalWord.get();
        word.setDifficulty(difficulty);
        word.setLastReviewed(LocalDate.now());
        System.out.println("last review: " + word.getLastReviewed());
        // update review time
        if (difficulty == Difficulty.normal) {
            word.setNextReviewDue(LocalDate.now().plusDays(2));
        } else if (difficulty == Difficulty.difficult) {
            word.setNextReviewDue(LocalDate.now().plusDays(1));
        } else {
            word.setNextReviewDue(LocalDate.now());
        }
        wordRepo.save(word);
        return modelMapper.map(word, WordResponseDTO.class);
    }

    @Override
    public List<WordResponseDTO> searchWordsByPrefix(String prefix) {
        return wordRepo.findBywordNameStartingWithIgnoreCase(prefix).stream()
                .map(word -> modelMapper.map(word, WordResponseDTO.class))
                .collect(Collectors.toList());
    }
}
