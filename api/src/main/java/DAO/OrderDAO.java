package DAO;

import DBConnection.JDBIConnection;
import model.Order;

import java.util.List;

public class OrderDAO {

    public static List<Order> getAllOrder(int userId){
        String sql = "select * from `order` where userId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,userId)
                    .mapToBean(Order.class)
                    .list();
        });
    }

}
