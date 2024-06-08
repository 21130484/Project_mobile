package service;

import DAO.ProductDAO;
import model.Product;

public class ProductService {
    public static ProductService instance;
    public static ProductService getInstance() {
        if (instance == null) instance = new ProductService();
        return instance;
    }

    public Product getPDetailsById(int id) {
        return ProductDAO.getPDetailsById(id);
    }
}
