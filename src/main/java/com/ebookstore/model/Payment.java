package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {

    private String id;
    private String orderId;
    private String memberId;
    private String method;          // CREDIT_CARD | DEBIT_CARD | PAYPAL | APPLE_PAY | GOOGLE_PAY
    private String provider;        // Stripe | PayPal | etc.
    private String transactionId;
    private String status;          // PENDING | AUTHORIZED | CAPTURED | FAILED | REFUNDED | CANCELLED
    private BigDecimal amount;
    private String currency;
    private String cardLast4;
    private String cardBrand;
    private String billingAddressId;
    private BigDecimal refundAmount;
    private Instant createdAt;
    private Instant updatedAt;
}
