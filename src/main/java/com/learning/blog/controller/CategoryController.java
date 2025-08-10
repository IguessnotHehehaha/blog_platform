package com.learning.blog.controller;

import com.learning.blog.domain.dtos.CategoryDTO;
import com.learning.blog.domain.dtos.CreateCategoryRequest;
import com.learning.blog.domain.entities.Category;
import com.learning.blog.repositories.CategoryRepository;
import com.learning.blog.mappers.CategoryMapper;
import com.learning.blog.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> listCategories(){
        List<CategoryDTO> categories = categoryService.listCategories().stream()
                        .map(categoryMapper::toDTO).toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        Category category = categoryMapper.toEntity(createCategoryRequest);
        Category savedCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryMapper.toDTO(savedCategory), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
