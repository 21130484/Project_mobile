package service;

import DAO.RateDAO;
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

}
