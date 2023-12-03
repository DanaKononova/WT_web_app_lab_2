package com.wt.lab2.model.entities.order;

import com.wt.lab2.model.entities.car.Car;

public class OrderItem {
    private Long id;
    private Car car;
    private Order order;
    private int quantity;

    public Car getCar() {
        return car;
    }

    public void setcar(final Car car) {
        this.car = car;
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