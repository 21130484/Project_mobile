package DAO;

import DBConnection.JDBIConnection;
import model.Order;
import model.OrderItem;
import model.Receiver;

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

    public Order insertOrder(Receiver receiver, double shippingfee, String note, double totalPrice, String address) {
        String sql = "insert into `order` (totalPrice,status,consigneeName,consigneePhoneNumber, address, shippingFee, userId, note) values (?,?,?,?,?,?,?,?)";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,totalPrice)
                    .bind(1,"Đang giao")
                    .bind(2,receiver.getName())
                    .bind(3,receiver.getPhoneNumber())
                    .bind(4,address)
                    .bind(5,shippingfee)
                    .bind(6,receiver.getId())
                    .bind(7,note).executeAndReturnGeneratedKeys("id").mapToBean(Order.class).first();
        });
    }
}
