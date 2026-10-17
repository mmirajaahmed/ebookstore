package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final MockDataStore store;

    public List<Book> findAll() {
        return Collections.unmodifiableList(store.getBooks());
    }

    public Optional<Book> findById(String id) {
        return store.getBooks().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    public List<Book> findByCategoryId(String categoryId) {
        return store.getBooks().stream()
                .filter(b -> categoryId.equals(b.getCategoryId()))
                .collect(Collectors.toList());
    }

    public List<Book> findFeatured() {
        return store.getBooks().stream()
                .filter(Book::isFeatured)
                .collect(Collectors.toList());
    }

    public List<Book> search(String query) {
        String q = query.toLowerCase();
        return store.getBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q)
                        || b.getAuthor().toLowerCase().contains(q)
                        || (b.getTags() != null && b.getTags().stream().anyMatch(t -> t.contains(q))))
                .collect(Collectors.toList());
    }

    public Book save(Book book) {
        if (book.getId() == null || book.getId().isBlank()) {
            book.setId("B" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        // Replace if exists, otherwise append
        boolean replaced = false;
        List<Book> books = store.getBooks();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                replaced = true;
                break;
            }
        }
        if (!replaced) {
            books.add(book);
        }
        return book;
    }

    public boolean deleteById(String id) {
        return store.getBooks().removeIf(b -> b.getId().equals(id));
    }
}
