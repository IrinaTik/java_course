package DataModel;

import java.util.ArrayList;
import java.util.List;

public class Line {

    private String name;
    private String number;
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
    public String toString() {
        return name + "\nНомер: " + number + "\nСтанции: " + stations.size();
    }
}
