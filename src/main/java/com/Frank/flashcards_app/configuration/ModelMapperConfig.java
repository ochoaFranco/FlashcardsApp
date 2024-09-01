package com.Frank.flashcards_app.configuration;

import com.Frank.flashcards_app.dto.DeckResponseDTO;
import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.model.Category;
import com.Frank.flashcards_app.model.Deck;
import com.Frank.flashcards_app.model.Word;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureWordMappings(modelMapper);
        configureDeckMappings(modelMapper);
        return modelMapper;
    }
    // Deck mappings
    private void configureDeckMappings(ModelMapper modelMapper) {
        // Mapping from Deck to DeckResponseDTO
        modelMapper.typeMap(Deck.class, DeckResponseDTO.class).addMappings(mapper -> {
            // Mapping for the deck's name, description.
            mapper.map(Deck::getDeckName, DeckResponseDTO::setDeckName);
            mapper.map(Deck::getDescription, DeckResponseDTO::setDescription);
            // Mapping for associated words.
            mapper.map(
                    src -> src.getWordList() != null ?
                            src.getWordList().stream()
                                    .map(word -> modelMapper.map(word, WordResponseDTO.class))
                                    .collect(Collectors.toList()) :
                            Collections.emptyList(),
                    DeckResponseDTO::setWords
            );
        });
    }

    // Word mappings
    private void configureWordMappings(ModelMapper modelMapper) {
        // Custom mapping from wordResquestDTO to word.
        modelMapper.typeMap(WordRequestDTO.class, Word.class).addMappings(mapper -> {
            mapper.skip(Word::setCategoryList);
            mapper.skip(Word::setDeckList);
        });

        // Custom mapping form word to WordResponseDTO
        modelMapper.typeMap(Word.class, WordResponseDTO.class).addMappings(mapper -> {
            // Mapping for categories.
            mapper.map(
                    src -> src.getCategoryList() != null ? // handling when a category was associated.
                            src.getCategoryList().stream()
                                    .map(Category::getCategoryName)
                                    .collect(Collectors.toList()) : // handling when a category was NOT associated.
                            Collections.emptyList(),
                    WordResponseDTO::setCategoryNames
            );
            // Mapping for decks.
            mapper.map(
                    src -> src.getDeckList() != null ?
                            src.getDeckList().stream()
                                    .map(Deck::getDeckName)
                                    .collect(Collectors.toList()) :
                            Collections.emptyList(),
                    WordResponseDTO::setDeckNames
            );
        });
    }
}
