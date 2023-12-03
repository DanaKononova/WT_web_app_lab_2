package com.wt.lab2.model.entities.basket;

import com.wt.lab2.model.entities.jewelry.Jewelry;
import com.wt.lab2.model.exceptions.CloneException;

import java.io.Serializable;

public class BasketItem implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private Jewelry jewelry;
    private int quantity;

    public BasketItem(Jewelry product, int quantity) {
        this.jewelry = product;
        this.quantity = quantity;
    }

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "code=" + jewelry.getId() +
                ", quantity=" + quantity;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException("Error copying the product " + jewelry.getId() + "with quantity" + quantity);
        }
    }
}
