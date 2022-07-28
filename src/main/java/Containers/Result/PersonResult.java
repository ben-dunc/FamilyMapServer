package Containers.Result;

import Containers.Models.Person;

import java.util.Arrays;
import java.util.Objects;

/**
 * A container for the person api result
 */
public class PersonResult {
    /**
     * a unique username
     */
    private String associatedUsername;
    /**
     * A unique personID
     */
    private String personID;
    /**
     * Their first name
     */
    private String firstName;
    /**
     * Their last name
     */
    private String lastName;
    /**
     * Their gender
     */
    private String gender;
    /**
     * their father's person id - can be null
     */
    private String fatherID;
    /**
     * their mother's person id - can be null
     */
    private String motherID;
    /**
     * Their spouse's person id - can be null
     */
    private String spouseID;
    /**
     * true if successful, false otherwise
     */
    private boolean success;
    /**
     * status message if call failed - can be null
     */
    private String message;
    /**
     * Data for one of the endpoints - can be null
     */
    private Person[] data;

    public PersonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public PersonResult(boolean success, Person[] data) {
        this.success = success;
        this.data = data;
    }

    public PersonResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PersonResult{" +
                "associatedUsername='" + associatedUsername + '\'' +
                ", personID='" + personID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonResult)) return false;
        PersonResult that = (PersonResult) o;
        return success == that.success && Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(personID, that.personID) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID) && Objects.equals(message, that.message) && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(associatedUsername, personID, firstName, lastName, gender, fatherID, motherID, spouseID, success, message);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
