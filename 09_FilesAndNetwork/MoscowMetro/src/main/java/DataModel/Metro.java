package DataModel;

import java.util.*;
import java.util.stream.Collectors;

public class Metro {

    private Set<Line> lines;
    private List<Station> stations;
    private List<Connection> connections;

    public Metro() {
        this.lines = new TreeSet<>();
        this.stations = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public void setStations(Map<String, List<String>> stations) {
        stations.forEach((key, value) -> value.forEach(val -> this.stations.add(new Station(val, key))));
    }

    public Line getLine(String number) {
        return this.lines.stream().filter(line -> line.getNumber().equals(number)).findAny().orElse(null);
    }

    public Set<Line> getLines() {
        return lines;
    }

    public List<Station> getStations() {
        return stations;
    }

    public Map<String, List<String>> getStationsByLines() {
        Map<String, List<String>> map = new TreeMap<>(Comparator.comparing(String::length).thenComparing(String::compareTo));
        lines.forEach(line -> map.put(line.getNumber(), stations.stream()
                .filter(station -> station.getLineNumber().equals(line.getNumber()))
                .map(Station::getName)
                .collect(Collectors.toList())));
        return map;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void addStation(Station station) {
        this.stations.add(station);
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public void output() {
        System.out.println("Линии:");
        lines.forEach(line -> System.out.println("\t" + line.toString()));
        System.out.println("Станции:");
        stations.forEach(station -> System.out.println("\t" + station.toString()));
        System.out.println("Переходы:");
        connections.forEach(connection -> System.out.println(connection.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metro metro = (Metro) o;
        return lines.stream().allMatch(line -> metro.getLines().stream().anyMatch(otherLine -> otherLine.equals(line))) &&
                stations.stream().allMatch((station -> metro.getStations().stream().anyMatch(otherStation -> otherStation.equals(station)))) &&
                connections.stream().allMatch((connection -> metro.getConnections().stream().anyMatch(otherConnection -> otherConnection.equals(connection))));
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }
}
