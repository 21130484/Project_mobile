package controller;

import DAO.CartDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String userId = req.getParameter("userId");
//        int cartId = cartDAO.getCartByUserId(userId).getId();
        List<CartItem> cartItems = cartDAO.getAllCartItem(userId);

        objectMapper.writeValue(resp.getOutputStream(), cartItems);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        int newQuantity = Integer.parseInt(req.getParameter("newQuantity"));
        int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        int cartId = Integer.parseInt(req.getParameter("cartId"));
        cartDAO.updateQuantity(newQuantity,cartItemId,cartId);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
        int cartId = Integer.parseInt(req.getParameter("cartId"));
        boolean isUpdated = cartDAO.deleteCartItem(cartItemId,cartId);

        objectMapper.writeValue(resp.getOutputStream(), isUpdated);
    }
}
