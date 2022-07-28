package Services;

import Containers.Models.Authtoken;
import Containers.Models.Person;
import Containers.Result.PersonResult;
import DataAccess.AuthtokenDao;
import DataAccess.PersonDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Does work for the person handler
 */
public class PersonService {
    /**
     * Processes the person endpoint
     * @param authtoken a unique auth token
     * @return the person result object for this call
     */
    public static PersonResult person(String authtoken) {
        PersonResult personResult;
        Connection connection = null;

        System.out.println("person service called! " + authtoken);

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            System.out.println("Get auth!");
            Authtoken auth = AuthtokenDao.getAuthtoken(connection, authtoken);

            if (auth != null) {
                System.out.println("Auth received! " + auth.toString());

                Person[] persons = PersonDao.getAssociated(connection, auth.getUsername());

                System.out.println(Arrays.toString(persons));

                if (persons.length > 0) {
                    personResult = new PersonResult(true, persons);
                } else {
                    personResult = new PersonResult(false, "Error:[No such persons]");
                }
            } else {
                System.out.println("Auth undefined!");
                personResult = new PersonResult(false, "Error:[Unauthorized]");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            personResult = new PersonResult(false, "Error: [" + ex.toString() + "]");
        }

        return personResult;
    }

    /**
     * Processes the person endpoint with a person ID
     * @param personID a unique person id
     * @param authtoken a unique auth token
     * @return the person result object for this call
     */
    public static PersonResult person(String personID, String authtoken) {
        PersonResult personResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;
            connection.setAutoCommit(false);

            Authtoken auth = AuthtokenDao.getAuthtoken(connection, authtoken);

            if (auth != null) {
                Person person = PersonDao.getPerson(connection, personID, auth.getUsername());

                if (person != null) {
                    personResult = new PersonResult(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(), person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(), person.getSpouseID(), true);
                } else {
                    personResult = new PersonResult(false, "No such person exists in db");
                }
            } else {
                personResult = new PersonResult(false, "Unauthroized");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            personResult = new PersonResult(false, "Error:[" + ex.toString() + "]");
        }

        return personResult;
    }
}
