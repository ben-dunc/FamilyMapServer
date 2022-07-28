package DataAccess;

import Containers.Models.Person;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 *  A class for person DAO
 */
public class PersonDao {
    /**
     * Clears the person table
     * @param connection the db connection
     */
    public static void clearTable(Connection connection) throws SQLException {
        String sql = "delete from person";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("SQL success: " + sql);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql);
            throw ex;
        }
    }


    /**
     * adds a person to the table
     * @param connection the db connection
     * @param person the object to add
     */
    public static void newPerson(Connection connection, Person person) throws SQLException {
        String sql = "insert into person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
            System.out.println("SQL success: " + sql  + " | " + person);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql  + " | " + person);
            throw ex;
        }
    }

    /**
     * gets the family of someone by their authtoken
     * @param connection the db connection
     * @param username the username of the user
     * @return all the persons associated with the user
     */
    public static Person[] getAssociated(Connection connection, String username) throws SQLException {
        String sql = "select person.* from person where person.associatedUsername = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                Set<Person> persons = new HashSet<>();
                while(rs.next()) {
                    String personID = rs.getString(1);
                    String associatedUsername = rs.getString(2);
                    String firstName = rs.getString(3);
                    String lastName = rs.getString(4);
                    String gender = rs.getString(5);
                    String fatherID = rs.getString(6);
                    String motherID = rs.getString(7);
                    String spouseID = rs.getString(8);

                    persons.add(new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID));
                }
                System.out.println("SQL success: " + sql + " | " + username);
                Person[] personArray = new Person[persons.size()];
                persons.toArray(personArray);
                return personArray;
            }
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + username);
            throw ex;
        }
    }

    /**
     * gets a person by their id
     * @param connection the db connection
     * @param personID a unique person id
     * @param username a username
     * @return the person associated with this person id
     */
    public static Person getPerson(Connection connection, String personID, String username) throws SQLException {
        String sql = "select person.* from person where person.associatedUsername = ? and person.personID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, personID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("SQL success: " + sql + personID + " | " + username + " | " + personID + " - no result!");
                    return null;
                }

                String pID = rs.getString(1);
                String associatedUsername = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);

                System.out.println("SQL success: " + sql + personID + " | " + username + " | " + personID);
                return new Person(pID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
            }
        }  catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + personID + " | " + username + " | " + personID);
            throw ex;
        }
    }

    /**
     * gets the family of someone by their authtoken
     * @param connection the db connection
     * @param username the username of the user
     */
    public static void removeAssociated(Connection connection, String username) throws SQLException {
        String sql = "delete from person where person.associatedUsername = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("SQL success: " + sql + " | " + username);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + username);
            throw ex;
        }
    }
}
