package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.entities.order.Order;
import com.wt.lab2.model.exceptions.ServiceException;
import com.wt.lab2.model.service.CartService;
import com.wt.lab2.model.service.OrderService;
import com.wt.lab2.model.service.impl.HttpSessionCartService;
import com.wt.lab2.model.service.impl.OrderServiceImpl;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.CommandHelper;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author nekit
 * @version 1.0
 * Command to get order page, fill client data and place th order
 */
public class OrderPageCommand implements ICommand {
    private static final String ODER_ATTRIBUTE = "order";
    private OrderService orderService = OrderServiceImpl.getInstance();
    private CartService cartService = HttpSessionCartService.getInstance();
    private final String car_VALIDATION_REG_EXP = "^\\+375(29|25|44|33)\\d{7}$";

    /**
     * Get order page jsp or fill client data and try to place order
     *
     * @param request http request
     * @return order page jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        if (request.getMethod().equals("GET")) {
            request.setAttribute(ODER_ATTRIBUTE, orderService.createOrder(cartService.getCart(request.getSession())));
            return JspPageName.ORDER_JSP;
        } else {
            Map<Integer, String> errorsMap = new HashMap<>();
            Order order = fillClientData(request, errorsMap);
            if (errorsMap.isEmpty()) {
                try {
                    orderService.placeOrder(order, request.getSession());
                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage());
                }
                if (errorsMap.isEmpty()) {
                    request.setAttribute("secureId", order.getSecureID());
                    return CommandHelper.getInstance().getCommand("order_overview").execute(request);
                }
            } else {
                request.setAttribute("errorsMap", errorsMap);
                request.setAttribute(ODER_ATTRIBUTE, orderService.createOrder(cartService.getCart(request.getSession())));
                return JspPageName.ORDER_JSP;
            }
        }
        return JspPageName.ORDER_JSP;
    }

    /**
     * Fill client data from request to order attributes
     *
     * @param request   http request
     * @param errorsMap map with errors
     * @return order with client data
     */
    private Order fillClientData(HttpServletRequest request, Map<Integer, String> errorsMap) {
        Order order = orderService.createOrder(cartService.getCart(request.getSession()));
        String field = request.getParameter("firstName");
        Object lang = request.getSession().getAttribute("lang");
        if (lang == null) {
            lang = "en";
        }
        Locale locale = new Locale(lang.toString());
        ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
        if (field == null || field.isEmpty()) {
            errorsMap.put(1, rb.getString("POSSIBLE_ERROR_MESSAGE_NO_FILLING"));
        } else {
            order.setFirstName(field);
        }
        field = request.getParameter("lastName");
        if (field == null || field.isEmpty()) {
            errorsMap.put(2, rb.getString("POSSIBLE_ERROR_MESSAGE_NO_FILLING"));
        } else {
            order.setLastName(field);
        }
        field = request.getParameter("deliveryAddress");
        if (field == null || field.isEmpty()) {
            errorsMap.put(3, rb.getString("POSSIBLE_ERROR_MESSAGE_NO_FILLING"));
        } else {
            order.setDeliveryAddress(field);
        }
        field = request.getParameter("contactcarNo");
        if (field == null || field.isEmpty()) {
            errorsMap.put(4, rb.getString("POSSIBLE_ERROR_MESSAGE_NO_FILLING"));
        } else {
            if (!field.matches(car_VALIDATION_REG_EXP)) {
                errorsMap.put(4, rb.getString("POSSIBLE_ERROR_MESSAGE_HAS_ERRORS_car"));
            } else {
                order.setContactcarNo(field);
            }
        }
        order.setAdditionalInformation(request.getParameter("additionalInformation"));
        return order;
    }
}
