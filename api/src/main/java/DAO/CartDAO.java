package DAO;

import DBConnection.JDBIConnection;
import model.Cart;
import model.CartItem;

import java.util.List;

public class CartDAO {

    public List<CartItem> getAllCartItem(int userId){
        String sql = "select cd.id, c.id as cartId, p.name, p.description, p.sellingPrice, temp.PATH, cd.quantity from cart c JOIN cart_details cd ON c.id = cd.cartId join product p on cd.productId = p.id JOIN (SELECT productId, PATH FROM image i JOIN product p ON i.productId = p.id GROUP BY productId) as temp ON temp.productId = p.id WHERE userId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,userId)
                    .mapToBean(CartItem.class)
                    .list();
        });
    }
    public Cart getCartByUserId(int userId){
        String sql = "SELECT c.id, c.userId from cart c join user u on c.userId = u.id where userId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,userId)
                    .mapToBean(Cart.class).stream().findFirst().orElse(null);
        });
    }

    public boolean updateQuantity(int newQuantity, int cartItemId, int cartId) {
        String sql = "update cart_details set quantity = ? where id = ? and cartId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,newQuantity)
                    .bind(1,cartItemId)
                    .bind(2,cartId)
                    .execute() > 0;
        });
    }

    public boolean deleteCartItem(int cartItemId, int cartId) {
        String sql = "delete from cart_details where id = ? and cartId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,cartItemId)
                    .bind(1,cartId)
                    .execute() > 0;
        });
    }
}
