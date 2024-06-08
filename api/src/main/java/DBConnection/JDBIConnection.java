package DBConnection;

import org.jdbi.v3.core.Jdbi;

public class JDBIConnection {
    private static JDBIConnection instance = new JDBIConnection();
    private Jdbi jdbi;
    public static JDBIConnection me(){
        return instance;
    }
    private JDBIConnection(){}
    public Jdbi connect() {
        if(jdbi == null) create();
        return jdbi;
    }

    private void create(){
        String url = "jdbc:mysql://localhost:3306/handmadestore1";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(url, "root", "");
    }
}
