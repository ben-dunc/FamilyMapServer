package Containers.Result;

import java.util.Objects;

/**
 *  A class for fill requests
 */
public class FillResult {
    /**
     * status message if call failed
     */
    private String message;
    /**
     * true if successful, false otherwise
     */
    private boolean success;

    /**
     * Takes all variables
     * @param message status message if call failed
     * @param success true if successful, false otherwise
     */
    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FillResult{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FillResult)) return false;
        FillResult that = (FillResult) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}
