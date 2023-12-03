package com.wt.lab2.model.service;

import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.exceptions.ServiceException;
import com.wt.lab2.model.service.impl.HttpSessionCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpSessionBasketServiceTest {
    private HttpSessionCartService cartService;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testGetCart_NewCart() {
        Basket basket = cartService.getCart(request.getSession());
        assertNotNull(basket);
        assertEquals(BigDecimal.ZERO, basket.getTotalCost());
    }

    @Test
    public void testDeleteCartItem() throws ServiceException {
        Basket basket = new Basket();
        cartService.add(basket, 1L, 5, request.getSession());

        cartService.delete(basket, 1L, request.getSession());

        assertEquals(0, basket.getTotalItems());
    }

    @Test
    public void testClearCart() throws ServiceException {
        Basket basket = new Basket();
        cartService.add(basket, 1L, 5, request.getSession());

        cartService.clear(request.getSession());

        assertEquals(0, basket.getTotalItems());
    }

    @Test
    public void testRemoveCartItem() throws ServiceException {
        Basket basket = new Basket();
        cartService.add(basket, 1L, 5, request.getSession());

        cartService.remove(request.getSession(), 1L);

        assertEquals(0, basket.getTotalItems());
    }
}