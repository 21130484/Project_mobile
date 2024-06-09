package DAO;

import DBConnection.JDBIConnection;
import model.Order;
import model.OrderItem;

import java.util.List;

public class OrderDAO {

    public List<Order> getAllOrder(int userId){
        String sql = "select * from `order` where userId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,userId)
                    .mapToBean(Order.class)
                    .list();
        });
    }

    public List<OrderItem> getItemForOrder(int orderId) {
        String sql = "SELECT od.orderId, p.sellingPrice, p.name, p.description, od.quantity,temp.PATH FROM order_details od JOIN product p ON od.productId = p.id JOIN (SELECT PATH, productId FROM image GROUP BY productId) temp ON temp.productId = p.id WHERE od.orderId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,orderId)
                    .mapToBean(OrderItem.class)
                    .list();
        });
    }
    public boolean UpdateStatusOrder(String id){
        String sql = "Update `order` SET status = ? WHERE id = ? ";
       return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,"Đã hủy")
                    .bind(1,id)
                    .execute() > 0;
        });
    }
}
