package controller;

import DAO.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.Order;
import utils.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        int userId = Integer.parseInt(req.getParameter("userId"));
        List<Order> orders = orderDAO.getAllOrder(userId);

        objectMapper.writeValue(resp.getOutputStream(), orders);
    }
}
