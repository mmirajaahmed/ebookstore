package com.ebookstore.controller;

import com.ebookstore.model.Category;
import com.ebookstore.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Book category taxonomy management")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    @Operation(summary = "List all categories")
    public ResponseEntity<List<Category>> listAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @GetMapping("/root")
    @Operation(summary = "List root (top-level) categories")
    public ResponseEntity<List<Category>> listRoot() {
        return ResponseEntity.ok(categoryRepository.findRootCategories());
    }

    @GetMapping("/{id}/children")
    @Operation(summary = "List sub-categories of a parent")
    public ResponseEntity<List<Category>> listChildren(@PathVariable String id) {
        return ResponseEntity.ok(categoryRepository.findByParentId(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));
    }

    @PostMapping
    @Operation(summary = "Create category (admin)")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(category));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category (admin)")
    public ResponseEntity<Category> update(@PathVariable String id, @RequestBody Category category) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found: " + id));
        category.setId(id);
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category (admin)")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!categoryRepository.deleteById(id)) {
            throw new NoSuchElementException("Category not found: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
