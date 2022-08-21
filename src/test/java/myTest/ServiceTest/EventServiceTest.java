package myTest.ServiceTest;

import Models.Event;
import DataAccess.EventDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {

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
    @DisplayName("Positive get event")
    public void positiveEvent() throws SQLException {
        insertAllEvents();

        for (Event event : TestData.events) {
            Event returnedEvent = EventDao.getEvent(connection, event.getEventID(), event.getAssociatedUsername());
            assertEquals(returnedEvent, event);
        }
    }

    @Test
    @DisplayName("Negative get event")
    public void negativeEvent() throws SQLException {
        // Check for events that are not in the db
        for (Event event : TestData.events) {
            Event returnedEvent = EventDao.getEvent(connection, event.getEventID(), event.getAssociatedUsername());
            assertNull(returnedEvent);
        }
    }

    @Test
    @DisplayName("Positive get associated events")
    public void positiveAssociatedEvents() throws SQLException {
        insertAllEvents();

        String associatedUsername = "sheila";
        Event[] events = EventDao.getAssociated(connection, associatedUsername);

        assertNotEquals(events.length, TestData.events.length);

        for (Event event: events) {
            int foundIndex = 0;
            boolean foundEvent = false;
            for (int i = 0; i < TestData.events.length; i++) {
                if (event.getEventID().equals(TestData.events[i].getEventID())) {
                    foundIndex = i;
                    foundEvent = true;
                }
            }
            assertTrue(foundEvent);
            assertEquals(event, TestData.events[foundIndex]);
        }
    }

    @Test
    @DisplayName("Negative get associated events")
    public void negativeAssociatedEvents() throws SQLException {
        insertAllEvents();
        String invalidUsername = "jerry";
        assertEquals(EventDao.getAssociated(connection, invalidUsername).length, 0);
    }

    private void insertAllEvents() {
        for (Event event : TestData.events) {
            assertDoesNotThrow(() -> EventDao.newEvent(connection, event));
        }
    }
}
