package com.ebookstore.controller;

import com.ebookstore.model.Cart;
import com.ebookstore.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Journey Steps 5–7: View cart, add item, update quantity, remove item.
 */
@RestController
@RequestMapping("/api/v1/members/{memberId}/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Shopping cart operations for a member")
public class CartController {

    private final CartService cartService;

    // ─── Step 5: View cart ──────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Get cart for a member")
    public ResponseEntity<Cart> getCart(@PathVariable String memberId) {
        return ResponseEntity.ok(cartService.getOrCreateCart(memberId));
    }

    @GetMapping("/total")
    @Operation(summary = "Get cart total (pre-tax)")
    public ResponseEntity<Map<String, BigDecimal>> getCartTotal(@PathVariable String memberId) {
        Cart cart = cartService.getOrCreateCart(memberId);
        BigDecimal total = cartService.calculateTotal(cart);
        return ResponseEntity.ok(Map.of("total", total));
    }

    // ─── Step 6: Add item ───────────────────────────────────────────────────────
    @PostMapping("/items")
    @Operation(summary = "Add a book to the cart", description = "Body: { bookId, quantity }")
    public ResponseEntity<Cart> addItem(
            @PathVariable String memberId,
            @RequestBody Map<String, Object> body) {
        String bookId = (String) body.get("bookId");
        int quantity = ((Number) body.get("quantity")).intValue();
        return ResponseEntity.ok(cartService.addItem(memberId, bookId, quantity));
    }

    // ─── Step 7: Update item quantity ──────────────────────────────────────────
    @PutMapping("/items/{bookId}")
    @Operation(summary = "Update item quantity (0 = remove)")
    public ResponseEntity<Cart> updateItem(
            @PathVariable String memberId,
            @PathVariable String bookId,
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(cartService.updateItemQuantity(memberId, bookId, body.get("quantity")));
    }

    @DeleteMapping("/items/{bookId}")
    @Operation(summary = "Remove a specific item from the cart")
    public ResponseEntity<Cart> removeItem(
            @PathVariable String memberId,
            @PathVariable String bookId) {
        return ResponseEntity.ok(cartService.removeItem(memberId, bookId));
    }

    @DeleteMapping
    @Operation(summary = "Clear the entire cart")
    public ResponseEntity<Void> clearCart(@PathVariable String memberId) {
        cartService.clearCart(memberId);
        return ResponseEntity.noContent().build();
    }
}
