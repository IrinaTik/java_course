import core.Line;
import core.Station;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteCalculatorTest extends TestCase {

    private static StationIndex stationIndex;

    List<Station> routeOneLine;
    List<Station> routeTwoLines;
    List<Station> routeThreeLines;
    List<Station> stations;

    Line line1;
    Line line2;
    Line line3;

    @Override
    protected void setUp() throws Exception {
        line1 = new Line(1, "Белая");
        line2 = new Line(2, "Черная");
        line3 = new Line(3, "Красная");

        Station[] arrStations = {new Station("Оловянная", line1), new Station("Деревянная", line1), new Station("Пластиковая", line1),
                new Station("Стеклянная", line2), new Station("Железная", line2), new Station("Алюминиевая", line2),
                new Station("Медная", line3), new Station("Стальная", line3)};
        stations = Arrays.asList(arrStations);

        fillLine(line1);
        fillLine(line2);
        fillLine(line3);
        createStationIndex();
        fillRoutes();
    }

    @Test
    public void testDurationCalculation() {
        double actualOneLine = RouteCalculator.calculateDuration(routeOneLine);
        double expectedOneLine = 5.0;
        assertEquals(expectedOneLine, actualOneLine);

        double actualTwoLines = RouteCalculator.calculateDuration(routeTwoLines);
        double expectedTwoLines = 13.5;
        assertEquals(expectedTwoLines, actualTwoLines);

        double actualThreeLines = RouteCalculator.calculateDuration(routeThreeLines);
        double expectedThreeLines = 19.5;
        assertEquals(expectedThreeLines, actualThreeLines);
    }

    @Test
    public void testGetShortestRoute() {
        RouteCalculator rc = new RouteCalculator(stationIndex);

        validateRoute(rc, 0, 2, routeOneLine);
        validateRoute(rc, 0, 5, routeTwoLines);
        validateRoute(rc, 0, 7, routeThreeLines);
    }

    private void validateRoute(RouteCalculator rc, int fromStationIndex, int toStationIndex, List<Station> expectedRoute) {
        Station from = stations.get(fromStationIndex);
        Station to = stations.get(toStationIndex);
        List<Station> actual = rc.getShortestRoute(from, to);
        assertEquals(expectedRoute, actual);
    }

    private void createStationIndex() {
        stationIndex = new StationIndex();
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stations.forEach(station -> stationIndex.addStation(station));
        fillConnections();
    }

    private void fillConnections() {
        List<Station> connections1 = new ArrayList<>();
        List<Station> connections2 = new ArrayList<>();
        connections1.add(stations.get(2));
        connections1.add(stations.get(3));
        connections2.add(stations.get(5));
        connections2.add(stations.get(6));
        stationIndex.addConnection(connections1);
        stationIndex.addConnection(connections2);
    }

    private void fillLine(Line line) {
        stations.stream().filter(station -> station.getLine().equals(line)).forEach(line::addStation);
    }

    private void fillRoutes() {
        routeOneLine = new ArrayList<>();
        stations.stream().filter(station -> station.getLine().equals(line1))
                .forEach(station -> routeOneLine.add(station));

        routeTwoLines = new ArrayList<>();
        stations.stream().filter(station -> !station.getLine().equals(line3))
                .forEach(station -> routeTwoLines.add(station));


        routeThreeLines = new ArrayList<>();
        routeThreeLines.addAll(stations);
    }

}
