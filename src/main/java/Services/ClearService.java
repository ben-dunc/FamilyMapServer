package Services;

import Containers.Result.ClearResult;
import DataAccess.AuthtokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Does work for the clear handler
 */
public class ClearService {
    /**
     * Clears the db
     * @return the clear result object of this function
     */
    public static ClearResult clear() {
        ClearResult clearResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            clearAllTables(connection);

            clearResult = new ClearResult("Clear succeeded.", true);

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            clearResult = new ClearResult("Error: [" + ex.toString() + "]", false);
        }

        return clearResult;
    }

    public static void clearAllTables(Connection connection) throws SQLException {
        AuthtokenDao.clearTable(connection);
        EventDao.clearTable(connection);
        PersonDao.clearTable(connection);
        UserDao.clearTable(connection);
    }
}
