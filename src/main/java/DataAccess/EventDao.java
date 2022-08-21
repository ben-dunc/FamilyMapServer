package DataAccess;

import Models.Event;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 *  A class for event DAO
 */
public class EventDao {
    /**
     * @param connection the db connection
     * clears the event table
     */
    public static void clearTable(Connection connection) throws SQLException {
        String sql = "delete from event;";

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("SQL success: " + sql);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql);
            throw ex;
        }
    }

    /**
     * Adds an event to the table
     * @param connection the db connection
     * @param event the event object to add
     */
    public static void newEvent(Connection connection, Event event) throws SQLException {
        String sql = "insert into event (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
            System.out.println("SQL success: " + sql + " | " + event);
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + event);
            throw ex;
        }
    }

    /**
     * Gets all the events associated with that username
     * @param connection the db connection
     * @param username the user's username
     * @return an array of events
     */
    public static Event[] getAssociated(Connection connection, String username) throws SQLException {
        Set<Event> events = new HashSet<>();
        String sql = "select event.* from event where event.associatedUsername = ? order by personID, year, eventType";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {

                    String eID = rs.getString(1);
                    String associatedUsername = rs.getString(2);
                    String personId = rs.getString(3);
                    float latitude = rs.getFloat(4);
                    float longitude = rs.getFloat(5);
                    String country = rs.getString(6);
                    String city = rs.getString(7);
                    String eventType = rs.getString(8);
                    int year = rs.getInt(9);

                    events.add(new Event(eID, associatedUsername, personId, latitude, longitude, country, city, eventType, year));
                }
                System.out.println("SQL success: " + sql  + " | " + username);
                Event[] eventArray = new Event[events.size()];
                events.toArray(eventArray);
                return eventArray;
            }
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + username);
            throw ex;
        }

    }

    /**
     * Gets one event by its id
     * @param connection the db connection
     * @param eventID get the event id
     * @return A single event object
     */
    public static Event getEvent(Connection connection, String eventID, String username) throws SQLException {
        String sql = "select event.* from event where event.associatedUsername = ? and event.eventID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, eventID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String eID = rs.getString(1);
                String associatedUsername = rs.getString(2);
                String personId = rs.getString(3);
                float latitude = rs.getFloat(4);
                float longitude = rs.getFloat(5);
                String country = rs.getString(6);
                String city = rs.getString(7);
                String eventType = rs.getString(8);
                int year = rs.getInt(9);

                System.out.println("SQL success: " + sql + " | " + username + " | " + eventID);
                return new Event(eID, associatedUsername, personId, latitude, longitude, country, city, eventType, year);
            }
        } catch (SQLException ex) {
            System.out.println("SQL failure: " + sql + " | " + username + " | " + eventID);
            throw ex;
        }
    }

    /**
     * removes all the events associated with the provided username
     * @param connection the db connection
     * @param username the user's username
     */
    public static void removeAssociated(Connection connection, String username) throws SQLException {
        String sql = "delete from event where event.associatedUsername = ?";

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
