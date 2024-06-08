package DAO;

import DBConnection.JDBIConnection;
import model.Rate;

import java.util.List;

public class RateDAO {

    public static List<Rate> getRateByProduct(int id) {
        List<Rate> rateList = JDBIConnection.me().connect().withHandle(
                handle -> handle.createQuery("Select u.name as userName , r.starRatings, r.comment, r.createDate " +
                                "from rate r join user u on r.userId = u.id where productId = :productID"
                        ).bind("productID", id)
                        .mapToBean(Rate.class)
                        .stream()
                        .toList()
        );
        return rateList;
    }



}
