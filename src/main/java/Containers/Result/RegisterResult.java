package Containers.Result;

import java.util.Objects;

/**
 *  A class for register API call result
 */
public class RegisterResult {
    /**
     * A unique token
     */
    private String authtoken;
    /**
     * A unique username
     */
    private String username;
    /**
     * A unique person id
     */
    private String personID;
    /**
     * True if call successful
     */
    private boolean success;
    /**
     * status of call
     */
    private String message;

    /**
     * A constructor for the resgister api result calls. Takes all fields.
     * @param authtoken a unique token
     * @param username a unique username
     * @param personID a unique person id
     * @param success if the call was successful
     * @param message status of failure. Empty if successful
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    @Override
    public String toString() {
        return "RegisterResult{" +
                "authtoken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                ", personID='" + personID + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegisterResult)) return false;
        RegisterResult that = (RegisterResult) o;
        return success == that.success && Objects.equals(authtoken, that.authtoken) && Objects.equals(username, that.username) && Objects.equals(personID, that.personID) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username, personID, success, message);
    }
}
