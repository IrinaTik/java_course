import core.Line;
import core.Station;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Использующаяся схема:

Оловянная -> Деревянная -> Пластиковая -> Шерстяная -> Платиновая                       ЛИНИЯ 1
                                |
             Брезентовая -> Стеклянная -> Железная -> Алюминиевая                       ЛИНИЯ 2
                                                           |
                                                         Медная -> Стальная             ЛИНИЯ 3
 */


public class RouteCalculatorTest {

    private static StationIndex stationIndex;
    static RouteCalculator rc;

    static List<Station> routeOneLine;
    static List<Station> routeTwoLines;
    static List<Station> routeThreeLines;
    static List<Station> routeReverseDirection;
    static List<Station> stations;

    static Line line1;
    static Line line2;
    static Line line3;

    @BeforeAll
    public static void setUp() {
        line1 = new Line(1, "Белая");
        line2 = new Line(2, "Черная");
        line3 = new Line(3, "Красная");

        Station[] arrStations = {new Station("Оловянная", line1), new Station("Деревянная", line1),
                new Station("Пластиковая", line1), new Station("Шерстяная", line1),
                new Station("Платиновая", line1), new Station("Брезентовая", line2),
                new Station("Стеклянная", line2), new Station("Железная", line2),
                new Station("Алюминиевая", line2), new Station("Медная", line3),
                new Station("Стальная", line3)};
        stations = Arrays.asList(arrStations);

        fillLine(line1);
        fillLine(line2);
        fillLine(line3);
        createStationIndex();
        fillRoutes();

        rc = new RouteCalculator(stationIndex);
    }

    @Test
    @DisplayName("Route calculation for 1 line")
    public void testDurationCalculationOneLine() {
        double actualOneLine = RouteCalculator.calculateDuration(routeOneLine);
        double expectedOneLine = 5.0;
        assertEquals(expectedOneLine, actualOneLine);
    }

    @Test
    @DisplayName("Route calculation for 2 lines")
    public void testDurationCalculationTwoLines() {
        double actualTwoLines = RouteCalculator.calculateDuration(routeTwoLines);
        double expectedTwoLines = 13.5;
        assertEquals(expectedTwoLines, actualTwoLines);
    }

    @Test
    @DisplayName("Route calculation for 3 lines")
    public void testDurationCalculationThreeLines() {
        double actualThreeLines = RouteCalculator.calculateDuration(routeThreeLines);
        double expectedThreeLines = 19.5;
        assertEquals(expectedThreeLines, actualThreeLines);
    }

    @Test
    @DisplayName("Searching for shortest route - 1 line")
    public void testGetShortestRoute() {
        validateRoute(rc, stations.indexOf(getStationByName("Оловянная")),
                stations.indexOf(getStationByName("Пластиковая")), routeOneLine);
    }

    @Test
    @DisplayName("Searching for shortest route - 2 lines")
    public void testGetShortestRouteTwoLines() {
        validateRoute(rc, stations.indexOf(getStationByName("Оловянная")),
                stations.indexOf(getStationByName("Алюминиевая")), routeTwoLines);
    }

    @Test
    @DisplayName("Searching for shortest route - 3 lines")
    public void testGetShortestRouteThreeLines() {
        validateRoute(rc, stations.indexOf(getStationByName("Оловянная")),
                stations.indexOf(getStationByName("Стальная")), routeThreeLines);
    }

    @Test
    @DisplayName("Searching for shortest route - going in reverse direction")
    public void testGetShortestRouteReverse() {
        validateRoute(rc, stations.indexOf(getStationByName("Платиновая")),
                stations.indexOf(getStationByName("Брезентовая")), routeReverseDirection);
    }

    private void validateRoute(RouteCalculator rc, int fromStationIndex, int toStationIndex, List<Station> expectedRoute) {
        Station from = stations.get(fromStationIndex);
        Station to = stations.get(toStationIndex);
        List<Station> actual = rc.getShortestRoute(from, to);
        assertEquals(expectedRoute, actual);
    }

    private static void createStationIndex() {
        stationIndex = new StationIndex();
        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);
        stations.forEach(station -> stationIndex.addStation(station));
        fillConnections();
    }

    private static void fillConnections() {
        List<Station> connections1 = new ArrayList<>();
        List<Station> connections2 = new ArrayList<>();
        connections1.add(getStationByName("Пластиковая"));
        connections1.add(getStationByName("Стеклянная"));
        connections2.add(getStationByName("Алюминиевая"));
        connections2.add(getStationByName("Медная"));
        stationIndex.addConnection(connections1);
        stationIndex.addConnection(connections2);
    }

    private static Station getStationByName(String name) {
        for (Station station : stations) {
            if (station.getName().equals(name))
                return station;
        }
        return null;
    }

    private static void fillLine(Line line) {
        stations.stream().filter(station -> station.getLine().equals(line)).forEach(line::addStation);
    }

    private static void fillRoutes() {
        routeOneLine = new ArrayList<>();
        stations.stream().limit(3).forEach(station -> routeOneLine.add(station));

        routeTwoLines = new ArrayList<>();
        routeTwoLines.add(getStationByName("Оловянная"));
        routeTwoLines.add(getStationByName("Деревянная"));
        routeTwoLines.add(getStationByName("Пластиковая"));
        routeTwoLines.add(getStationByName("Стеклянная"));
        routeTwoLines.add(getStationByName("Железная"));
        routeTwoLines.add(getStationByName("Алюминиевая"));


        routeThreeLines = new ArrayList<>();
        routeThreeLines.add(getStationByName("Оловянная"));
        routeThreeLines.add(getStationByName("Деревянная"));
        routeThreeLines.add(getStationByName("Пластиковая"));
        routeThreeLines.add(getStationByName("Стеклянная"));
        routeThreeLines.add(getStationByName("Железная"));
        routeThreeLines.add(getStationByName("Алюминиевая"));
        routeThreeLines.add(getStationByName("Медная"));
        routeThreeLines.add(getStationByName("Стальная"));


        routeReverseDirection = new ArrayList<>();
        routeReverseDirection.add(getStationByName("Платиновая"));
        routeReverseDirection.add(getStationByName("Шерстяная"));
        routeReverseDirection.add(getStationByName("Пластиковая"));
        routeReverseDirection.add(getStationByName("Стеклянная"));
        routeReverseDirection.add(getStationByName("Брезентовая"));
    }

}
