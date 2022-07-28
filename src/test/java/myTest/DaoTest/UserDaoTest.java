package myTest.DaoTest;

import Containers.Models.User;
import DataAccess.UserDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
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

    // USER DAO TESTS

    @Test
    @DisplayName("Positive User Insert Test")
    public void positiveUserInsert() {
        try {
            UserDao.newUser(connection, TestData.users[0]);
            User user = UserDao.getUser(connection, TestData.users[0].getUsername());
            assertEquals(user, TestData.users[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative User Insert Test")
    public void negativeUserInsert() throws SQLException {
        UserDao.newUser(connection, TestData.users[0]);
        assertThrows(SQLException.class, () -> UserDao.newUser(connection, TestData.users[0]));
    }

    @Test
    @DisplayName("Positive User Query Test")
    public void positiveUserQuery() {
        try {
            UserDao.newUser(connection, TestData.users[0]);
            UserDao.newUser(connection, TestData.users[1]);

            User user = UserDao.getUser(connection, TestData.users[0].getUsername());

            assertEquals(user, TestData.users[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative User Query Test")
    public void negativeUserQuery() {
        try {
            UserDao.newUser(connection, TestData.users[0]);

            User user = UserDao.getUser(connection, TestData.users[1].getUsername());

            assertNull(user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("User Clear Test")
    public void userClear() {
        try {
            UserDao.newUser(connection, TestData.users[0]);
            UserDao.newUser(connection, TestData.users[1]);

            UserDao.clearTable(connection);

            assertNull(UserDao.getUser(connection, TestData.users[0].getUsername()));
            assertNull(UserDao.getUser(connection, TestData.users[1].getUsername()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
