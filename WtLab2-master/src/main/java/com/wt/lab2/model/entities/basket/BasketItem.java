package com.wt.lab2.model.entities.basket;

import com.wt.lab2.model.entities.car.Car;
import com.wt.lab2.model.exceptions.CloneException;

import java.io.Serializable;

public class BasketItem implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private Car car;
    private int quantity;

    public BasketItem(Car product, int quantity) {
        this.car = product;
        this.quantity = quantity;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "code=" + car.getId() +
                ", quantity=" + quantity;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException("Error copying the product " + car.getId() + "with quantity" + quantity);
        }
    }
}
