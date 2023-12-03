package com.wt.lab2.web.commands.commandImpl;

import com.wt.lab2.model.service.CartService;
import com.wt.lab2.model.service.impl.HttpSessionCartService;
import com.wt.lab2.web.JspPageName;
import com.wt.lab2.web.commands.ICommand;
import com.wt.lab2.web.exceptions.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author dana
 * @version 1.0
 * Command to get cart jsp
 */
public class CartGetCommand implements ICommand {
    private static final String CART_ATTRIBUTE = "cart";
    private final CartService cartService = HttpSessionCartService.getInstance();

    /**
     * Get cart jsp
     *
     * @param request http request
     * @return cart jsp path
     * @throws CommandException throws when there is some errors during command execution
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(CART_ATTRIBUTE, cartService.getCart(request.getSession()));
        return JspPageName.CART_JSP;
    }
}
