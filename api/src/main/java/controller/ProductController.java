package controller;

import DAO.ImageDAO;
import DAO.ProductDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Category;
import model.Image;
import model.Product;
import model.Rate;
import service.ImageService;
import service.ProductService;
import service.RateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api-product")
public class ProductController extends HttpServlet {

    private ProductService p = new ProductService();
    private RateService r = new RateService();
    private ImageService i = new ImageService();
    ProductDAO productDAO = new ProductDAO();
    ImageDAO imageDAO = new ImageDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");

            if (action.equals("getAllProducts")) {
                List<Product> products = productDAO.getAll();
                objectMapper.writeValue(resp.getOutputStream(), products);
            } else if (action.equals("getImageByProductId")) {
                String productId = req.getParameter("productId");
                if (productId != null) {
                    List<Image> images = imageDAO.getImagesForProduct(productId);
                    objectMapper.writeValue(resp.getOutputStream(), images);
                }
            } else if (action.equals("getProductById")) {
                String productId = req.getParameter("productId");
                if (productId != null) {
                    Product products = productDAO.getProductById(productId);
                    objectMapper.writeValue(resp.getOutputStream(), products);
                }
            } else if (action.equals("getTopSoldoutProducts")) {
                List<Product> products = productDAO.getTopSoldoutProduct(15);
                objectMapper.writeValue(resp.getOutputStream(), products);
            } else if (action.equals("getCategories")) {
                List<Category> categories = productDAO.getCategories();
                objectMapper.writeValue(resp.getOutputStream(), categories);
            } else if (action.equals("getDiscountProducts")) {
                List<Product> products = productDAO.getDiscountProducts();
                objectMapper.writeValue(resp.getOutputStream(), products);
            } else if (action.equals("getProductDetailsById")) {

                String pid = req.getParameter("productId");

                int id = Integer.parseInt(pid);
                List<Image> imageList = i.getImageForPDetails(id);
                List<Rate> rateList = r.getRateByProduct(id);
                Product product = p.getPDetailsById(id);
                product.setImageList(imageList);
                product.setRateList(rateList);
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), product);
            }
        }
    }
}
