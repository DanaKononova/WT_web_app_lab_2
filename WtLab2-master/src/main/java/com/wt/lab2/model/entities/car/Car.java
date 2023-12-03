package com.wt.lab2.model.entities.car;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private Long id;
    private String mark;
    private String subMark;
    private String type;
    private String years;
    private BigDecimal price;
    private String imageUrl;
    private String description;

    public Car() {
    }

    public Car(Long id, String mark, String subMark, String type, String years, BigDecimal price, String imageUrl, String description) {
        this.id = id;
        this.mark = mark;
        this.subMark = subMark;
        this.type = type;
        this.years = years;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id.equals(car.id);
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSubMark() {
        return subMark;
    }

    public void setSubMark(String subMark) {
        this.subMark = subMark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
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
