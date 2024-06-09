package controller;
public class OrderDetailController {

import DAO.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Order;
import model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/updateOrder")
public class OrderDetailController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }
        String json = jsonBuilder.toString();
        Order order = objectMapper.readValue(json, Order.class);
        OrderDAO orderDAO = new OrderDAO();
        boolean isUpdated = orderDAO.UpdateStatusOrder(String.valueOf(order.getId()));
        objectMapper.writeValue(resp.getOutputStream(), order);
    }
}
