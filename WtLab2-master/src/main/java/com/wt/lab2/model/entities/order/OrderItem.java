package com.wt.lab2.model.entities.order;

import com.wt.lab2.model.entities.jewelry.Jewelry;

public class OrderItem {
    private Long id;
    private Jewelry jewelry;
    private Order order;
    private int quantity;

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(final Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}