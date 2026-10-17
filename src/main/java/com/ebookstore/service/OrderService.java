package com.ebookstore.service;

import com.ebookstore.model.Order;
import com.ebookstore.model.Payment;
import com.ebookstore.model.Shipment;
import com.ebookstore.repository.OrderRepository;
import com.ebookstore.repository.PaymentRepository;
import com.ebookstore.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ShipmentRepository shipmentRepository;
    private final CartService cartService;

    /** Step 8 — Place a new order */
    public Order placeOrder(Order order) {
        order.setStatus("PENDING");
        if (order.getCreatedAt() == null) {
            order.setCreatedAt(Instant.now());
        }
        order.setUpdatedAt(Instant.now());
        Order saved = orderRepository.save(order);
        // Clear cart after successful order placement
        if (order.getMemberId() != null && !order.getMemberId().isBlank()) {
            cartService.clearCart(order.getMemberId());
        }
        return saved;
    }

    /** Step 9 — Fetch order history for a member */
    public List<Order> getOrderHistory(String memberId) {
        return orderRepository.findByMemberId(memberId);
    }

    /** Fetch a single order */
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found: " + orderId));
    }

    /** Step 10 — Update order status (admin/system use) */
    public Order updateOrderStatus(String orderId, String newStatus) {
        Order order = getOrder(orderId);
        order.setStatus(newStatus.toUpperCase());
        order.setUpdatedAt(Instant.now());
        return orderRepository.save(order);
    }

    public Order cancelOrder(String orderId) {
        return updateOrderStatus(orderId, "CANCELLED");
    }

    /** Retrieve all orders (admin) */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /** Step 11 — Retrieve shipment by order */
    public Shipment getShipmentByOrder(String orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Shipment not found for order: " + orderId));
    }

    /** Step 12 — Process / update payment status */
    public Payment updatePaymentStatus(String paymentId, String status, Double refundAmount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoSuchElementException("Payment not found: " + paymentId));
        payment.setStatus(status.toUpperCase());
        if (refundAmount != null) {
            payment.setRefundAmount(java.math.BigDecimal.valueOf(refundAmount));
        }
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentForOrder(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new NoSuchElementException("Payment not found for order: " + orderId));
    }

    public Shipment saveShipment(Shipment shipment) {
        if (shipment.getCreatedAt() == null) shipment.setCreatedAt(Instant.now());
        shipment.setUpdatedAt(Instant.now());
        return shipmentRepository.save(shipment);
    }

    public Payment savePayment(Payment payment) {
        if (payment.getCreatedAt() == null) payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }
}
