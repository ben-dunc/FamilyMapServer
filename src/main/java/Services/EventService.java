package Services;

import Models.Authtoken;
import Models.Event;
import Result.EventResult;
import DataAccess.AuthtokenDao;
import DataAccess.EventDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Does work for the event handler.
 */
public class EventService {
    /**
     * Processes the event endpoint "/event"
     * @return the event result object of this function
     */
    public static EventResult event(String authtoken) {
        EventResult eventResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            Authtoken auth = AuthtokenDao.getAuthtoken(connection, authtoken);
            if (auth != null) {
                Event[] events = EventDao.getAssociated(connection, auth.getUsername());

                System.out.println(Arrays.toString(events));

                eventResult = new EventResult(true, events);
            } else {
                eventResult = new EventResult(false, "Error:[Unauthorized]");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            eventResult = new EventResult(false, "Error: [" + ex.toString() + "]");
        }

        return eventResult;
    }

    /**
     * Process the event endpoint "/event/[eventID]"
     * @param eventID a unique event id
     * @return the event result object of this function
     */
    public static EventResult event(String eventID, String authtoken) {
        EventResult eventResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;

            Authtoken auth = AuthtokenDao.getAuthtoken(connection, authtoken);
            if (auth != null) {
                Event event = EventDao.getEvent(connection, eventID, auth.getUsername());

                if (event != null) {
                    eventResult = new EventResult(event.getAssociatedUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear(), true);
                } else {
                    eventResult = new EventResult(false, "Error:[No such event in DB]");
                }
            } else {
                eventResult = new EventResult(false, "Error:[Unauthorized]");
            }

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            eventResult = new EventResult(false, "Error:[" + ex.toString() + "]");
        }

        return eventResult;
    }
}
