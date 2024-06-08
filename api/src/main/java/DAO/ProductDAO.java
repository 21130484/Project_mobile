package DAO;

import DBConnection.JDBIConnection;
import model.Category;
import model.Product;
import model.ProductDetail;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    //Tất cả các sản phẩm
    public static List<Product> getAll() {
        List<Product> product = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("select * from product")
                        .mapToBean(Product.class)
                        .stream()
                        .toList()
        );
        return product;
    }

    public static Product getProductById(final String id) {
        Optional<Product> product = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("select * from product where id= :id")
                        .bind("id", id)
                        .mapToBean(Product.class)
                        .stream()
                        .findFirst()
        );
        return product.isEmpty() ? null : product.get();
    }
    public static List<Product> getTopSoldoutProduct(int number) {
        List<Product> products = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("SELECT * FROM product where soldout > 0 order by soldout limit ?")
                        .bind(0, number)
                        .mapToBean(Product.class)
                        .stream().toList());
        return products;
    }
    public static List<Category> getCategories() {
        List<Category> categories = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("SELECT * FROM category")
                        .mapToBean(Category.class)
                        .stream().toList());
        return categories;
    }
    public static List<Product> getDiscountProducts() {
        List<Product> products = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("SELECT p.* FROM product p " +
                                "JOIN discount d ON p.discountId = d.id " +
                                "WHERE p.discountId IS NOT NULL " +
                                "AND NOW() BETWEEN d.startDate AND d.endDate")
                        .mapToBean(Product.class)
                        .stream().toList());
        return products;
    }

    /*
   Lấy product detail từ id.
    */
    public static ProductDetail getPDetailsById(int productID) {
        ProductDetail p = JDBIConnection.me().connect().withHandle(
                handle -> handle.createQuery("SELECT id, name, description, sellingPrice, quantity - soldout as stock, categoryId, isSale" +
                                " from product where id = :productID")
                        .bind("productID", productID)
                        .mapToBean(ProductDetail.class).findFirst().orElse(null)
        );
        return p;
    }
    public static void main(String[] args) {
        System.out.println(getAll());
    }
}