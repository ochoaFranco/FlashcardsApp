package com.Frank.flashcards_app.controller;

import com.Frank.flashcards_app.dto.CategoryDTO;
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
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryDTOResponse= categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(categoryDTOResponse, HttpStatus.CREATED);
    }

    // Read all categories.
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    // Read one category.
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }

    // update a category by its ID.
    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryDTO> editCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.editCategory(id, categoryDTO ), HttpStatus.OK);
    }
    // Delete a category by its ID.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
