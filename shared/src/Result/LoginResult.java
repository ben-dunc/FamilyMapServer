package Result;

import java.util.Objects;

/**
 *  A class for login results
 */
public class LoginResult {
    /**
     * a unique authtoken
     */
    private String authtoken;
    /**
     * a unique username
     */
    private String username;
    /**
     * a unique person id
     */
    private String personID;
    /**
     * details about failure
     */
    private String message;
    /**
     * true if successful, false otherwise
     */
    private boolean success;

    /**
     * Takes all variables
     * @param authtoken a unique token
     * @param username a unique username
     * @param personID a unique person id
     * @param message a unique person id
     * @param success true if success, false otherwise
     */
    public LoginResult(String authtoken, String username, String personID, String message, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.message = message;
        this.success = success;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "authtoken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                ", personID='" + personID + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginResult)) return false;
        LoginResult that = (LoginResult) o;
        return success == that.success && Objects.equals(authtoken, that.authtoken) && Objects.equals(username, that.username) && Objects.equals(personID, that.personID) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username, personID, message, success);
    }
}
