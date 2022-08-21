package Result;

import java.util.Objects;

/**
 *  A class for clear results
 */
public class ClearResult {
    /**
     * status message if call failed
     */
    private String message;
    /**
     * true if call succeeded, false otherwise
     */
    private boolean success;

    /**
     * Takes all variables
     * @param message status message if call failed
     * @param success true if call succeeded, false otherwise
     */
    public ClearResult(String message, boolean success) {
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
        return "ClearResult{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClearResult)) return false;
        ClearResult that = (ClearResult) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}

