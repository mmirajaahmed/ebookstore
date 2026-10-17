package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class ShipmentRepository {

    private final MockDataStore store;

    public Optional<Shipment> findById(String id) {
        return store.getShipments().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public Optional<Shipment> findByOrderId(String orderId) {
        return store.getShipments().stream()
                .filter(s -> orderId.equals(s.getOrderId()))
                .findFirst();
    }

    public Shipment save(Shipment shipment) {
        if (shipment.getId() == null || shipment.getId().isBlank()) {
            shipment.setId("SHP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        List<Shipment> shipments = store.getShipments();
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getId().equals(shipment.getId())) {
                shipments.set(i, shipment);
                return shipment;
            }
        }
        shipments.add(shipment);
        return shipment;
    }
}
