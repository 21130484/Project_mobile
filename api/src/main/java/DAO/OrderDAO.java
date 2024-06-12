package DAO;

import DBConnection.JDBIConnection;
import model.Order;
import model.OrderItem;
import model.Product;
import model.Receiver;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.StringTokenizer;

public class OrderDAO {

    public List<Order> getAllOrder(String userId){
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
                   .bind(0, "Đã hủy")
                   .bind(1, id)
                   .execute() > 0;
       });
    }

    public String insertOrder(String userId, double shippingfee, String note, double totalPrice, String address, String consigneeName, String phoneNumber) {
        String sql = "insert into `order` (totalPrice,status,consigneeName,consigneePhoneNumber, address, shippingFee, userId, note) values (?,?,?,?,?,?,?,?)";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,totalPrice)
                    .bind(1,"Chờ xác nhận")
                    .bind(2,consigneeName)
                    .bind(3, phoneNumber)
                    .bind(4,address)
                    .bind(5,shippingfee)
                    .bind(6,userId)
                    .bind(7,note).executeAndReturnGeneratedKeys("id")
                    .mapTo(String.class).findOne().orElse("");
        });
    }

    public int insertOrderDetail(String orderId, String productId, String productQuantity) {
        String productIds = productId.substring(1, productId.length() -1 );
        String productQuantitys = productQuantity.substring(1, productQuantity.length() -1 );
        StringTokenizer stk = new StringTokenizer(productIds, ", ");
        StringTokenizer stk1 = new StringTokenizer(productQuantitys, ", ");
        int execute = 0;
        Jdbi jdbi = JDBIConnection.me().connect();
        while (stk.hasMoreTokens() && stk1.hasMoreTokens()) {
            String id = stk.nextToken();
            String quantity = stk1.nextToken();
            String sqlSelectProduct = "Select p.id, p.soldout, p.sellingPrice from product p inner join cart_details cd on p.id = cd.productId" +
                    " where cd.id = ?";
            String updateSoldOut = "update product set soldout = ?  where id = ?";
            Product product = jdbi.withHandle(handle ->
                    handle.createQuery(sqlSelectProduct).bind(0, id).mapToBean(Product.class).findOne().orElse(null));
            jdbi.withHandle(handle ->
                    handle.createUpdate(updateSoldOut)
                            .bind(0, product.getSoldout() + Integer.parseInt(quantity))
                            .bind(1, product.getId())
                            .execute());
            if (product != null) {
                String sql = "insert into order_details(orderId, productId, quantity, sellingPrice, finalSellingPrice) " +
                        "values(?,?,?,?,?)";
                double sellingPrice = product.getSellingPrice();
                execute = jdbi.withHandle(handle ->
                        handle.createUpdate(sql)
                                .bind(0, orderId)
                                .bind(1, product.getId())
                                .bind(2, quantity)
                                .bind(3, sellingPrice)
                                .bind(4, sellingPrice * product.getQuantity()).execute());
            }
        }
        return execute;
    }
}
