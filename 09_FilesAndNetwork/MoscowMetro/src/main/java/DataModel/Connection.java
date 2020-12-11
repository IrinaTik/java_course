package DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Connection {

    @JsonProperty("station")
    private Station mainStation;
    @JsonProperty("connected stations")
    private List<Station> conStations;

    public Connection() {}

    public Connection(Station mainStation) {
        this.mainStation = mainStation;
        this.conStations = new ArrayList<>();
    }

    public Connection(Station mainStation, List<Station> conStations) {
        this.mainStation = mainStation;
        this.conStations = conStations;
    }

    public Station getMainStation() {
        return mainStation;
    }

    public void setMainStation(Station mainStation) {
        this.mainStation = mainStation;
    }

    public List<Station> getConStations() {
        return conStations;
    }

    public void setConStations(List<Station> conStations) {
        this.conStations = conStations;
    }

    public void addConnection(Station conStation) {
        this.conStations.add(conStation);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(mainStation.toString() + ": ");
        for (Station conStation : conStations) {
            builder.append("\n\t" + conStation.toString());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(mainStation, that.mainStation) &&
                Objects.equals(conStations, that.conStations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainStation, conStations);
    }
}
