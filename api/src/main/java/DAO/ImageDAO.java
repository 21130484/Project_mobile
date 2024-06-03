package DAO;

import DBConnection.JDBIConnection;
import model.Image;

import java.util.List;
import java.util.Optional;

public class ImageDAO {
    public static List<Image> getImagesForProduct(String productId) {
        List<Image> imageList = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("SELECT * from image where productId = :productId ")
                        .bind("productId", productId)
                        .mapToBean(Image.class)
                        .stream().toList());

        return imageList;
    }
}
