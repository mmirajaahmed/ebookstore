package com.ebookstore.service;

import com.ebookstore.model.Book;
import com.ebookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final BookRepository bookRepository;

    /** Step 1 — Browse full catalog with optional text search */
    public List<Book> browseCatalog(String search) {
        if (search != null && !search.isBlank()) {
            return bookRepository.search(search.trim());
        }
        return bookRepository.findAll();
    }

    /** Step 2 — Filter by category */
    public List<Book> filterByCategory(String categoryId) {
        return bookRepository.findByCategoryId(categoryId);
    }

    /** Step 3 — Retrieve featured/promoted books */
    public List<Book> getFeaturedBooks() {
        return bookRepository.findFeatured();
    }

    /** Step 4 — Get single book detail */
    public Book getBook(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(String id, Book updated) {
        bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
        updated.setId(id);
        return bookRepository.save(updated);
    }

    public void deleteBook(String id) {
        if (!bookRepository.deleteById(id)) {
            throw new NoSuchElementException("Book not found: " + id);
        }
    }
}
