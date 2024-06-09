package DAO;

import DBConnection.JDBIConnection;
import model.Order;
import model.OrderItem;
import model.Receiver;

import java.util.List;
import java.util.StringTokenizer;

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

    public String insertOrder(String userId, double shippingfee, String note, double totalPrice, String address, String consigneeName) {
        String sql = "insert into `order` (totalPrice,status,consigneeName,consigneePhoneNumber, address, shippingFee, userId, note) values (?,?,?,?,?,?,?,?)";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,totalPrice)
                    .bind(1,"Đang giao")
                    .bind(2,consigneeName)
                    .bind(3, "")
                    .bind(4,address)
                    .bind(5,shippingfee)
                    .bind(6,userId)
                    .bind(7,note).executeAndReturnGeneratedKeys("id")
                    .mapTo(String.class).findOne().orElse("");
        });
    }

    public int insertOrderDetail(String orderId, String productId) {
        String productIds = productId.substring(1, productId.length() -1 );
        StringTokenizer stk = new StringTokenizer(productIds, ", ");
        int execute = 0;
        while (stk.hasMoreTokens()) {
            String id = stk.nextToken();
            String sql = "insert into order_details(orderId, productId, quantity, sellingPrice, finalSellingPrice) " +
                    "values(?,?,?,?,?)";
            execute = JDBIConnection.me().connect().withHandle(handle ->
                    handle.createUpdate(sql)
                            .bind(0, orderId)
                            .bind(1, id)
                            .bind(2, 1)
                            .bind(3, 1)
                            .bind(4, 1).execute());
        }
        return execute;
    }
}
