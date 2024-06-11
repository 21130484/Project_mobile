package DAO;

import DBConnection.JDBIConnection;
import model.Rate;

import java.sql.Timestamp;
import java.util.List;

public class RateDAO {

    public  static  List<Rate> getRateByProduct(int id) {
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


    public  void insertIntoRating(int productId, String userId, int starRatings, String comment, String fullName){
        String sql = "Insert into rate (productId, userId, starRatings, comment, fullName)" +
                "values(:productId, :userId, :starRatings, :comment, :fullName)";
        JDBIConnection.me().connect().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("productId", productId)
                        .bind("userId", userId)
                        .bind("starRatings", starRatings)
                        .bind("comment", comment)
                        .bind("fullName", fullName)
                        .execute()
        );
    }




}
