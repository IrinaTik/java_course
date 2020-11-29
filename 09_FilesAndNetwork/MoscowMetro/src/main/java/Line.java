import java.util.ArrayList;
import java.util.List;

public class Line {

    private String name;
    private String number;
    private List<String> stations;

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

    public List<String> getStations() {
        return stations;
    }

    public void addStation(String stationName) {
        stations.add(stationName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Линия: " + name + "\nНомер: " + number + "\nСтанции:\n");
        for (String station : stations) {
            builder.append("\t" + station + "\n");
        }
        return builder.toString();
    }
}
