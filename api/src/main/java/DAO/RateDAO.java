package DAO;

import DBConnection.JDBIConnection;
import model.Rate;

import java.util.List;

public class RateDAO {

    public static List<Rate> getRateByProduct(int id) {
        List<Rate> rateList = JDBIConnection.me().connect().withHandle(
                handle -> handle.createQuery("Select  fullName , starRatings, comment, createDate " +
                                "from rate where productId = :productID"
                        ).bind("productID", id)
                        .mapToBean(Rate.class)
                        .stream()
                        .toList()
        );
        return rateList;
    }



}
