package com.ebookstore.controller;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Store;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
@Tag(name = "Store", description = "Store configuration and info")
public class StoreController {

    private final MockDataStore dataStore;

    @GetMapping
    @Operation(summary = "Get store info")
    public ResponseEntity<Store> getStore() {
        return dataStore.getStores().stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Store not configured"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update store config (admin)")
    public ResponseEntity<Store> updateStore(@PathVariable String id, @RequestBody Store store) {
        store.setId(id);
        List<Store> stores = dataStore.getStores();
        for (int i = 0; i < stores.size(); i++) {
            if (stores.get(i).getId().equals(id)) {
                stores.set(i, store);
                return ResponseEntity.ok(store);
            }
        }
        stores.add(store);
        return ResponseEntity.ok(store);
    }
}
