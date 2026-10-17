package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final MockDataStore store;

    public List<Category> findAll() {
        return Collections.unmodifiableList(store.getCategories());
    }

    public Optional<Category> findById(String id) {
        return store.getCategories().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public List<Category> findRootCategories() {
        return store.getCategories().stream()
                .filter(c -> c.getParentId() == null)
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    public List<Category> findByParentId(String parentId) {
        return store.getCategories().stream()
                .filter(c -> parentId.equals(c.getParentId()))
                .sorted(Comparator.comparingInt(Category::getDisplayOrder))
                .toList();
    }

    public Category save(Category category) {
        if (category.getId() == null || category.getId().isBlank()) {
            category.setId("CAT" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        List<Category> cats = store.getCategories();
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getId().equals(category.getId())) {
                cats.set(i, category);
                return category;
            }
        }
        cats.add(category);
        return category;
    }

    public boolean deleteById(String id) {
        return store.getCategories().removeIf(c -> c.getId().equals(id));
    }
}
