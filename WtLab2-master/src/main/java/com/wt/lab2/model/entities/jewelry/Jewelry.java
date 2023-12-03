package com.wt.lab2.model.entities.jewelry;

import java.math.BigDecimal;
import java.util.Objects;

public class Jewelry {
    private Long id;
    private String material;
    private String stones;
    private String type;
    private String collection;
    private BigDecimal price;
    private String imageUrl;
    private String description;

    public Jewelry() {
    }

    public Jewelry(Long id, String material, String stones, String type, String collection, BigDecimal price, String imageUrl, String description) {
        this.id = id;
        this.material = material;
        this.stones = stones;
        this.type = type;
        this.collection = collection;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jewelry jewelry = (Jewelry) o;
        return id.equals(jewelry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStones() {
        return stones;
    }

    public void setStones(String stones) {
        this.stones = stones;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
