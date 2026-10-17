package com.ebookstore.repository;

import com.ebookstore.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cart is session-scoped and ephemeral — stored purely in a ConcurrentHashMap,
 * not loaded from any JSON file.
 */
@Repository
public class CartRepository {

    private final Map<String, Cart> cartsByMemberId = new ConcurrentHashMap<>();

    public Optional<Cart> findByMemberId(String memberId) {
        if (memberId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cartsByMemberId.get(memberId));
    }

    public Cart save(Cart cart) {
        if (cart != null && cart.getMemberId() != null) {
            cartsByMemberId.put(cart.getMemberId(), cart);
        }
        return cart;
    }

    public void deleteByMemberId(String memberId) {
        if (memberId != null) {
            cartsByMemberId.remove(memberId);
        }
    }
}
