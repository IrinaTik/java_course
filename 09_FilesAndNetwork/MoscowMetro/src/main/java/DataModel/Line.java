package DataModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Line implements Comparable<Line> {

    private String name;
    private String number;
    @JsonIgnore
    private List<Station> stations;

    public Line(String name, String number) {
        this.name = name;
        this.number = number;
        this.stations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


    public String getNumber() {
        return number;
    }


    public Station getStation(String name) {
        return this.stations.stream().filter(station -> station.getName().equals(name)).findAny().orElse(null);
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStations(List<Station> stations) {
        this.stations.addAll(stations);
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(name, line.name) &&
                Objects.equals(number, line.number) &&
                Objects.equals(getStations().size(), line.getStations().size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public String toString() {
        return name + "\nНомер: " + number + "\nСтанции: " + stations.size();
    }

    @Override
    public int compareTo(Line o) {
        if (this.getNumber().length() > o.getNumber().length()) {
            return 1;
        } else if (this.getNumber().length() < o.getNumber().length()) {
            return -1;
        } else {
            return this.getNumber().compareTo(o.getNumber());
        }
    }
}
