package Containers.Models;

import java.util.Objects;

/**
 * A user
 */
public class User {
    /**
     *  Unique username for user
     */
    private String username;

    /**
     *  User's password
     */
    private String password;

    /**
     *  User's email address
     */
    private String email;

    /**
     *  User's first name
     */
    private String firstName;

    /**
     *  User's last name
     */
    private String lastName;

    /**
     *  User's gender
     */
    private String gender;

    /**
     *  Unique Person ID assigned to this user's generated Person
     */
    private String personID;

    /**
     * Takes all variables
     * @param username a unique username
     * @param password a password
     * @param email a unique email
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param gender the user's gender: 'm' or 'f'
     * @param personID the user's person id
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", personID='" + personID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(gender, user.gender) && Objects.equals(personID, user.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender, personID);
    }
}
