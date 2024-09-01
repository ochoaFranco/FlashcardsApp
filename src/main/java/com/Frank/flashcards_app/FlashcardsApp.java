package com.Frank.flashcards_app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;

@SpringBootApplication
public class FlashcardsApp {
	public static void main(String[] args) {
		SpringApplication.run(FlashcardsApp.class, args);
	}

}
