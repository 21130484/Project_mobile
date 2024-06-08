package controller;

import DAO.OrderDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Order;
import model.OrderItem;

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
        for (int i = 0 ; i < orders.size(); i++){
//            List<OrderItem> order1Items = orderDAO.getItemForOrder(orders.get(i).getId());
//            orders.get(i).setItemList(orderItems);
        }
        objectMapper.writeValue(resp.getOutputStream(), orders);
    }
}
