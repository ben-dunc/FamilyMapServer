package Services;

import Containers.JSON.*;
import Containers.Models.Event;
import Containers.Models.Person;
import Containers.Models.User;
import Containers.Result.FillResult;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import com.google.gson.Gson;

import java.io.*;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

/**
 * Does work for the fill handler
 */
public class FillService {
    private static final FemaleNames femaleNames;
    private static final MaleNames maleNames;
    private static final Surnames surnames;
    private static final Locations locations;
    private static final String jsonFilePath = "json/";
    private static final String fnamesJSON = "fnames.json";
    private static final String mnamesJSON = "mnames.json";
    private static final String snamesJSON = "snames.json";
    private static final String locationsJSON = "locations.json";

    private static final String BIRTH_EVENT = "birth";
    private static final String DEATH_EVENT = "death";
    private static final String MARRIAGE_EVENT = "marriage";
    private static final int GEN_GAP = 20;
    private static final int LIFE_SPAN = 80;

    static {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(jsonFilePath + fnamesJSON)) {
            femaleNames = gson.fromJson(reader, FemaleNames.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileReader reader = new FileReader(jsonFilePath + mnamesJSON)) {
            maleNames = gson.fromJson(reader, MaleNames.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileReader reader = new FileReader(jsonFilePath + snamesJSON)) {
            surnames = gson.fromJson(reader, Surnames.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileReader reader = new FileReader(jsonFilePath + locationsJSON)) {
            locations = gson.fromJson(reader, Locations.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Processes the fill request
     * @return the fill result object for this call
     */
    public static FillResult fill(String username, int numGenerations) {
        FillResult fillResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            User user = UserDao.getUser(connection, username);

            if (user == null) {
                fillResult = new FillResult("The given username isn't in the database", false);
            } else {
                PersonDao.removeAssociated(connection, username);
                EventDao.removeAssociated(connection, username);

                try {
                    generateFamily(connection, numGenerations, user);
                    int numPersons = 0;
                    for (int i = 0; i <= numGenerations; i++) {
                        numPersons += Math.pow(2, i);
                    }

                    int numEvents = (numPersons * 3) - 2;

                    fillResult = new FillResult("Successfully added " + numPersons + " persons and " + numEvents + " events to the database", true);
                } catch (InvalidParameterException ex) {
                    fillResult = new FillResult(ex.getMessage(), false);
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            fillResult = new FillResult("Error:[" + ex.toString() + "]", false);
        }

        return fillResult;
    }

    public static void generateFamily(Connection connection, int numGenerations, User user) throws InvalidParameterException {
        if (numGenerations < 0) {
            System.out.println("");
            throw new InvalidParameterException("The number of generations needs to be greater than 0");
        } else if (user == null) {
            System.out.println("");
            throw new InvalidParameterException("The user parameter is undefined");
        }

        final int BIRTH_YEAR = 2000;

        Person p = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender(), null, null, null);

        if (numGenerations > 0) {
            Person father = generatePerson(connection, user,"m", numGenerations - 1, BIRTH_YEAR - GEN_GAP);
            Person mother = generatePerson(connection, user,"f", numGenerations - 1, BIRTH_YEAR - GEN_GAP);

            p.setFatherID(father.getPersonID());
            p.setMotherID(mother.getPersonID());
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());
            Event fm = generateEvent(MARRIAGE_EVENT, BIRTH_YEAR + GEN_GAP, user.getUsername(), father.getPersonID());
            Event mm = new Event(UUID.randomUUID().toString().substring(0,8), fm.getAssociatedUsername(), mother.getPersonID(), fm.getLatitude(), fm.getLongitude(), fm.getCountry(), fm.getCity(), fm.getEventType(), fm.getYear());
            try {
                PersonDao.newPerson(connection, mother);
                PersonDao.newPerson(connection, father);
                EventDao.newEvent(connection, fm);
                EventDao.newEvent(connection, mm);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        Event birth = generateEvent(BIRTH_EVENT, BIRTH_YEAR, user.getUsername(), user.getPersonID());

        try {
            PersonDao.newPerson(connection, p);
            EventDao.newEvent(connection, birth);
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static Person generatePerson(Connection connection, User user, String gender, int numGeneration, int year) {
        Person person = generatePerson(user.getUsername(), gender);

        if (numGeneration > 0) {
            Person father = generatePerson(connection, user,"m", numGeneration - 1, year - GEN_GAP);
            Person mother = generatePerson(connection, user,"f", numGeneration - 1, year - GEN_GAP);

            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            Event fm = generateEvent(MARRIAGE_EVENT, year + GEN_GAP, user.getUsername(), father.getPersonID());
            Event mm = new Event(UUID.randomUUID().toString().substring(0,8), fm.getAssociatedUsername(), mother.getPersonID(), fm.getLatitude(), fm.getLongitude(), fm.getCountry(), fm.getCity(), fm.getEventType(), fm.getYear());

            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

            try {
                EventDao.newEvent(connection, fm);
                EventDao.newEvent(connection, mm);
                PersonDao.newPerson(connection, mother);
                PersonDao.newPerson(connection, father);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        Event birth = generateEvent(BIRTH_EVENT, year, user.getUsername(), person.getPersonID());
        Event death = generateEvent(DEATH_EVENT, year + LIFE_SPAN, user.getUsername(), person.getPersonID());

        try {
            EventDao.newEvent(connection, birth);
            EventDao.newEvent(connection, death);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return person;
    }

    private static Location getRandomLocation() {
        int index = (int) Math.floor(Math.random() * locations.getData().length);
        return locations.getData()[index];
    }

    private static String randomFemaleName() {
        int index = (int) Math.floor(Math.random() * femaleNames.getData().length);
        return femaleNames.getData()[index];
    }

    private static String randomMaleName() {
        int index = (int) Math.floor(Math.random() * maleNames.getData().length);
        return maleNames.getData()[index];
    }

    private static String randomSurname() {
        int index = (int) Math.floor(Math.random() * surnames.getData().length);
        return surnames.getData()[index];
    }

    private static Event generateEvent(String eventType, int year, String associatedUsername, String personID) {
        Location l = getRandomLocation();
        return new Event(UUID.randomUUID().toString().substring(0,8), associatedUsername, personID, l.getLatitude(), l.getLongitude(), l.getCountry(), l.getCity(), eventType, year);
    }

    private static Person generatePerson(String username, String gender) {
        return new Person(UUID.randomUUID().toString().substring(0,8), username, Objects.equals(gender, "m") ? randomMaleName() : randomFemaleName(), randomSurname(), gender, null, null, null);
    }
}
