package Containers.Result;

import Containers.Models.Event;

import java.util.Arrays;
import java.util.Objects;

/**
 * A container for the event api call results
 */
public class EventResult {
    /**
     * a unique username
     */
    private String associatedUsername;
    /**
     * a unique event id
     */
    private String eventID;
    /**
     * a unique person id
     */
    private String personID;
    /**
     * the latitude of this event
     */
    private float latitude;
    /**
     * the longitude of this event
     */
    private float longitude;
    /**
     * the country of this event
     */
    private String country;
    /**
     * the city of this event
     */
    private String city;
    /**
     * the event's type
     */
    private String eventType;
    /**
     * the event's year
     */
    private int year;
    /**
     * was the call successful?
     */
    private boolean success;
    /**
     * status message if call failed
     */
    private String message;
    /**
     * Data for one of the calls - may be null
     */
    private Event[] data;

    public EventResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
    }

    public EventResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public EventResult(boolean success, Event[] data) {
        this.success = success;
        this.data = data;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventResult{" +
                "associatedUsername='" + associatedUsername + '\'' +
                ", eventID='" + eventID + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventResult)) return false;
        EventResult that = (EventResult) o;
        return Float.compare(that.latitude, latitude) == 0 && Float.compare(that.longitude, longitude) == 0 && year == that.year && success == that.success && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(eventID, that.eventID) && Objects.equals(personID, that.personID) && Objects.equals(country, that.country) && Objects.equals(city, that.city) && Objects.equals(eventType, that.eventType) && Objects.equals(message, that.message) && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(associatedUsername, eventID, personID, latitude, longitude, country, city, eventType, year, success, message);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
