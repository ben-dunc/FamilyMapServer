package myTest.ServiceTest;

import Models.Person;
import DataAccess.PersonDao;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {

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
    @DisplayName("Positive get person")
    public void positivePerson() throws SQLException {
        insertAllPersons();

        for (Person person : TestData.persons) {
            Person returnedPerson = PersonDao.getPerson(connection, person.getPersonID(), person.getAssociatedUsername());
            assertEquals(returnedPerson, person);
        }
    }

    @Test
    @DisplayName("Negative get person")
    public void negativePerson() throws SQLException {
        // Check for persons that are not in the db
        for (Person person : TestData.persons) {
            Person returnedPerson = PersonDao.getPerson(connection, person.getPersonID(), person.getAssociatedUsername());
            assertNull(returnedPerson);
        }
    }

    @Test
    @DisplayName("Positive get associated persons")
    public void positiveAssociatedPersons() throws SQLException {
        insertAllPersons();

        String associatedUsername = "sheila";
        Person[] persons = PersonDao.getAssociated(connection, associatedUsername);

        assertNotEquals(persons.length, TestData.persons.length);

        for (Person person: persons) {
            int foundIndex = 0;
            boolean foundPerson = false;
            for (int i = 0; i < TestData.persons.length; i++) {
                if (person.getPersonID().equals(TestData.persons[i].getPersonID())) {
                    foundIndex = i;
                    foundPerson = true;
                }
            }
            assertTrue(foundPerson);
            assertEquals(person, TestData.persons[foundIndex]);
        }
    }

    @Test
    @DisplayName("Negative get associated persons")
    public void negativeAssociatedPersons() throws SQLException {
        insertAllPersons();
        String invalidUsername = "jerry";
        assertEquals(PersonDao.getAssociated(connection, invalidUsername).length, 0);
    }

    private void insertAllPersons() {
        for (Person person : TestData.persons) {
            assertDoesNotThrow(() -> PersonDao.newPerson(connection, person));
        }
    }
}
