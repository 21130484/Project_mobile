package controller;

import DAO.CartDAO;
import DAO.ImageDAO;
import DAO.ProductDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import service.ImageService;
import service.ProductService;
import service.RateService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/api-product")
public class ProductController extends HttpServlet {

    private ProductService p = new ProductService();
    private RateService r = new RateService();
    private ImageService i = new ImageService();
    ProductDAO productDAO = new ProductDAO();
    ImageDAO imageDAO = new ImageDAO();
    CartDAO cartDAO = new CartDAO();

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
                List<ProductDetail> productDetails = new ArrayList<>();
                int id;
                for (Product product : products) {
                    id = product.getId();
                    List<Image> imageList = i.getImageForPDetails(id);
                    List<Rate> rateList = r.getRateByProduct(id);
                    ProductDetail productDetail = p.getPDetailsById(id);
                    productDetail.setImageList(imageList);
                    productDetail.setRateList(rateList);
                    productDetails.add(productDetail);
                }
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), productDetails);
            } else if (action.equals("getImageByProductId")) {
                String productId = req.getParameter("productId");
                if (productId != null) {
                    List<Image> images = imageDAO.getImagesForProduct(productId);
                    objectMapper.writeValue(resp.getOutputStream(), images);
                }
            } else if (action.equals("getTopSoldoutProducts")) {
                List<Product> products = productDAO.getTopSoldoutProduct(15);
                List<ProductDetail> productDetails = new ArrayList<>();
                int id;
                for (Product product : products) {
                    id = product.getId();
                    List<Image> imageList = i.getImageForPDetails(id);
                    List<Rate> rateList = r.getRateByProduct(id);
                    ProductDetail productDetail = p.getPDetailsById(id);
                    productDetail.setImageList(imageList);
                    productDetail.setRateList(rateList);
                    productDetails.add(productDetail);
                }
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), productDetails);
            } else if (action.equals("getCategories")) {
                List<Category> categories = productDAO.getCategories();
                objectMapper.writeValue(resp.getOutputStream(), categories);
            } else if (action.equals("getDiscountProducts")) {
                List<Product> products = productDAO.getDiscountProducts();
                List<ProductDetail> productDetails = new ArrayList<>();
                int id;
                for (Product product : products) {
                    id = product.getId();
                    List<Image> imageList = i.getImageForPDetails(id);
                    List<Rate> rateList = r.getRateByProduct(id);
                    ProductDetail productDetail = p.getPDetailsById(id);
                    productDetail.setImageList(imageList);
                    productDetail.setRateList(rateList);
                    productDetails.add(productDetail);
                }
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), productDetails);
            } else if (action.equals("getProductDetailsById")) {
                String pid = req.getParameter("productId");
                int id = Integer.parseInt(pid);
                List<Image> imageList = i.getImageForPDetails(id);
                List<Rate> rateList = r.getRateByProduct(id);
                ProductDetail product = p.getPDetailsById(id);
                product.setImageList(imageList);
                product.setRateList(rateList);
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json");
                objectMapper.writeValue(resp.getOutputStream(), product);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String action = req.getParameter("action");

        if (action != null) {
            if (action.equals("updateQuantity")) {
                int newQuantity = Integer.parseInt(req.getParameter("newQuantity"));
                int cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                int cartId = Integer.parseInt(req.getParameter("cartId"));
                boolean isUpdated = cartDAO.updateQuantity(newQuantity, cartItemId, cartId);
                objectMapper.writeValue(resp.getOutputStream(), isUpdated);
            } else if (action.equals("addCartWithItems")) {

                String userId = req.getParameter("userId");

                int productId = Integer.parseInt(req.getParameter("productId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));
                if (userId != null) {
                    cartDAO.addCartWithItem(userId, productId, quantity);
                    objectMapper.writeValue(resp.getOutputStream(), null);
                }
            }

        }


    }
}
