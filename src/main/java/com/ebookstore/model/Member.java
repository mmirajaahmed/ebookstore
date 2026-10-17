package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String phone;
    private String role;          // CUSTOMER | ADMIN
    private String status;        // ACTIVE | SUSPENDED | DELETED
    private int loyaltyPoints;
    private List<ShippingAddress> shippingAddresses;
    private Instant createdAt;
    private Instant updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingAddress {
        private String addressId;
        private String label;
        private String street;
        private String city;
        private String state;
        private String zip;
        private String country;
        private boolean isDefault;
    }
}
