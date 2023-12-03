package com.wt.lab2.model.service;

import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.entities.order.Order;
import com.wt.lab2.model.entities.order.OrderStatus;
import com.wt.lab2.model.exceptions.ServiceException;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

/**
 * @author nekit
 * @version 1.0
 */
public interface OrderService {
    /**
     * Create empty order
     * @param basket cart with items
     * @return order
     */
    Order createOrder(Basket basket);

    /**
     * Place order in database
     * @param order order to place
     * @param session session with cart
     */
    void placeOrder(Order order, HttpSession session) throws ServiceException;

    /**
     * Change order status in database
     * @param id id of order
     * @param status new status of order
     */
    void changeOrderStatus(Long id, OrderStatus status) throws ServiceException;

    Optional<Order> getById (Long id) throws ServiceException;
}
