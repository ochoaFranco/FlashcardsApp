package com.Frank.flashcards_app.service;

import com.Frank.flashcards_app.dto.DeckRequestDTO;
import com.Frank.flashcards_app.dto.DeckResponseDTO;
import com.Frank.flashcards_app.exception.DuplicateNameException;
import com.Frank.flashcards_app.exception.NotFoundException;
import com.Frank.flashcards_app.model.Deck;
import com.Frank.flashcards_app.model.Word;
import com.Frank.flashcards_app.repository.IDeckRepository;
import com.Frank.flashcards_app.repository.IWordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeckService implements IDeckService {
    @Autowired
    IDeckRepository deckRepo;
    @Autowired
    IWordRepository wordRepo;
    @Autowired
    ModelMapper modelMapper;

    // Creates a deck.
    @Override
    public DeckResponseDTO saveDeck(DeckRequestDTO deckRequestDTO) {
        Deck deck = modelMapper.map(deckRequestDTO, Deck.class);
        // fetch words using IDs from the DTO.
        Set<Long> wordIds = deckRequestDTO.getWordIds();

        // Assign words to the deck.
        if (wordIds != null && !wordIds.isEmpty()) {
            List<Word> words = wordRepo.findAllById(wordIds);
            deck.setWordList(new ArrayList<>(new HashSet<>(words)));
        }
        // check if deck already exists.
        if (deckRepo.existsByDeckName(deck.getDeckName()))
            throw new DuplicateNameException("Deck already exists");

        return modelMapper.map(deckRepo.save(deck), DeckResponseDTO.class);
    }

    // Get all decks.
    @Override
    public List<DeckResponseDTO> getDecks() {
        return deckRepo.findAll()
                .stream()
                .map(deck -> modelMapper.map(deck, DeckResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get one deck given by its ID.
    @Override
    public DeckResponseDTO getDeckById(Long id) {
        Optional<Deck> optionalDeck = deckRepo.findById(id);
        // throw an error if deck does not exist.
        if (optionalDeck.isEmpty())
            throw new NotFoundException("deck not found!");
        return modelMapper.map(optionalDeck.get(), DeckResponseDTO.class);
    }

    // Update a deck.
    public DeckResponseDTO editDeck(Long id, DeckRequestDTO deckRequestDTO) {
        Optional<Deck> optionalDeck = deckRepo.findById(id);
        // throw an error if deck does not exist.
        if (optionalDeck.isEmpty())
            throw new NotFoundException("Deck not found!");
        Deck deck = optionalDeck.get();
        modelMapper.map(deckRequestDTO, deck);
        return modelMapper.map(deckRepo.save(deck),DeckResponseDTO.class);
    }

    // Delete a deck.
    @Override
    public void deleteDeck(Long id) {
        Optional<Deck> optionalDeck = deckRepo.findById(id);
        // throw an error if deck does not exist.
        if (optionalDeck.isEmpty())
            throw new NotFoundException("Deck not found!");
        deckRepo.deleteById(id);
    }

    @Override
    public DeckResponseDTO assignWord(Long deckId, Long wordId) {
        Optional<Deck> optionalDeck = deckRepo.findById(deckId);
        Optional<Word> optionalWord = wordRepo.findById(wordId);
        // check for null decks and words.
        if (optionalWord.isEmpty() || optionalDeck.isEmpty())
            throw new NotFoundException("word or deck not found!");
        // get entities.
        Word word = optionalWord.get();
        Deck deck = optionalDeck.get();
        if (deck.getWordList().contains(word))
            throw new IllegalArgumentException("Word already assigned!");
        // update lists.
        deck.getWordList().add(word);
        word.getDeckList().add(deck);
        // updating entities.
        deckRepo.save(deck);
        wordRepo.save(word);
        return modelMapper.map(deck, DeckResponseDTO.class);
    }
}
