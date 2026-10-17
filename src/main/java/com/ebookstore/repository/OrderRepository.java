package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final MockDataStore store;

    public List<Order> findAll() {
        return Collections.unmodifiableList(store.getOrders());
    }

    public Optional<Order> findById(String id) {
        return store.getOrders().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public List<Order> findByMemberId(String memberId) {
        return store.getOrders().stream()
                .filter(o -> Objects.equals(memberId, o.getMemberId()))
                .sorted(Comparator.comparing(Order::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public List<Order> findByStatus(String status) {
        return store.getOrders().stream()
                .filter(o -> status != null && status.equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
    }

    public Order save(Order order) {
        if (order.getId() == null || order.getId().isBlank()) {
            order.setId("ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(Instant.now());
        }
        if (order.getUpdatedAt() == null) {
            order.setUpdatedAt(Instant.now());
        }
        List<Order> orders = store.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                return order;
            }
        }
        orders.add(order);
        return order;
    }

    public boolean deleteById(String id) {
        return store.getOrders().removeIf(o -> o.getId().equals(id));
    }
}
