package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Catalog entry — represents a single book in the store catalog.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private String id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String categoryId;
    private String brand;
    private String description;
    private String language;
    private int pages;
    private String format;          // PAPERBACK | HARDCOVER | EBOOK | AUDIOBOOK
    private BigDecimal price;
    private int discountPercent;
    private int stock;
    private double rating;
    private int reviewCount;
    private String coverImageUrl;
    private List<String> tags;
    private LocalDate publishedDate;
    private boolean featured;
}
