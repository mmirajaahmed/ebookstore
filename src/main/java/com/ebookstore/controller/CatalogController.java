package com.ebookstore.controller;

import com.ebookstore.model.Book;
import com.ebookstore.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Journey Steps 1–4: Browse catalog, filter by category/brand, get book details.
 */
@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Tag(name = "Catalog", description = "Browse books, filter by category, view details")
public class CatalogController {

    private final CatalogService catalogService;

    // ─── Step 1: Browse catalog (with optional full-text search) ───────────────
    @GetMapping("/books")
    @Operation(summary = "Browse catalog", description = "Returns all books. Use ?search= to filter by title, author, or tag.")
    public ResponseEntity<List<Book>> browseBooks(
            @RequestParam(required = false) @Parameter(description = "Full-text search across title, author, tags") String search) {
        return ResponseEntity.ok(catalogService.browseCatalog(search));
    }

    // ─── Step 2: Filter by category ────────────────────────────────────────────
    @GetMapping("/books/category/{categoryId}")
    @Operation(summary = "Filter books by category")
    public ResponseEntity<List<Book>> booksByCategory(
            @PathVariable String categoryId) {
        return ResponseEntity.ok(catalogService.filterByCategory(categoryId));
    }

    // ─── Step 3: Featured / promoted books ─────────────────────────────────────
    @GetMapping("/books/featured")
    @Operation(summary = "Get featured books")
    public ResponseEntity<List<Book>> featuredBooks() {
        return ResponseEntity.ok(catalogService.getFeaturedBooks());
    }

    // ─── Step 4: Book detail ────────────────────────────────────────────────────
    @GetMapping("/books/{id}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        return ResponseEntity.ok(catalogService.getBook(id));
    }

    // ─── Admin CRUD ─────────────────────────────────────────────────────────────
    @PostMapping("/books")
    @Operation(summary = "Create a new book (admin)")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogService.createBook(book));
    }

    @PutMapping("/books/{id}")
    @Operation(summary = "Update a book (admin)")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(catalogService.updateBook(id, book));
    }

    @DeleteMapping("/books/{id}")
    @Operation(summary = "Delete a book (admin)")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        catalogService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
