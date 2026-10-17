package com.ebookstore.controller;

import com.ebookstore.model.Order;
import com.ebookstore.model.Payment;
import com.ebookstore.model.Shipment;
import com.ebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Journey Steps 8–12: Place order, view history, track shipment, manage payment.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order placement, history, shipment tracking, and payment")
public class OrderController {

    private final OrderService orderService;

    // ─── Step 8: Place new order ────────────────────────────────────────────────
    @PostMapping
    @Operation(summary = "Place a new order (checkout)")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeOrder(order));
    }

    // ─── Step 9: Order history ──────────────────────────────────────────────────
    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get order history for a member")
    public ResponseEntity<List<Order>> orderHistory(@PathVariable String memberId) {
        return ResponseEntity.ok(orderService.getOrderHistory(memberId));
    }

    // ─── Get single order ───────────────────────────────────────────────────────
    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    // ─── Admin: all orders ──────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "List all orders (admin)")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ─── Step 10: Update order status (admin) ──────────────────────────────────
    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Update order status", description = "Body: { status: 'PROCESSING' }")
    public ResponseEntity<Order> updateStatus(
            @PathVariable String orderId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, body.get("status")));
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "Cancel an order")
    public ResponseEntity<Order> cancelOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    // ─── Step 11: Shipment tracking ─────────────────────────────────────────────
    @GetMapping("/{orderId}/shipment")
    @Operation(summary = "Get shipment tracking info for an order")
    public ResponseEntity<Shipment> getShipment(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getShipmentByOrder(orderId));
    }

    @PostMapping("/{orderId}/shipment")
    @Operation(summary = "Create / attach a shipment to an order (admin)")
    public ResponseEntity<Shipment> createShipment(
            @PathVariable String orderId,
            @RequestBody Shipment shipment) {
        shipment.setOrderId(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.saveShipment(shipment));
    }

    // ─── Step 12: Payment ────────────────────────────────────────────────────────
    @GetMapping("/{orderId}/payment")
    @Operation(summary = "Get payment details for an order")
    public ResponseEntity<Payment> getPayment(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getPaymentForOrder(orderId));
    }

    @PostMapping("/{orderId}/payment")
    @Operation(summary = "Record a payment for an order")
    public ResponseEntity<Payment> createPayment(
            @PathVariable String orderId,
            @RequestBody Payment payment) {
        payment.setOrderId(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.savePayment(payment));
    }

    @PatchMapping("/{orderId}/payment/status")
    @Operation(summary = "Update payment status (e.g., CAPTURED, REFUNDED)", description = "Body: { status, refundAmount? }")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable String orderId,
            @RequestBody Map<String, Object> body) {
        Payment payment = orderService.getPaymentForOrder(orderId);
        String status = (String) body.get("status");
        Double refund = body.containsKey("refundAmount") ? ((Number) body.get("refundAmount")).doubleValue() : null;
        return ResponseEntity.ok(orderService.updatePaymentStatus(payment.getId(), status, refund));
    }
}
