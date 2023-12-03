package com.wt.lab2.model.service.impl;

import com.wt.lab2.model.dao.JewelryDao;
import com.wt.lab2.model.dao.impl.JdbcJewelryDao;
import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.entities.basket.BasketItem;
import com.wt.lab2.model.entities.jewelry.Jewelry;
import com.wt.lab2.model.exceptions.DaoException;
import com.wt.lab2.model.exceptions.ServiceException;
import com.wt.lab2.model.service.CartService;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service to work with cart
 *
 * @author dana
 * @version 1.0
 */
public class HttpSessionCartService implements CartService {
    /**
     * Attribute of cart in session
     */
    private static final String CART_SESSION_ATTRIBUTE = HttpSessionCartService.class.getName() + ".cart";
    /**
     * Attribute of cart in request attribute
     */
    private static final String CART_ATTRIBUTE = "cart";
    /**
     * Instance of HttpSessionCartService
     */
    private static volatile HttpSessionCartService instance;
    /**
     * Instance of jewelryDao
     */
    private JewelryDao jewelryDao;

    /**
     * Realisation of Singleton pattern
     *
     * @return instance of HttpSessionCartServiece
     */

    public static HttpSessionCartService getInstance() {
        if (instance == null) {
            synchronized (HttpSessionCartService.class) {
                if (instance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    /**
     * Constructor of HttpSessionCartService
     */
    private HttpSessionCartService() {
        jewelryDao = JdbcJewelryDao.getInstance();
    }

    /**
     * Get cart from session
     *
     * @param currentSession session with cart
     * @return cart from session
     */
    @Override
    public Basket getCart(HttpSession currentSession) {
        synchronized (currentSession) {
            Basket basket = (Basket) currentSession.getAttribute(CART_SESSION_ATTRIBUTE);
            if (basket == null) {
                basket = new Basket();
                currentSession.setAttribute(CART_SESSION_ATTRIBUTE, basket);
            }
            if (basket.getTotalCost() == null) {
                basket.setTotalCost(BigDecimal.ZERO);
            }
            return basket;
        }
    }

    /**
     * Add car to cart
     *
     * @param basket           cart to adding
     * @param productId      productId of jewelry to add
     * @param quantity       quantity of jewelry to add
     * @param currentSession session with cart
     * @throws ServiceException throws when there is some errors during service method execution
     */
    @Override
    public void add(Basket basket, Long productId, int quantity, HttpSession currentSession) throws ServiceException {
        Optional<BasketItem> productMatch;
        synchronized (currentSession) {
            Jewelry jewelry;
            try {
                jewelry = jewelryDao.getJewelryById(productId).orElse(null);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
            if (jewelry != null) {
                if ((productMatch = getCartItemMatch(basket, jewelry)).isPresent()) {
                    basket.getItems().
                            get(basket.getItems().indexOf(productMatch.get())).
                            setQuantity(productMatch.get().getQuantity() + quantity);
                } else {
                    basket.getItems().add(new BasketItem(jewelry, quantity));
                    currentSession.setAttribute(CART_ATTRIBUTE, basket);
                }
                reCalculateCart(basket);
            }
        }
    }

    /**
     * Update quantity of jewelry in cart
     *
     * @param basket           cart to update
     * @param productId      id of jewelry to update
     * @param quantity       quantity of jewelry to update
     * @param currentSession session with cart
     * @throws ServiceException throws when there is some errors during service method execution
     */
    @Override
    public void update(Basket basket, Long productId, int quantity, HttpSession currentSession) throws ServiceException {
        synchronized (currentSession) {
            Jewelry jewelry;
            try {
                jewelry = jewelryDao.getJewelryById(productId).orElse(null);
            } catch (DaoException e) {
                throw new ServiceException(e.getMessage());
            }
            if (jewelry != null) {
                getCartItemMatch(basket, jewelry).ifPresent(basketItem -> basket.getItems().
                        get(basket.getItems().indexOf(basketItem)).
                        setQuantity(quantity));
                reCalculateCart(basket);
            }
        }
    }

    /**
     * Delete item from cart
     *
     * @param basket           cart to delete
     * @param productId      id of jewelry to delete
     * @param currentSession session with cart
     */
    @Override
    public void delete(Basket basket, Long productId, HttpSession currentSession) {
        synchronized (currentSession) {
            basket.getItems().removeIf(item -> productId.equals(item.getJewelry().getId()));
            reCalculateCart(basket);
        }
    }

    /**
     * Recalculate cart
     *
     * @param basketToRecalculate jewelry to recalculate
     */
    @Override
    public void reCalculateCart(Basket basketToRecalculate) {
        BigDecimal totalCost = BigDecimal.ZERO;
        basketToRecalculate.setTotalItems(
                basketToRecalculate.getItems().stream().
                        map(BasketItem::getQuantity).
                        mapToInt(q -> q).
                        sum()
        );
        for (BasketItem item : basketToRecalculate.getItems()) {
            totalCost = totalCost.add(
                    item.getJewelry().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        basketToRecalculate.setTotalCost(totalCost);
    }

    /**
     * Find cart item in cart
     *
     * @param basket    cart in witch we find
     * @param product product to find
     * @return cartItem
     */
    private Optional<BasketItem> getCartItemMatch(Basket basket, Jewelry product) {
        return basket.getItems().stream().
                filter(currProduct -> currProduct.getJewelry().getId().equals(product.getId())).
                findAny();
    }

    /**
     * Clear cart in request
     *
     * @param currentSession session with cart
     */
    @Override
    public void clear(HttpSession currentSession) {
        Basket basket = getCart(currentSession);
        basket.getItems().clear();
        reCalculateCart(basket);
    }

    /**
     * Remove item from cart
     *
     * @param currentSession session with cart
     * @param jewelryId        id of jewelry to remove
     */
    @Override
    public void remove(HttpSession currentSession, Long jewelryId) {
        Basket basket = getCart(currentSession);
        basket.getItems().removeIf(item -> jewelryId.equals(item.getJewelry().getId()));
        reCalculateCart(basket);
    }
}
