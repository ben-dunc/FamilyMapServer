package JSON;

import java.util.Arrays;

public class MaleNames {
    private String[] data;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public MaleNames(String[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaleNames)) return false;
        MaleNames maleNames = (MaleNames) o;
        return Arrays.equals(data, maleNames.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "MaleNames{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
