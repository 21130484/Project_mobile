package DAO;

import DBConnection.JDBIConnection;
import model.Image;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImageDAO {
    public static List<Image> getImagesForProduct(String productId) {
        List<Image> imageList = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("SELECT * from image where productId = :productId ")
                        .bind("productId", productId)
                        .mapToBean(Image.class)
                        .stream().toList());

        return imageList;
    } /**
     * Lấy danh saách id và path của Image.
     * @param id
     * @return
     */

    public static List<Image> getImageForPDetails(int id){
        String sql = "SELECT id, path from image where productId = :productID";

        List<Image> imageList = JDBIConnection.me().connect().withHandle(
                handle -> handle.createQuery(sql)
                        .bind("productID", id)
                        .map(new RowMapper<Image>() {
                            @Override
                            public Image map(ResultSet rs, StatementContext ctx) throws SQLException {
                                return new Image(rs.getInt("id"), rs.getString("path"));
                            }

                        })
                        .list()
        );
        return imageList;
    }


}
