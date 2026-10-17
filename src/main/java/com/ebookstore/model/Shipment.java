package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipment {

    private String id;
    private String orderId;
    private String carrier;
    private String trackingNumber;
    private String status;          // PENDING | IN_TRANSIT | OUT_FOR_DELIVERY | DELIVERED | FAILED
    private LocalDate estimatedDelivery;
    private Instant actualDelivery;
    private String origin;
    private String destination;
    private List<ShipmentEvent> events;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShipmentEvent {
        private Instant timestamp;
        private String location;
        private String description;
    }
}
