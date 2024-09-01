package com.Frank.flashcards_app.configuration;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.dto.WordResponseDTO;
import com.Frank.flashcards_app.model.Category;
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
        // Custom mapping from wordResquestDTO to word.
        modelMapper.typeMap(WordRequestDTO.class, Word.class).addMappings(mapper -> {
            mapper.skip(Word::setCategoryList);
            mapper.skip(Word::setDeckList);
        });

        // Custom mapping form word to WordResponseDTO
        modelMapper.typeMap(Word.class, WordResponseDTO.class).addMappings(mapper -> {
            mapper.map(
                    src -> src.getCategoryList() != null ? // handling when a category was associated.
                            src.getCategoryList().stream()
                                .map(Category::getCategoryName)
                                .collect(Collectors.toList()) : // handling when a category was NOT associated.
                            Collections.emptyList(),
                    WordResponseDTO::setCategoryNames
            );
        });
        return modelMapper;
    }
}
