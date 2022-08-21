package Models;

import java.util.Objects;

/**
 *  An authtoken
 */
public class Authtoken {
    /**
     *  A unique token for quthentication
     */
    private String authtoken;
    /**
     * A unique username
     */
    private String username;

    /**
     * Takes all variables
     * @param authtoken a unique auth token
     * @param username a unique username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Authtoken{" +
                "authtoken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authtoken)) return false;
        Authtoken authtoken1 = (Authtoken) o;
        return Objects.equals(authtoken, authtoken1.authtoken) && Objects.equals(username, authtoken1.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username);
    }
}
