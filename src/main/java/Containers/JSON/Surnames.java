package Containers.JSON;

import java.util.Arrays;

public class Surnames {
    private String[] data;

    @Override
    public String toString() {
        return "Surnames{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Surnames)) return false;
        Surnames surnames = (Surnames) o;
        return Arrays.equals(data, surnames.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
