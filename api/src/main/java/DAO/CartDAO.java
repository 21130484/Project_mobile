package DAO;

import DBConnection.JDBIConnection;
import model.Cart;
import model.CartItem;
import model.Product;

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
//
//<<<<<<< HEAD
//
    /**
     * INSERT DB INTO CART.
     * @param userId
     * @return
     */
//    public static int  insertCart(String userId){
//        String sql = "Insert into cart (userId) values(:userId)";
//    return JDBIConnection.me().connect().withHandle(handle ->
//            handle.createUpdate(sql)
//                    .bind("userId", userId)
//                    .executeAndReturnGeneratedKeys("id")
//                    .mapTo(int.class)
//                    .one()
//            );
//    }

    public static int findCartByUserId(String userId){
        String sql = "Select id from cart where userId = :userId";
    return JDBIConnection.me().connect().withHandle(handle ->
            handle.createQuery(sql).
                    bind("userId", userId)
                    .mapTo(int.class)
                    .one()
            );

    }

    public static void insertCartItem(int p , int quantity, int cartId){
        String sql = "Insert into cart_details (productId, quantity, cartId)" +
                "values(:productId, :quantity, :cartId)" ;

        JDBIConnection.me().connect().useHandle(handle ->
                handle.createUpdate(sql).bind("productId", p)
                        .bind("quantity", quantity)
                        .bind("cartId", cartId)
                        .execute()
        );
    }
    public static void addCartWithItem(String userId, int productId, int quantity) {
        String sql = "INSERT INTO cart_details (productId, quantity, cartId) " +
                "SELECT :productId, :quantity, cart.id " +
                "FROM cart WHERE cart.userId = :userId";

        JDBIConnection.me().connect().useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("productId", productId)
                        .bind("quantity", quantity)
                        .bind("userId", userId)
                        .execute()
        );
    }
//    public  void addCartWithItem(String userId, int productId, int quantity) {
//                int cartId = findCartByUserId(userId);
//                insertCartItem(productId, quantity, cartId);
//    }
//
//
//=======
    public boolean deleteCartItem(int cartItemId, int cartId) {
        String sql = "delete from cart_details where id = ? and cartId = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createUpdate(sql)
                    .bind(0,cartItemId)
                    .bind(1,cartId)
                    .execute() > 0;
        });
    }

    public int createCart(String userId) {
        String sql = "Insert into cart(userId) values(?)";
        return JDBIConnection.me().connect().withHandle(handle ->
                handle.createUpdate(sql).bind(0, userId).execute());
    }

    public static void main(String[] args) {
        addCartWithItem("kobi1", 38, 2);
    }






}
