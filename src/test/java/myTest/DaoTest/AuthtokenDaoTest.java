package myTest.DaoTest;

import Containers.Models.Authtoken;
import DataAccess.AuthtokenDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthtokenDaoTest {
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

    // AUTHTOKEN DAO TESTS

    private static String getToken() {
        return UUID.randomUUID().toString().substring(0, 32);
    }

    @Test
    @DisplayName("Positive Authtoken Insert Test")
    public void positiveAuthtokenInsert() {
        try {
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
            Authtoken authtoken = AuthtokenDao.getAuthtoken(connection, TestData.authtokens[0].getAuthtoken());
            assertEquals(authtoken, TestData.authtokens[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Authtoken Insert Test")
    public void negativeAuthtokenInsert() throws SQLException {
        assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
        assertThrows(SQLException.class, () -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
    }

    @Test
    @DisplayName("Positive Authtoken Query Test")
    public void positiveAuthtokenQuery() {
        try {
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[1]));

            Authtoken authtoken = AuthtokenDao.getAuthtoken(connection, TestData.authtokens[0].getAuthtoken());

            assertEquals(authtoken, TestData.authtokens[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Authtoken Query Test")
    public void negativeAuthtokenQuery() {
        try {
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
            Authtoken authtoken = AuthtokenDao.getAuthtoken(connection, TestData.authtokens[1].getAuthtoken());

            assertNull(authtoken);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Authtoken Clear Test")
    public void authtokenClear() {
        try {
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[0]));
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, TestData.authtokens[1]));

            assertDoesNotThrow(() -> AuthtokenDao.clearTable(connection));

            assertNull(AuthtokenDao.getAuthtoken(connection, TestData.authtokens[0].getAuthtoken()));
            assertNull(AuthtokenDao.getAuthtoken(connection, TestData.authtokens[1].getAuthtoken()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
