package api;

import com.google.gson.Gson;
import model.bean.Image;
import model.bean.Product;
import model.service.ImageService;
import model.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api-product")
public class ProductAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Gson gson = new Gson();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        if (action != null) {
            if (action.equals("getAllProduct")) {
                List<Product> products = ProductService.getInstance().getAll();
                String jsonResponse = gson.toJson(products);
                resp.getWriter().write(jsonResponse);
            } else if (action.equals("getImageByIdProduct")) {
                String productId = req.getParameter("productId");
                if (productId != null) {
                    List<Image> images = ImageService.getInstance().getImagesForProduct(productId);
                    String jsonResponse = gson.toJson(images);
                    resp.getWriter().write(jsonResponse);
                }
            } else if (action.equals("getProductById")) {
                String productId = req.getParameter("productId");
                if (productId != null) {
                    Product products = ProductService.getInstance().getProductById(productId);
                    String jsonResponse = gson.toJson(products);
                    resp.getWriter().write(jsonResponse);
                }
            }
        }
    }
}
