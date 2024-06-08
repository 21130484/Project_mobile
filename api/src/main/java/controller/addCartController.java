package controller;

import DAO.CartDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addCart")
public class addCartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String userId = req.getParameter("userId");
        if (userId != null) {
            CartDAO cartDAO = new CartDAO();
            cartDAO.createCart(userId);
            objectMapper.writeValue(resp.getOutputStream(), null);
        }
    }
}
