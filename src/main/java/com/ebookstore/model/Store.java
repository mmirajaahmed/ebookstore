package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Store {

    private String id;
    private String name;
    private String slug;
    private String description;
    private String logoUrl;
    private String bannerUrl;
    private String contactEmail;
    private String contactPhone;
    private String currency;
    private BigDecimal taxRate;
    private BigDecimal freeShippingThreshold;
    private BigDecimal standardShippingFee;
    private String status;           // OPEN | CLOSED | MAINTENANCE
    private Map<String, String> socialLinks;
    private Instant createdAt;
    private Instant updatedAt;
}
