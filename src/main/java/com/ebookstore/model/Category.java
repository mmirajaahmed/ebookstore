package com.ebookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private String id;
    private String name;
    private String slug;
    private String description;
    private String parentId;       // null = root category
    private String imageUrl;
    private int displayOrder;
}
