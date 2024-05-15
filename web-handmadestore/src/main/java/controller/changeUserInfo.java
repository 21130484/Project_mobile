package controller;

import model.bean.User;
import model.dao.UserDAO;
import model.service.RoleService;
import model.service.UserService;
import utils.HashPassword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/changeUserInfo")
public class changeUserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/jsp; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        User user = (User) (req.getSession().getAttribute("auth"));
        if (user != null) {
            String submit = req.getParameter("submit");
            if (submit != null) {
                if (submit.equals("change_name")) {
                    String input_name = req.getParameter("input_name");
                    if (input_name == null || input_name.trim().equals(""))
                        req.setAttribute("result", "Tên mới không khả dụng");
                    else {
                        UserService.getInstance().updateName(user.getId() + "", input_name);
                        req.setAttribute("result", "Tên mới - " + input_name + " đã cập nhật");

                        user = (User) UserService.getInstance().getUserById(user.getId() + "");
                        req.getSession().setAttribute("auth", user);
                    }

                } else if (submit.equals("change_phone")) {
                    String input_phone = req.getParameter("input_phone");
                    if (input_phone == null || input_phone.trim().equals("") || !(input_phone.length() <= 10 && input_phone.charAt(0) == '0'))
                        req.setAttribute("result", "Số điện thoại không khả dụng");
                    else {
                        UserService.getInstance().updatePhoneNumber(user.getId() + "", input_phone);
                        req.setAttribute("result", "Số điện thoại mới - " + input_phone + " đã cập nhật");

                        user = (User) UserService.getInstance().getUserById(user.getId() + "");
                        req.getSession().setAttribute("auth", user);
                    }
                }
            }
            req.getRequestDispatcher("./views/Admin/changeUserInfo.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
