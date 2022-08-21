package myTest.ServiceTest;

import Models.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import DataAccess.UserDao;
import Services.RegisterService;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    static Connection connection = null;

    @BeforeAll
    static void prepareConnection() {
        connection = TestHelper.prepareConnection();
    }

    @BeforeEach
    @DisplayName("Setup")
    public void setup() throws SQLException {
        TestHelper.clearTables(connection);
        commit();
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
    @DisplayName("Positive register")
    public void positiveRegister() throws SQLException {
        User user = TestData.users[0];
        RegisterRequest request = new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender());
        RegisterResult result = RegisterService.register(request);
        assertTrue(result.isSuccess());

        commit();

        User user1 = UserDao.getUser(connection ,user.getUsername());
        assertNotNull(user1);
        assertEquals(user.getUsername(), user1.getUsername());
        assertEquals(user.getPassword(), user1.getPassword());
        assertEquals(user.getEmail(), user1.getEmail());
        assertEquals(user.getFirstName(), user1.getFirstName());
        assertEquals(user.getLastName(), user1.getLastName());
        assertEquals(user.getGender(), user1.getGender());
    }

    @Test
    @DisplayName("Negative register")
    public void negativeRegister() {
        User user = TestData.users[0];
        RegisterRequest request = new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getGender());
        RegisterResult result = RegisterService.register(request);
        assertTrue(result.isSuccess());
        result = RegisterService.register(request);
        assertFalse(result.isSuccess());
    }

}
