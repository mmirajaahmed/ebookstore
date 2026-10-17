package com.ebookstore.service;

import com.ebookstore.model.Book;
import com.ebookstore.model.Cart;
import com.ebookstore.repository.BookRepository;
import com.ebookstore.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    /** Step 5 — Retrieve or create cart for a member */
    public Cart getOrCreateCart(String memberId) {
        return cartRepository.findByMemberId(memberId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setId("CRT-" + memberId);
            cart.setMemberId(memberId);
            cart.setItems(new ArrayList<>());
            cart.setUpdatedAt(Instant.now());
            return cartRepository.save(cart);
        });
    }

    /** Step 6 — Add item to cart */
    public Cart addItem(String memberId, String bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + bookId));

        Cart cart = getOrCreateCart(memberId);

        // Merge if already in cart
        boolean found = false;
        for (Cart.CartItem item : cart.getItems()) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            Cart.CartItem newItem = new Cart.CartItem(
                    book.getId(),
                    book.getTitle(),
                    book.getCoverImageUrl(),
                    quantity,
                    book.getPrice(),
                    book.getDiscountPercent()
            );
            cart.getItems().add(newItem);
        }
        cart.setUpdatedAt(Instant.now());
        return cartRepository.save(cart);
    }

    /** Step 7 — Update item quantity in cart */
    public Cart updateItemQuantity(String memberId, String bookId, int quantity) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found for member: " + memberId));

        if (quantity <= 0) {
            cart.getItems().removeIf(i -> i.getBookId().equals(bookId));
        } else {
            cart.getItems().stream()
                    .filter(i -> i.getBookId().equals(bookId))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Item not in cart: " + bookId))
                    .setQuantity(quantity);
        }
        cart.setUpdatedAt(Instant.now());
        return cartRepository.save(cart);
    }

    /** Remove item from cart */
    public Cart removeItem(String memberId, String bookId) {
        Cart cart = cartRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found for member: " + memberId));
        cart.getItems().removeIf(i -> i.getBookId().equals(bookId));
        cart.setUpdatedAt(Instant.now());
        return cartRepository.save(cart);
    }

    /** Clear cart (post-order checkout) */
    public void clearCart(String memberId) {
        cartRepository.deleteByMemberId(memberId);
    }

    /** Compute cart total */
    public BigDecimal calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .map(i -> {
                    BigDecimal line = i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity()));
                    BigDecimal discount = line.multiply(BigDecimal.valueOf(i.getDiscountPercent() / 100.0));
                    return line.subtract(discount);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
