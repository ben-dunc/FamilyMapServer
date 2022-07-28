package myTest.DaoTest;

import Containers.Models.Event;
import DataAccess.EventDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventDaoTest {
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

    // EVENT DAO TESTS

    @Test
    @DisplayName("Positive Event Insert Test")
    public void positiveEventInsert() {
        try {
            EventDao.newEvent(connection, TestData.events[0]);
            Event event = EventDao.getEvent(connection, TestData.events[0].getEventID(), TestData.events[0].getAssociatedUsername());
            assertEquals(event, TestData.events[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Event Insert Test")
    public void negativeEventInsert() {
        try {
            EventDao.newEvent(connection, TestData.events[0]);
            EventDao.newEvent(connection, TestData.events[0]);
            fail("No exception thrown!");
        } catch (SQLException ignored) {}
    }

    @Test
    @DisplayName("Positive Event Query Test")
    public void positiveEventQuery() {
        try {
            EventDao.newEvent(connection, TestData.events[0]);
            EventDao.newEvent(connection, TestData.events[1]);
            EventDao.newEvent(connection, TestData.events[2]);
            EventDao.newEvent(connection, TestData.events[3]);
            EventDao.newEvent(connection, TestData.events[4]);
            EventDao.newEvent(connection, TestData.events[5]);

            Event event = EventDao.getEvent(connection, TestData.events[0].getEventID(), TestData.events[0].getAssociatedUsername());

            assertEquals(event, TestData.events[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Event Query Test")
    public void negativeEventQuery() {
        try {
            EventDao.newEvent(connection, TestData.events[0]);
            EventDao.newEvent(connection, TestData.events[1]);
            EventDao.newEvent(connection, TestData.events[2]);
            EventDao.newEvent(connection, TestData.events[3]);
            EventDao.newEvent(connection, TestData.events[4]);
            EventDao.newEvent(connection, TestData.events[5]);

            Event event = EventDao.getEvent(connection, TestData.events[6].getEventID(), TestData.events[7].getAssociatedUsername());

            assertNull(event);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Event Get Associated")
    public void eventGetAssociated() {
        for (Event event: TestData.events) {
            assertDoesNotThrow(() -> EventDao.newEvent(connection, event));
        }

        Event[] events = null;
        try {
            events = EventDao.getAssociated(connection, TestData.events[0].getAssociatedUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertNotNull(events);
        for (Event event: events) {
            assertEquals(event, findEvent(event.getEventID()));
        }
    }

    private Event findEvent(String eventID) {
        for (Event event: TestData.events) {
            if (event.getEventID().equals(eventID)) {
                return event;
            }
        }
        return null;
    }

    @Test
    @DisplayName("Event Clear Test")
    public void eventClear() {
        try {
            EventDao.newEvent(connection, TestData.events[0]);
            EventDao.newEvent(connection, TestData.events[1]);
            EventDao.newEvent(connection, TestData.events[2]);
            EventDao.newEvent(connection, TestData.events[3]);
            EventDao.newEvent(connection, TestData.events[4]);
            EventDao.newEvent(connection, TestData.events[5]);

            EventDao.clearTable(connection);

            assertNull(EventDao.getEvent(connection, TestData.events[0].getEventID(), TestData.events[0].getAssociatedUsername()));
            assertNull(EventDao.getEvent(connection, TestData.events[1].getEventID(), TestData.events[1].getAssociatedUsername()));
            assertNull(EventDao.getEvent(connection, TestData.events[2].getEventID(), TestData.events[2].getAssociatedUsername()));
            assertNull(EventDao.getEvent(connection, TestData.events[3].getEventID(), TestData.events[3].getAssociatedUsername()));
            assertNull(EventDao.getEvent(connection, TestData.events[4].getEventID(), TestData.events[4].getAssociatedUsername()));
            assertNull(EventDao.getEvent(connection, TestData.events[5].getEventID(), TestData.events[5].getAssociatedUsername()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
