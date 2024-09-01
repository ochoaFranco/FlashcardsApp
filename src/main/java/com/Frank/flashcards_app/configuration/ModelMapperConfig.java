package com.Frank.flashcards_app.configuration;

import com.Frank.flashcards_app.dto.WordRequestDTO;
import com.Frank.flashcards_app.model.Word;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Custom ModelMapper configuration.
        modelMapper.typeMap(WordRequestDTO.class, Word.class).addMappings(mapper -> {
            mapper.skip(Word::setCategoryList);
            mapper.skip(Word::setDeckList);
        });
        return modelMapper;
    }
}
