package Services;

import Models.Authtoken;
import Models.User;
import Request.LoginRequest;
import Result.LoginResult;
import DataAccess.AuthtokenDao;
import DataAccess.UserDao;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Does heavy lifting for the login handler
 */
public class LoginService {
    /**
     * Processes the login request
     * @param loginRequest the request for this call
     * @return the login result object for this call
     */
    public static LoginResult login(LoginRequest loginRequest) {
        LoginResult loginResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            User user = UserDao.getUser(connection, loginRequest.getUsername());

            if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
                loginResult = new LoginResult(null, null, null, "Error:[User does not exist in DB]", false);
            } else {
                String authtoken = loginUser(connection, user.getUsername());
                loginResult = new LoginResult(authtoken, user.getUsername(), user.getPersonID(), null, true);
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            loginResult = new LoginResult(null, null, null, "Error:[" + ex.toString() + "]", false);
        }

        return loginResult;
    }

    public static String loginUser(Connection connection, String username) throws SQLException, InvalidParameterException {
        User user = UserDao.getUser(connection ,username);
        if (user == null) {
            throw new InvalidParameterException("This user doesn't exist!");
        }

        String auth = UUID.randomUUID().toString().substring(0, 32);
        AuthtokenDao.newAuthtoken(connection, new Authtoken(auth, username));
        return auth;
    }
}
