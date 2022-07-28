package myTest.ServiceTest;

import Containers.Models.Event;
import Containers.Models.Person;
import Containers.Models.User;
import Containers.Request.LoadRequest;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Services.LoadService;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {

    static Connection connection = null;

    @BeforeAll
    static void prepareConnection() {
        connection = TestHelper.prepareConnection();
    }

    @BeforeEach
    @DisplayName("Setup")
    public void setup() throws SQLException {
//        TestHelper.clearTables(connection);
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
    @DisplayName("Positive load")
    public void positiveLoad() throws SQLException, InterruptedException {
        LoadRequest request = new LoadRequest(TestData.users, TestData.persons, TestData.events);
        LoadService.load(request);

        for (User user: TestData.users) {
            User u = UserDao.getUser(connection, user.getUsername());
            assertNotNull(u);
            assertEquals(user, u);
        }
        for (Person person: TestData.persons) {
            Person p = PersonDao.getPerson(connection,person.getPersonID(), person.getAssociatedUsername());
            assertNotNull(p);
            assertEquals(person, p);
        }
        for (Event event: TestData.events) {
            Event e = EventDao.getEvent(connection,event.getEventID(), event.getAssociatedUsername());
            assertNotNull(e);
            assertEquals(event, e);
        }
    }

    @Test
    @DisplayName("Negative load")
    public void negativeLoad() throws SQLException {
        LoadRequest request = new LoadRequest(null, null, null);
        LoadService.load(request);

        for (User user: TestData.users) {
            User u = UserDao.getUser(connection, user.getUsername());
            assertNull(u);
        }
        for (Person person: TestData.persons) {
            Person p = PersonDao.getPerson(connection,person.getPersonID(), person.getAssociatedUsername());
            assertNull(p);
        }
        for (Event event: TestData.events) {
            Event e = EventDao.getEvent(connection,event.getEventID(), event.getAssociatedUsername());
            assertNull(e);
        }
    }
}
