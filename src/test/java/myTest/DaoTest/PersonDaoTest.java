package myTest.DaoTest;

import Containers.Models.Person;
import DataAccess.PersonDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonDaoTest {
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

    // PERSON DAO TESTS

    @Test
    @DisplayName("Positive Person Insert Test")
    public void positivePersonInsert() {
        try {
            PersonDao.newPerson(connection, TestData.persons[0]);
            Person person = PersonDao.getPerson(connection, TestData.persons[0].getPersonID(), TestData.persons[0].getAssociatedUsername());
            assertEquals(person, TestData.persons[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Person Insert Test")
    public void negativePersonInsert() {
        try {
            PersonDao.newPerson(connection, TestData.persons[0]);
            PersonDao.newPerson(connection, TestData.persons[0]);
            fail("No exception thrown!");
        } catch (SQLException ignored) {}
    }

    @Test
    @DisplayName("Positive Person Query Test")
    public void positivePersonQuery() {
        try {
            PersonDao.newPerson(connection, TestData.persons[0]);
            PersonDao.newPerson(connection, TestData.persons[1]);
            PersonDao.newPerson(connection, TestData.persons[2]);
            PersonDao.newPerson(connection, TestData.persons[3]);
            PersonDao.newPerson(connection, TestData.persons[4]);
            PersonDao.newPerson(connection, TestData.persons[5]);

            Person person = PersonDao.getPerson(connection, TestData.persons[0].getPersonID(), TestData.persons[0].getAssociatedUsername());

            assertEquals(person, TestData.persons[0]);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Negative Person Query Test")
    public void negativePersonQuery() {
        try {
            PersonDao.newPerson(connection, TestData.persons[0]);
            PersonDao.newPerson(connection, TestData.persons[1]);
            PersonDao.newPerson(connection, TestData.persons[2]);
            PersonDao.newPerson(connection, TestData.persons[3]);
            PersonDao.newPerson(connection, TestData.persons[4]);
            PersonDao.newPerson(connection, TestData.persons[5]);

            Person person = PersonDao.getPerson(connection, TestData.persons[6].getPersonID(), TestData.persons[7].getAssociatedUsername());

            assertNull(person);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("Person Get Associated")
    public void personGetAssociated() {
        for (Person person: TestData.persons) {
            assertDoesNotThrow(() -> PersonDao.newPerson(connection, person));
        }

        Person[] persons = null;
        try {
            persons = PersonDao.getAssociated(connection, TestData.persons[0].getAssociatedUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertNotNull(persons);
        for (Person person: persons) {
            assertEquals(person, findPerson(person.getPersonID()));
        }
    }

    private Person findPerson(String personID) {
        for (Person person: TestData.persons) {
            if (person.getPersonID().equals(personID)) {
                return person;
            }
        }
        return null;
    }

    @Test
    @DisplayName("Person Clear Test")
    public void personClear() {
        try {
            PersonDao.newPerson(connection, TestData.persons[0]);
            PersonDao.newPerson(connection, TestData.persons[1]);
            PersonDao.newPerson(connection, TestData.persons[2]);
            PersonDao.newPerson(connection, TestData.persons[3]);
            PersonDao.newPerson(connection, TestData.persons[4]);
            PersonDao.newPerson(connection, TestData.persons[5]);

            PersonDao.clearTable(connection);

            assertNull(PersonDao.getPerson(connection, TestData.persons[0].getPersonID(), TestData.persons[0].getAssociatedUsername()));
            assertNull(PersonDao.getPerson(connection, TestData.persons[1].getPersonID(), TestData.persons[1].getAssociatedUsername()));
            assertNull(PersonDao.getPerson(connection, TestData.persons[2].getPersonID(), TestData.persons[2].getAssociatedUsername()));
            assertNull(PersonDao.getPerson(connection, TestData.persons[3].getPersonID(), TestData.persons[3].getAssociatedUsername()));
            assertNull(PersonDao.getPerson(connection, TestData.persons[4].getPersonID(), TestData.persons[4].getAssociatedUsername()));
            assertNull(PersonDao.getPerson(connection, TestData.persons[5].getPersonID(), TestData.persons[5].getAssociatedUsername()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
