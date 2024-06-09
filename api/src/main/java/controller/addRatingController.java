package controller;

import DAO.RateDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addRate")
public class addRatingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        int productId = Integer.parseInt(req.getParameter("productId"));
        String userId = req.getParameter("userId");
        int starRatings = Integer.parseInt(req.getParameter("starRatings"));
        String comment = req.getParameter("comment");
        String fullName = req.getParameter("fullName");

        if(userId != null) {
            RateDAO rateDAO = new RateDAO();
            rateDAO.insertIntoRating(productId, userId, starRatings, comment, fullName);
            objectMapper.writeValue(resp.getOutputStream(), null);
        }
    }
}
