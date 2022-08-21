package JSON;

import java.util.Arrays;

public class FemaleNames {
    private String[] data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FemaleNames)) return false;
        FemaleNames that = (FemaleNames) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public String toString() {
        return "FemaleNames{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public FemaleNames(String[] data) {
        this.data = data;
    }
}
