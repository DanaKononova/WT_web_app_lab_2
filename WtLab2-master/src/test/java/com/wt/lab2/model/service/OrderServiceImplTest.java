package com.wt.lab2.model.service;

import com.wt.lab2.model.entities.basket.Basket;
import com.wt.lab2.model.entities.basket.BasketItem;
import com.wt.lab2.model.entities.order.Order;
import com.wt.lab2.model.entities.car.Car;
import com.wt.lab2.model.service.impl.OrderServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest {
    private OrderService orderService;


    @Before
    public void init() {
        orderService = OrderServiceImpl.getInstance();
    }

    @Test
    public void testCreatingOrderFromCart() {
        Basket basket = new Basket();
        basket.setTotalCost(new BigDecimal(100));
        basket.setTotalItems(3);

        List<BasketItem> testList = new ArrayList<>();
        testList.add(new BasketItem(new Car(), 2));
        basket.setItems(testList);

        Order order = orderService.createOrder(basket);
        Assert.assertEquals(order.getSubtotal(), basket.getTotalCost());
    }

    @Test
    public void testCloningObjectsWhileCreatingOrderFromCart() {
        Basket basket = new Basket();
        basket.setTotalCost(new BigDecimal(100));
        basket.setTotalItems(1);

        List<BasketItem> testList = new ArrayList<>();
        testList.add(new BasketItem(new Car(), 2));
        basket.setItems(testList);

        Order order = orderService.createOrder(basket);
        for (int i = 0; i < order.getOrderItems().size(); i++) {
            Assert.assertEquals(order.getOrderItems().get(i).getCar(), basket.getItems().get(i).getCar());
            Assert.assertEquals(order.getOrderItems().get(i).getQuantity(), basket.getItems().get(i).getQuantity());
        }
    }

    @After
    public void clean() {
        orderService = null;
    }
}
