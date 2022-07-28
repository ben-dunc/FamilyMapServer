package myTest.ServiceTest;

import Containers.Models.Authtoken;
import Containers.Models.Event;
import Containers.Models.Person;
import Containers.Models.User;
import DataAccess.AuthtokenDao;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Services.ClearService;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {

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
    @DisplayName("Positive clear")
    public void positiveClear() throws SQLException {
        for (User user: TestData.users) {
            assertDoesNotThrow(() -> UserDao.newUser(connection, user));
        }
        for (Person person: TestData.persons) {
            assertDoesNotThrow(() -> PersonDao.newPerson(connection, person));
        }
        for (Event event: TestData.events) {
            assertDoesNotThrow(() -> EventDao.newEvent(connection, event));
        }
        for (Authtoken authtoken: TestData.authtokens) {
            assertDoesNotThrow(() -> AuthtokenDao.newAuthtoken(connection, authtoken));
        }

        TestHelper.commit(connection);

        assertDoesNotThrow(ClearService::clear);

        assertNull(UserDao.getUser(connection, TestData.users[0].getUsername()));
        assertEquals(0, EventDao.getAssociated(connection, TestData.events[0].getAssociatedUsername()).length);
        assertEquals(0, PersonDao.getAssociated(connection, TestData.persons[0].getAssociatedUsername()).length);
        assertNull(AuthtokenDao.getAuthtoken(connection, TestData.authtokens[0].getAuthtoken()));
    }

    @Test
    @DisplayName("Negative clear")
    public void negativeClear() {
        // WHAT TO DO HERE? HOW CAN IT FAIL?
    }
}
