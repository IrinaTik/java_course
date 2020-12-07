import DataModel.Connection;
import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    /*
    ============================ writing JSON ============================
    */

    public static void writeToJSON(Metro metro, String jsonfilePath){
        Map<String, Object> metroMap = new HashMap<>();
        metroMap.put("lines", metro.getLines());
        metroMap.put("stations", convertStationsToPrettyJSON(metro));
        metroMap.put("connected stations", convertConnectionsToPrettyJSON(metro));
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(jsonfilePath), metroMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Station> convertConnectionsToPrettyJSON(Metro metro) {
        List<Station> connections = new ArrayList<>();
        metro.getLines()
                .forEach(line -> line.getStations().stream()
                        .filter(station -> !station.getConnections().isEmpty())
                        .forEach(connections::add));
        return connections;

    }

    private static Map<String, List<String>> convertStationsToPrettyJSON(Metro metro) {
        Map<String, List<String>> stations = new TreeMap<>(getStationsComparator());
        metro.getLines().forEach(line -> stations.put(line.getNumber(),
                line.getStations().stream().map(Station::getName).collect(Collectors.toList())));
        return stations;
    }

    private static Comparator<String> getStationsComparator() {
        return (o1, o2) -> {
            if (o1.length() > o2.length()) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            } else {
                return o1.compareTo(o2);
            }
        };
    }

    /*
    ============================ reading JSON ============================
     */

    public static Metro parseJSON(String jsonfilePath) {
        Metro metro = new Metro();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jMetro = (JSONObject) parser.parse(getJSONData(jsonfilePath));
            Map<String, Object> metroMap = mapper.readValue(jMetro.toJSONString(), new TypeReference<Map<String, Object>>() {});
            parseLines(metroMap, metro);
            parseStations(metroMap, metro);
            parseConnections(metroMap, metro);
            System.out.println("Reading from JSON file " + Paths.get(jsonfilePath).toAbsolutePath() + " is complete");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return metro;
    }

    private static void parseConnections(Map<String, Object> metroMap, Metro metro) {
        List<Object> generalConList = (ArrayList) metroMap.get("connected stations");
        generalConList.forEach(item -> generateConnectionNode(item, metro));
    }

    private static void generateConnectionNode(Object item, Metro metro) {
        Map<String, Object> conNode = (Map) item;
        String stationName = (String) conNode.get("name");
        Line line = getLineFromConnection(conNode.get("line"), metro);
        Station station = line.getStation(stationName);
        if (station == null) {
            throw new NullPointerException("Station " + stationName + " on line " + line.getNumber() + " not found. Check the JSON file for incorrect info.");
        } else {
            getStationsConnections(conNode.get("connections"), station);
        }
    }

    private static void getStationsConnections(Object connections, Station station ) {
        List<Object> conStations = (ArrayList) connections;
        List<Connection> cons = new ArrayList<>();
        conStations.stream().map(con -> (Map) con).forEach(conInfo -> cons.add(new Connection( (String) conInfo.get("lineNumber"), (String) conInfo.get("name"))));
        station.addConnections(cons);
    }

    private static Line getLineFromConnection(Object info, Metro metro) {
        Map<String, String> lineInfo = (Map) info;
        String lineNumber = lineInfo.get("number");
        Line line = metro.getLine(lineNumber);
        if (line == null) {
            throw new NullPointerException("Line " + lineNumber + " not found. Check the JSON file for incorrect info.");
        } else {
            return line;
        }
    }

    private static void parseStations(Map<String, Object> metroMap, Metro metro) {
        Map<String, Object> stations = (Map) (metroMap.get("stations"));
        stations.entrySet().forEach(entry -> fillLineWithStations(entry, metro));
    }

    private static void fillLineWithStations(Map.Entry<String, Object> entry, Metro metro) {
        String lineNumber = entry.getKey();
        Line line = metro.getLine(lineNumber);
        if (line == null) {
            throw new IllegalArgumentException("The line number " + lineNumber + " not found. Check the JSON file for incorrect info.");
        } else {
            List<String> stations = (ArrayList) entry.getValue();
            stations.forEach(stationName -> line.addStation(new Station(stationName, line)));
        }
    }

    private static void parseLines(Map<String, Object> metroMap, Metro metro) {
        List<Map<String, String>> lines = (ArrayList) (metroMap.get("lines"));
        lines.forEach(line -> metro.addLine(new Line(line.get("name"), line.get("number"))));
    }

    private static String getJSONData(String jsonfilePath) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(jsonfilePath));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

}
