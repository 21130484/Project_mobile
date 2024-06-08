package service;

import DAO.ImageDAO;
import model.Image;

import java.util.List;

public class ImageService {
    public static ImageService instance;
    public static ImageService getInstance() {
        if (instance == null) instance = new ImageService();
        return instance;
    }

    public List<Image> getImageForPDetails(int id) {
        return ImageDAO.getImageForPDetails(id);
    }
}
