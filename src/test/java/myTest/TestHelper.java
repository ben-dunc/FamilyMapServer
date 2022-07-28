package myTest;

import DataAccess.AuthtokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestHelper {

    public static Connection prepareConnection() {
        String dbName = "db" + File.separator + "familymapserver.db";
        String connectionURL = "jdbc:sqlite:" + dbName;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionURL);
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex2) {
                    ex2.printStackTrace();
                }
            }
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return connection;
    }


    public static void close(Connection connection) {
        try {
            UserDao.clearTable(connection);
            PersonDao.clearTable(connection);
            AuthtokenDao.clearTable(connection);
            EventDao.clearTable(connection);

            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQL EXCEPTION IN clos()");
            ex.printStackTrace();
        }
    }

    public static void commit(Connection connection) {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void clearTables(Connection connection) throws SQLException {
        UserDao.clearTable(connection);
        PersonDao.clearTable(connection);
        AuthtokenDao.clearTable(connection);
        EventDao.clearTable(connection);
    }
}
