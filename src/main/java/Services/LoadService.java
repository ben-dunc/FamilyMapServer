package Services;

import Models.Event;
import Models.Person;
import Models.User;
import Request.LoadRequest;
import Result.LoadResult;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;


/**
 * Does work for the load service
 */
public class LoadService {
    /**
     * Removes old data and loads the new data in
     * @param loadRequest the request object for this call
     * @return the load result object for this call
     */
    public static LoadResult load(LoadRequest loadRequest) {
        LoadResult loadResult;
        Connection connection = null;

        try(Connection c = ServiceHelper.getConnection()) {
            connection = c;
            connection.setAutoCommit(false);

            ClearService.clearAllTables(connection);

            if (loadRequest.getUsers() != null) {
                for (User user: loadRequest.getUsers()) {
                    if (user.getPersonID() == null || user.getPersonID().equals("")) {
                        String uuid = UUID.randomUUID().toString().substring(0, 8);
                        user.setPersonID(uuid);
                    }

                    UserDao.newUser(connection, user);
                    LoginService.loginUser(connection, user.getUsername());
                }
            } else {
                loadRequest.setUsers(new User[]{});
            }
            if (loadRequest.getPersons() != null) {
                for (Person person : loadRequest.getPersons()) {
                    if (person.getPersonID() == null || person.getPersonID().equals("")) {
                        String uuid = UUID.randomUUID().toString().substring(0, 8);
                        person.setPersonID(uuid);
                    }
                    PersonDao.newPerson(connection, person);
                }
            } else {
                loadRequest.setPersons(new Person[]{});
            }
            if (loadRequest.getEvents() != null) {
                for (Event event: loadRequest.getEvents()) {
                    if (event.getEventID() == null || event.getEventID().equals("")) {
                        String uuid = UUID.randomUUID().toString().substring(0, 8);
                        event.setEventID(uuid);
                    }
                    EventDao.newEvent(connection, event);
                }
            } else {
                loadRequest.setEvents(new Event[]{});
            }

            loadResult = new LoadResult("Successfully added " + loadRequest.getUsers().length + " users, " + loadRequest.getPersons().length + " persons, and " + loadRequest.getEvents().length + " events to the database.", true);

            connection.commit();
        } catch (SQLException ex) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException ignored) {}

            System.out.println(ex.toString());
            loadResult = new LoadResult("Error: [" + ex.toString() + "]", false);
        }

        return loadResult;
    }
}
