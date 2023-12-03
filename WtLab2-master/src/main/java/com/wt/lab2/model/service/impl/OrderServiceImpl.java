package com.wt.lab2.model.service.impl;

import com.wt.lab2.model.dao.OrderDao;
import com.wt.lab2.model.dao.impl.JdbcOrderDao;
import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.entities.order.Order;
import com.wt.lab2.model.entities.order.OrderItem;
import com.wt.lab2.model.entities.order.OrderStatus;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.model.exceptions.ServiceException;
import com.wt.lab2.model.service.CartService;
import com.wt.lab2.model.service.OrderService;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Using to manage orders
 *
 * @author dana
 * @version 1.0
 */
public class OrderServiceImpl implements OrderService {
    /**
     * Instance of cart service
     */
    private CartService cartService = HttpSessionCartService.getInstance();

    /**
     * Instance of order dao
     */
    private OrderDao orderDao = JdbcOrderDao.getInstance();
    /**
     * Instance of OrderService
     */
    private static volatile OrderService instance;

    /**
     * Realisation of Singleton pattern
     *
     * @return instance of orderService
     */
    public static OrderService getInstance() {
        if (instance == null) {
            synchronized (OrderService.class) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Create empty order and fill order items
     *
     * @param basket cart with items
     * @return order
     */
    public Order createOrder(Basket basket) {
        Order order = new Order();
        BigDecimal deliveryPrice = BigDecimal.valueOf(10);
        order.setDeliveryPrice(deliveryPrice);
        order.setSubtotal(basket.getTotalCost());
        order.setTotalPrice(order.getSubtotal().add(order.getDeliveryPrice()));
        fillOrderItems(order, basket);
        return order;
    }

    /**
     * Place order in database
     *
     * @param order   order to place
     * @param session session with cart
     * @throws ServiceException throws when there is some errors during service method execution
     */
    @Override
    public void placeOrder(final Order order, HttpSession session) throws ServiceException {
        order.setDate(new Date(Instant.now().toEpochMilli()));
        order.setTime(new Time(Instant.now().toEpochMilli()));
        order.setLogin(session.getAttribute("login").toString());
        order.setStatus(OrderStatus.NEW);
        try {
            order.setSecureID(UUID.randomUUID().toString());
            orderDao.save(order);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        cartService.clear(session);
    }

    /**
     * Changing order status in database
     *
     * @param id     id of order
     * @param status new status of order
     * @throws ServiceException throws when there is some errors during service method execution
     */
    @Override
    public void changeOrderStatus(Long id, OrderStatus status) throws ServiceException {
        try {
            orderDao.changeStatus(id, status);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<Order> getById(Long id) throws ServiceException {
        try {
            return orderDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Fill order items from cart to order
     *
     * @param order order to fill
     * @param basket  cart with items
     */
    private void fillOrderItems(Order order, Basket basket) {
        List<OrderItem> orderItems = basket.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setJewelry(cartItem.getJewelry());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
    }
}
