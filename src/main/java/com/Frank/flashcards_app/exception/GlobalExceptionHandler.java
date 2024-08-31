package com.Frank.flashcards_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    // handle duplicate name.
    @ExceptionHandler(DuplicateCategoryNameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCategoryNameException(DuplicateCategoryNameException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Category name already exists",
                "A category with this name is already registered. Please use a different name.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // handle Not found exception.
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Not found",
                "Not found, try again with a different ID");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
