package JSON;

import java.util.Arrays;

public class Locations {
    private Location[] data;

    @Override
    public String toString() {
        return "Locations{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    public Locations(Location[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locations)) return false;
        Locations locations = (Locations) o;
        return Arrays.equals(data, locations.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public Location[] getData() {
        return data;
    }

    public void setData(Location[] data) {
        this.data = data;
    }
}
