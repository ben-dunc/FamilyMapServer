package myTest.ServiceTest;

import Containers.Models.Event;
import Containers.Models.Person;
import Containers.Models.User;
import Containers.Result.FillResult;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Services.FillService;
import myTest.TestData;
import myTest.TestHelper;
import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {

    private static final String BIRTH_EVENT = "birth";
    private static final String DEATH_EVENT = "death";
    private static final String MARRIAGE_EVENT = "marriage";

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
    @DisplayName("Positive fill")
    public void positiveFill() throws SQLException {
        int[] numGenTest = {0, 1, 2, 3, 4, 5};
        for (User user: TestData.users) {
            UserDao.newUser(connection, user);
        }

        for (int numGen: numGenTest) {
            System.out.println("Number of Generations: " + numGen);
            commit();

            String username = TestData.users[0].getUsername();

            // Run the algorithm
            FillResult result = FillService.fill(username, numGen);

            // Check the number of people
            assertEquals(numPeople(numGen), PersonDao.getAssociated(connection, username).length);
            // Check the number of events
            assertEquals(numEvents(numGen), EventDao.getAssociated(connection, username).length);
        }
    }

    @Test
    @DisplayName("Negative fill")
    public void negativeFill() throws SQLException {
        // Call fill service when the user is not in the db

            String username = TestData.users[0].getUsername();

            // Run the algorithm
            FillResult result = FillService.fill(username, 4);

            // Check the number of people
            assertEquals(0, PersonDao.getAssociated(connection, username).length);
    }

    @Test
    @DisplayName("Positive generateFamily")
    public void positiveGenerateFamily() throws SQLException, InvalidParameterException {
        User user = TestData.users[0];

        // call function
        FillService.generateFamily(connection, 4, user);

        // for each person, check num events, parents, etc... recursively
        Person userPerson = PersonDao.getPerson(connection, user.getPersonID(), user.getUsername());

        assertNotNull(userPerson);

        assertNotNull(userPerson.getFatherID());
        assertNotNull(userPerson.getMotherID());
        Event birth = getEvent(EventDao.getAssociated(connection, user.getUsername()), user.getPersonID(), BIRTH_EVENT);
        assertNotNull(birth);

        Person father = PersonDao.getPerson(connection, userPerson.getFatherID(), userPerson.getAssociatedUsername());
        Person mother = PersonDao.getPerson(connection, userPerson.getMotherID(), userPerson.getAssociatedUsername());

        assertNotNull(father);
        assertNotNull(mother);
        checkPerson(father, 3);
        checkPerson(mother, 3);
    }

    private void checkPerson(Person person, int numGen) throws SQLException {
        // if the num of generations is greater than 0, check parents
        if (numGen > 0) {
            assertNotNull(person.getFatherID());
            assertNotNull(person.getMotherID());

            Person father = PersonDao.getPerson(connection, person.getFatherID(), person.getAssociatedUsername());
            Person mother = PersonDao.getPerson(connection, person.getMotherID(), person.getAssociatedUsername());

            assertNotNull(father);
            assertNotNull(mother);

            assertEquals(father.getSpouseID(), mother.getPersonID());
            assertEquals(mother.getSpouseID(), father.getPersonID());

            checkPerson(father, numGen - 1);
            checkPerson(mother, numGen - 1);
        } else {
            assertNull(person.getFatherID());
            assertNull(person.getMotherID());
        }
        // check the events associated with this person: should be 3
        Event birth = getEvent(EventDao.getAssociated(connection, person.getAssociatedUsername()), person.getPersonID(), BIRTH_EVENT);
        assertNotNull(birth);
        Event marriage = getEvent(EventDao.getAssociated(connection, person.getAssociatedUsername()), person.getPersonID(), MARRIAGE_EVENT);
        assertNotNull(marriage);
        Event death = getEvent(EventDao.getAssociated(connection, person.getAssociatedUsername()), person.getPersonID(), DEATH_EVENT);
        assertNotNull(death);
    }

    @Test
    @DisplayName("Negative generateFamily")
    public void negativeGenerateFamily() {
        assertThrows(InvalidParameterException.class, () -> FillService.generateFamily(connection, -1, TestData.users[0]));
        assertThrows(InvalidParameterException.class, () -> FillService.generateFamily(connection, 1, null));
    }

    private Event getEvent(Event[] events, String personID, String eventType) {
        for (Event event: events) {
            if (event.getPersonID().equals(personID) && event.getEventType().equals(eventType)) {
                return event;
            }
        }

        return null;
    }

    private int numPeople(int numGenerations) {
        int numPersons = 0;
        for (int i = 0; i <= numGenerations; i++) {
            numPersons += Math.pow(2, i);
        }
        return numPersons;
    }

    private int numEvents(int numGenerations) {
        return (numPeople(numGenerations) * 3) - 2;
    }
}