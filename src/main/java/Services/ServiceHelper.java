package Services;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceHelper {
    public static Connection getConnection() throws SQLException {
        String dbName = "db" + File.separator + "familymapserver.db";
        String connectionURL = "jdbc:sqlite:" + dbName;
        Connection c = DriverManager.getConnection(connectionURL);
        c.setAutoCommit(false);
        return c;
    }
}
