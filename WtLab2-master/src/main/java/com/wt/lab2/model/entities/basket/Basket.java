package com.wt.lab2.model.entities.basket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<BasketItem> items;
    private int cartId;
    private int totalItems;
    private BigDecimal totalCost = BigDecimal.valueOf(0);

    public Basket() {
        items = new ArrayList<>();
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }

    public int getCartId() {
        return cartId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                items +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return items.equals(basket.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

}
