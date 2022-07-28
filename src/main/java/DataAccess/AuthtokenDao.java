package DataAccess;

import Containers.Models.Authtoken;
import java.sql.*;

/**
 *  A class for Authtoken DAO
 */
public class AuthtokenDao {
    /**
     * @param connection the db connection
     * clears the auth token table
     */
    public static void clearTable(Connection connection) throws SQLException {
        String sql = "delete from authtoken;";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("SQL success: " + sql);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql);
            throw ex;
        }
    }

    /**
     * gets the username by auth token
     * @param connection the db connection
     * @param authtoken the user's authtoken
     * @return username as a string
     */
    public static Authtoken getAuthtoken(Connection connection, String authtoken) throws SQLException {
        String sql = "select authtoken.* from authtoken where authtoken.authtoken = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authtoken);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String auth = rs.getString(1);
                String username = rs.getString(2);

                System.out.println("SQL success: " + sql + " | " + authtoken);
                return new Authtoken(auth, username);
            }
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + authtoken);
            throw ex;
        }
    }

    /**
     * adds an authtoken object to the table
     * @param connection the db connection
     * @param authtoken the user's authtoken
     */
    public static void newAuthtoken(Connection connection, Authtoken authtoken) throws SQLException {
        String sql = "insert into authtoken (authtoken, username) values (?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
            System.out.println("SQL success: " + sql + " | " + authtoken);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + authtoken);
            throw ex;
        }
    }
}
