package com.wt.lab2.model.service;

import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpSession;

/**
 * @author dana
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
     * Update jewelry in cart
     *
     * @param basket           cart to update
     * @param productId      id of jewelry to update
     * @param quantity       quantity of jewelry to update
     * @param currentSession session with cart
     */

    void update(Basket basket, Long productId, int quantity, HttpSession currentSession) throws ServiceException;

    /**
     * Delete jewelry from cart
     *
     * @param basket           cart to delete
     * @param productId      id of jewelry to delete
     * @param currentSession session with cart
     */

    void delete(Basket basket, Long productId, HttpSession currentSession);

    /**
     * Recalculate cart total price
     *
     * @param basketToRecalculate jewelry to recalculate
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
     * @param jewelryId        id of jewelry to remove
     */

    void remove(HttpSession currentSession, Long jewelryId);

}
