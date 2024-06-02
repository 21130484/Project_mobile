package DAO;

import DBConnection.JDBIConnection;
import model.Product;

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

}