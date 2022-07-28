package Containers.Request;

import Containers.Models.Event;
import Containers.Models.Person;
import Containers.Models.User;

import java.util.Arrays;

/**
 * A class to hold the load request
 */
public class LoadRequest {
    /**
     * the users to load in json format
     */
    private User[] users;
    /**
     * the persons to load in json format
     */
    private Person[] persons;
    /**
     * the events to load in json format
     */
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "LoadRequest{" +
                "users=" + Arrays.toString(users) +
                ", persons=" + Arrays.toString(persons) +
                ", events=" + Arrays.toString(events) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadRequest)) return false;
        LoadRequest that = (LoadRequest) o;
        return Arrays.equals(users, that.users) && Arrays.equals(persons, that.persons) && Arrays.equals(events, that.events);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(users);
        result = 31 * result + Arrays.hashCode(persons);
        result = 31 * result + Arrays.hashCode(events);
        return result;
    }
}
