package DataAccess;

import Models.User;
import java.sql.*;

/**
 *  A class for User DAO
 */
public class UserDao {
    /**
     * Clears the user table
     * @param connection the db connection
     */
    public static void clearTable(Connection connection) throws SQLException {
        String sql = "delete from user";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("SQL success: " + sql);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql);
            throw ex;
        }
    }

    /**
     * Adds a user to the table
     * @param connection the db connection
     * @param user the user to add to the table
     */
    public static void newUser(Connection connection, User user) throws SQLException {
        String sql = "insert into user (username,  password, email, firstName, lastName, gender, personID) values (?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
            System.out.println("SQL success: " + sql + " | " + user);
        } catch (Exception ex) {
            System.out.println("SQL failure: " + sql + " | " + user);
            throw ex;
        }
    }

    /**
     * gets a user by their authtoken
     * @param connection the database connection
     * @param username the username of the person that were looking for
     * @return the person associated with this person id
     */
    public static User getUser(Connection connection, String username) throws SQLException {
        String sql = "select user.* from user where user.username = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String u = rs.getString(1);
                String password = rs.getString(2);
                String email = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                String gender = rs.getString(6);
                String personID = rs.getString(7);

                System.out.println("SQL success: " + sql + " | " + username);
                return new User(u, password, email, firstName, lastName, gender, personID);
            }
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + username);
            throw ex;
        }
    }
}
