package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String id;
    private String memberId;
    private String status;           // PENDING | PROCESSING | SHIPPED | DELIVERED | CANCELLED | REFUNDED
    private List<OrderItem> items;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private String shippingAddressId;
    private String paymentId;
    private String shipmentId;
    private String notes;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderItem {
        private String bookId;
        private String title;
        private int quantity;
        private BigDecimal unitPrice;
        private int discountPercent;
    }
}
