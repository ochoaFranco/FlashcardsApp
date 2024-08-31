package com.Frank.flashcards_app.repository;

import com.Frank.flashcards_app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryName(String name);
}
