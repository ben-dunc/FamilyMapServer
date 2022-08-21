package myTest.ServiceTest;

import Models.User;
import Request.LoginRequest;
import Result.LoginResult;
import DataAccess.AuthtokenDao;
import DataAccess.UserDao;
import Services.LoginService;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    static Connection connection = null;

    @BeforeAll
    static void prepareConnection() {
        connection = TestHelper.prepareConnection();
    }

    @BeforeEach
    @DisplayName("Setup")
    public void setup() throws SQLException {
        TestHelper.clearTables(connection);
    }

    @AfterEach
    @DisplayName("Commit")
    public void commit() {
        TestHelper.commit(connection);
    }

    @AfterAll
    @DisplayName("Close everything")
    static void close() {
        TestHelper.close(connection);
    }

    // TESTS

    @Test
    @DisplayName("Positive login")
    public void positiveLogin() throws SQLException {
        User user = TestData.users[0];
        UserDao.newUser(connection, user);

        commit();

        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        LoginResult result = LoginService.login(request);
        assertTrue(result.getSuccess());
    }

    @Test
    @DisplayName("Negative login")
    public void negativeLogin() throws SQLException {
        User user = TestData.users[0];

        LoginRequest request = new LoginRequest(user.getUsername(), user.getPassword());
        LoginResult result = LoginService.login(request);
        assertFalse(result.getSuccess());
    }

    @Test
    @DisplayName("Positive loginUser")
    public void positiveLoginUser() throws SQLException {
        User user = TestData.users[0];
        UserDao.newUser(connection, user);

        String authtoken = LoginService.loginUser(connection, user.getUsername());

        assertNotNull(AuthtokenDao.getAuthtoken(connection, authtoken));
    }

    @Test
    @DisplayName("Negative loginUser")
    public void negativeLoginUser() throws SQLException {
        User user = TestData.users[0];

        assertThrows(InvalidParameterException.class, () -> LoginService.loginUser(connection, user.getUsername()));
    }
}
