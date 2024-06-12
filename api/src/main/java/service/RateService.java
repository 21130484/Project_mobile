package service;

import DAO.RateDAO;
import DBConnection.JDBIConnection;
import model.Rate;

import java.util.List;

public class RateService {

    public static RateService instance;
    public static RateService getInstance() {
        if (instance == null) instance = new RateService();
        return instance;
    }

    public List<Rate> getRateByProduct(int id) {
        return RateDAO.getRateByProduct(id);
    }

    public void insertIntoRating(int productId, String userId, int starRatings, String comment, String fullName){
        RateDAO.insertIntoRating(productId, userId, starRatings, comment, fullName);
    }


}
