package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory cart (not persisted to a JSON file).
 * Cart instances live in CartRepository keyed by memberId.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {

    private String id;
    private String memberId;
    private List<CartItem> items = new ArrayList<>();
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        private String bookId;
        private String title;
        private String coverImageUrl;
        private int quantity;
        private BigDecimal unitPrice;
        private int discountPercent;
    }
}
