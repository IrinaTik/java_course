import java.util.ArrayList;
import java.util.List;

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

    public void setName(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public List<Station> getConnections() {
        return connections;
    }

    public void addConnection(Station connection) {
        if (!this.connections.contains(connection)) {
            this.connections.add(connection);
        }
        if (!connection.getConnections().contains(this)) {
            connection.addConnection(this);
        }
    }

    public String consToString() {
        StringBuilder builder = new StringBuilder();
        for (Station conStation : this.connections) {
            builder.append("\t\t" + conStation.getName() + "(" + conStation.getLine().getNumber() + ")\n");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return this.name + "(" + this.line.getNumber() + ")" + (this.connections.isEmpty() ? "" : "\n" + consToString());
    }
}
