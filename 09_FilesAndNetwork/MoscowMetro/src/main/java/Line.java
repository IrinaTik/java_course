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

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Station getStation(String name) {
        return this.stations.stream().filter(station -> station.getName().equals(name)).findAny().orElse(null);
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStations(List<Station> stations) {
        stations.addAll(stations);
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Линия: " + name + "\nНомер: " + number + "\nСтанции: " + stations.size());
//        for (Station station : stations) {
//            builder.append("\t" + station.toString() + "\n");
//        }
        return builder.toString();
    }
}
