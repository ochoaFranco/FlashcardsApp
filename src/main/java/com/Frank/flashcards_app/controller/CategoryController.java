package com.Frank.flashcards_app.controller;

import com.Frank.flashcards_app.dto.CategoryRequestDTO;
import com.Frank.flashcards_app.dto.CategoryResponseDTO;
import com.Frank.flashcards_app.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    // Create category.
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryRequestDTOResponse = categoryService.saveCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryRequestDTOResponse, HttpStatus.CREATED);
    }

    // Read all categories.
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    // Read one category.
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    // update a category by its ID.
    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryResponseDTO> editCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return new ResponseEntity<>(categoryService.editCategory(id, categoryRequestDTO), HttpStatus.OK);
    }

    // Delete a category by its ID.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
