package com.Frank.flashcards_app.dto;


import com.Frank.flashcards_app.model.Difficulty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class WordRequestDTO {
    @NotEmpty(message = "Word name must not be empty")
    @Size(max = 30, message = "Word name must not exceed 30 characters")
    private String wordName;

    @NotNull(message = "Difficulty must not be null")
    private Difficulty difficulty;

    @NotEmpty(message = "Meaning must not be empty")
    @Size(max = 300, message = "Word name must not exceed 300 characters")
    private String meaning;
}
