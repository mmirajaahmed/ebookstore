package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final MockDataStore store;

    public Optional<Payment> findById(String id) {
        return store.getPayments().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Optional<Payment> findByOrderId(String orderId) {
        return store.getPayments().stream()
                .filter(p -> orderId.equals(p.getOrderId()))
                .findFirst();
    }

    public List<Payment> findByMemberId(String memberId) {
        return store.getPayments().stream()
                .filter(p -> memberId.equals(p.getMemberId()))
                .toList();
    }

    public Payment save(Payment payment) {
        if (payment.getId() == null || payment.getId().isBlank()) {
            payment.setId("PAY" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        List<Payment> payments = store.getPayments();
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getId().equals(payment.getId())) {
                payments.set(i, payment);
                return payment;
            }
        }
        payments.add(payment);
        return payment;
    }
}
