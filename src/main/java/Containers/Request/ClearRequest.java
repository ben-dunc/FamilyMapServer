package Containers.Request;

import java.util.Objects;

/**
 *  A class to represent the request body of the Clear API call
 */
public class ClearRequest {
    /**
     * status message if call fails
     */
    private String message;
    /**
     * true if success, false otherwise
     */
    private boolean success;

    /**
     * Takes all variables
     * @param message status message if call fails
     * @param success true if success, false otherwise
     */
    public ClearRequest(String message, boolean success) {
        this.message = message;
        this.success = success;
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
        return "ClearRequest{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClearRequest)) return false;
        ClearRequest that = (ClearRequest) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}

