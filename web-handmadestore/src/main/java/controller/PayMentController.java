package controller;

import model.bean.Cart;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name= "PaymentController", value = "/payment" )
public class PayMentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessions = req.getSession();
        User user = (User) sessions.getAttribute("auth");
        Cart cart = (Cart) sessions.getAttribute("cart");
        if (user == null) {
            resp.sendRedirect("views/Login/view_login/login.jsp");
        } else {

            doPost(req, resp);
        }
    }

    //
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessions = req.getSession();
        User user = (User) sessions.getAttribute("auth");
        Cart cart = (Cart) sessions.getAttribute("cart");

        OrderDAO orderZ = new OrderDAO();

        req.setCharacterEncoding("UTF-8");
        String namePay = req.getParameter("namePay");
        String phonePay = req.getParameter("phonePay");
        String address = req.getParameter("address");


//check validation
        Map<String, String> errors = new HashMap<>();

        validateRequireField("namePay", namePay, "Tên hiển thị", errors);
        validateRequireField("phonePay", phonePay, "Số điện thoại", errors);
        validateRequireField("address", address, "Địa chỉ", errors);


        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("views/PaymentPage/payment.jsp").forward(req, resp);
        } else {

            Order order = new Order();
            order.setConsigneeName(namePay);
            order.setConsigneePhoneNumber(phonePay);
            order.setAddress(address);

//           Xoasa sesion gio hàng
            orderZ.addOrder(order, cart, user);
            cart.clear();
            req.getSession().setAttribute("cart", cart);
            resp.sendRedirect(req.getContextPath() + "/views/MainPage/view_mainpage/mainpage.jsp");

        }
    }

    public static void validateRequireField(String fieldName, String value, String viewName, Map<String, String> errors) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(fieldName, viewName + " không được bỏ trống");
        }
    }
}