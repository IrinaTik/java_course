package DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Station {

    private String name;
    private Line line;
    private List<Station> connections;

    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
        this.connections = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    public List<Station> getConnections() {
        return connections;
    }

    public void addConnection(Station connection) {
        if (this.line != connection.getLine()) {
            if (!this.connections.contains(connection)) {
                this.connections.add(connection);
            }
            if (!connection.getConnections().contains(this)) {
                connection.addConnection(this);
            }
        }
    }

    public String consToString() {
        StringBuilder builder = new StringBuilder("\t\tПереход на: ");
        for (Station conStation : this.connections) {
            builder.append("\n\t\t\t" + conStation.getName() + "(" + conStation.getLine().getNumber() + ")");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(line, station.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, line);
    }

    @Override
    public String toString() {
        return this.name + "(" + this.line.getNumber() + ")" + (this.connections.isEmpty() ? "" : "\n" + consToString());
    }
}
