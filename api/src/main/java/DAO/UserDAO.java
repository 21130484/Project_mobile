package DAO;

import DBConnection.JDBIConnection;
import model.Cart;
import model.CartItem;
import model.Receiver;

import java.util.List;

public class UserDAO {
    public Receiver getUserById(String userId){
        String sql = "SELECT id, name, phoneNumber from user where id = ?";
        return JDBIConnection.me().connect().withHandle(handle -> {
            return handle.createQuery(sql)
                    .bind(0,userId)
                    .mapToBean(Receiver.class).stream().findFirst().orElse(null);
        });
    }
}
