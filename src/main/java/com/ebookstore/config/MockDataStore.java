package com.ebookstore.config;

import com.ebookstore.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Loads all mock JSON files from {@code classpath:mock-db/} into memory
 * at application startup. Each entity type gets its own thread-safe list
 * that acts as the in-memory "database table".
 */
@Slf4j
@Component
@Getter
public class MockDataStore {

    @Value("${app.mock.data.path:mock-db}")
    private String mockDataPath;

    private final ObjectMapper objectMapper;

    // In-memory "tables" — CopyOnWriteArrayList for safe concurrent reads
    private List<Book>     books     = new CopyOnWriteArrayList<>();
    private List<Category> categories = new CopyOnWriteArrayList<>();
    private List<Member>   members   = new CopyOnWriteArrayList<>();
    private List<Order>    orders    = new CopyOnWriteArrayList<>();
    private List<Shipment> shipments = new CopyOnWriteArrayList<>();
    private List<Payment>  payments  = new CopyOnWriteArrayList<>();
    private List<Store>    stores    = new CopyOnWriteArrayList<>();

    public MockDataStore() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @PostConstruct
    public void loadAll() {
        books      = loadFile("books.json",      new TypeReference<List<Book>>(){});
        categories = loadFile("categories.json", new TypeReference<List<Category>>(){});
        members    = loadFile("members.json",    new TypeReference<List<Member>>(){});
        orders     = loadFile("orders.json",     new TypeReference<List<Order>>(){});
        shipments  = loadFile("shipments.json",  new TypeReference<List<Shipment>>(){});
        payments   = loadFile("payments.json",   new TypeReference<List<Payment>>(){});
        stores     = loadFile("stores.json",     new TypeReference<List<Store>>(){});

        log.info("MockDataStore loaded — books={}, categories={}, members={}, orders={}, shipments={}, payments={}, stores={}",
                books.size(), categories.size(), members.size(),
                orders.size(), shipments.size(), payments.size(), stores.size());
    }

    /**
     * Reads a JSON file from the classpath mock-db directory and deserialises
     * it into a mutable {@code CopyOnWriteArrayList}.
     */
    private <T> List<T> loadFile(String fileName, TypeReference<List<T>> typeRef) {
        String resourcePath = mockDataPath + "/" + fileName;
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            if (!resource.exists()) {
                log.warn("Mock data file not found: {}", resourcePath);
                return new CopyOnWriteArrayList<>();
            }
            try (InputStream is = resource.getInputStream()) {
                List<T> data = objectMapper.readValue(is, typeRef);
                log.debug("Loaded {} records from {}", data.size(), resourcePath);
                return new CopyOnWriteArrayList<>(data);
            }
        } catch (Exception ex) {
            log.error("Failed to load mock data from {}: {}", resourcePath, ex.getMessage(), ex);
            return new CopyOnWriteArrayList<>();
        }
    }
}
