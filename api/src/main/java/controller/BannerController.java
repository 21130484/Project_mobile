package controller;

import DAO.BannerItemDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BannerItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api-banner")
public class BannerController extends HttpServlet {
    BannerItemDAO bannerItemDAO = new BannerItemDAO();
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BannerItem> bannerItems = bannerItemDAO.getAll();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), bannerItems);
    }
}
