package DAO;
import DBConnection.JDBIConnection;
import model.BannerItem;

import java.util.List;
import java.util.stream.Collectors;

public class BannerItemDAO {
    public List<BannerItem> getAll() {
        List<BannerItem> bannerItems = JDBIConnection.me().connect().withHandle(handle ->
                handle.createQuery("select * from banner_items").mapToBean(BannerItem.class).stream().collect(Collectors.toList()));
        return bannerItems;
    }
}