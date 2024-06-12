package controller;

import DAO.RateDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ProductDetail;
import model.Rate;
import service.ProductService;
import service.RateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/addRate")
public class addRatingController extends HttpServlet {
    private ProductService p = new ProductService();
    private RateService r = new RateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     doPost(req,resp);
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
            r.insertIntoRating(productId, userId, starRatings, comment, fullName);
//            List<Rate> rateList = rateDAO.getRateByProduct(productId);

            List<Rate> rateList = r.getRateByProduct(productId);
            ProductDetail productDetail = p.getPDetailsById(productId);
            productDetail.setRateList(rateList);
//            ProductDetail productDetail = p.getPDetailsById(productId);
//            productDetail.setRateList(rateList);
            objectMapper.writeValue(resp.getOutputStream(), null);


        }
    }

}
