package com.wt.lab2.model.service;

import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpSession;

/**
 * @author nekit
 * @version 1.0
 */
public interface CartService {
    /**
     * Get cart from request
     *
     * @param currentSession session with cart
     * @return cart
     */
    Basket getCart(HttpSession currentSession);

    /**
     * @param basket           cart to add
     * @param productId      id of product to add
     * @param quantity       quantity of product to add
     * @param currentSession session with cart
     */
    void add(Basket basket, Long productId, int quantity, HttpSession currentSession) throws ServiceException;

    /**
     * Update car in cart
     *
     * @param basket           cart to update
     * @param productId      id of car to update
     * @param quantity       quantity of car to update
     * @param currentSession session with cart
     */

    void update(Basket basket, Long productId, int quantity, HttpSession currentSession) throws ServiceException;

    /**
     * Delete car from cart
     *
     * @param basket           cart to delete
     * @param productId      id of car to delete
     * @param currentSession session with cart
     */

    void delete(Basket basket, Long productId, HttpSession currentSession);

    /**
     * Recalculate cart total price
     *
     * @param basketToRecalculate cat to recalculate
     */

    void reCalculateCart(Basket basketToRecalculate);

    /**
     * Clear cart
     *
     * @param currentSession session with cart
     */
    void clear(HttpSession currentSession);

    /**
     * Remove item from cart
     *
     * @param currentSession session with cart
     * @param carId        id of car to remove
     */

    void remove(HttpSession currentSession, Long carId);

}
