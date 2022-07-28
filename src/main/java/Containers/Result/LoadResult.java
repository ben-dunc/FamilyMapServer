package Containers.Result;

import java.util.Objects;

/**
 * Holds the result from the load endpoint
 */
public class LoadResult {
    /**
     * the status message of failure
     */
    private String message;
    /**
     * true if call was successful, false otherwise
     */
    private boolean success;

    /**
     * Takes all variables
     * @param message the status message if failed
     * @param success true if call was successful, false otherwise
     */
    public LoadResult(String message, boolean success) {
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
        return "LoadResult{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoadResult)) return false;
        LoadResult that = (LoadResult) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, success);
    }
}
