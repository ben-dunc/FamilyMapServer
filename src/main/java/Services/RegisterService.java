package Services;

import Models.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import DataAccess.UserDao;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Does work for the register handler
 */
public class RegisterService {
    /**
     * Processes the register request
     * @param registerRequest the request object for this call
     * @return the register result object for this call
     */
    public static RegisterResult register(RegisterRequest registerRequest) {
        RegisterResult registerResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            User user = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail(), registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getGender(), UUID.randomUUID().toString().substring(0, 8));
            User alreadyRegisteredUser = UserDao.getUser(connection, user.getUsername());
            if (alreadyRegisteredUser == null) {

                UserDao.newUser(connection, user);
                try {
                    FillService.generateFamily(connection, 4, user);
                    String auth = LoginService.loginUser(connection, user.getUsername());
                    registerResult = new RegisterResult(auth, user.getUsername(), user.getPersonID(), true, null);
                } catch (InvalidParameterException ex) {
                    registerResult = new RegisterResult(null, null, null, false, "Error:[" + ex.toString() + "]");
                }

            } else {
                registerResult = new RegisterResult(null, null, null, false, "Error:[User is already registered]");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            registerResult = new RegisterResult(null, null, null, false, "Error:[" + ex.toString() + "]");
        }

        return registerResult;
    }
}
