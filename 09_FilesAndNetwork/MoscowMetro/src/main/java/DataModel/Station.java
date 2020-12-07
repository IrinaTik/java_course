package DataModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Station {

    private String name;
    private Line line;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Connection> connections;

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

    public List<Connection> getConnections() {
        return connections;
    }

    public void addConnection(Connection connection) {
        if (!this.connections.contains(connection)) {
            this.connections.add(connection);
        }
    }

    public void addConnections(Collection<Connection> connections) {
        connections.forEach(this::addConnection);
    }

    public String consToString() {
        StringBuilder builder = new StringBuilder("\t\tПереход на: ");
        for (Connection conStation : this.connections) {
            builder.append("\n\t\t\t" + conStation.getName() + "(" + conStation.getLineNumber() + ")");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(line, station.line) &&
                Objects.equals(getConnections().size(), station.getConnections().size());
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
