import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonHelper {

    private static final String JSONFILE_PATH = "./09_FilesAndNetwork/MoscowMetro/src/main/resources/MoscowMetroMap.json";

    public static void writeToJSON(Metro metro){
        JSONObject jMetro = new JSONObject();
        jMetro.put("lines", JsonHelper.linesToJSON(metro));
        jMetro.put("stations", JsonHelper.stationsToJSON(metro));
        jMetro.put("connections", JsonHelper.connectionsToJSON(metro));
        try {
            FileWriter file = new FileWriter(JSONFILE_PATH);
            file.write(jMetro.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static JSONArray linesToJSON(Metro metro) {
        JSONArray jLines = new JSONArray();
        for (Line line : metro.getLines()) {
            JSONObject jLine = new JSONObject();
            jLine.put("name", line.getName());
            jLine.put("number", line.getNumber());
            jLines.add(jLine);
        }
        return jLines;
    }

    public static JSONObject stationsToJSON(Metro metro) {
        JSONObject jStations = new JSONObject();
        for (Line line : metro.getLines()) {
            JSONArray jLineStations = new JSONArray();
            line.getStations().forEach(station -> jLineStations.add(station.getName()));
            jStations.put(line.getNumber(), jLineStations);
        }
        return jStations;
    }

    public static JSONArray connectionsToJSON(Metro metro) {
        JSONArray jConnections = new JSONArray();
        Set<Station> allStations = new HashSet<>();
        Set<Station> connectedStations = new HashSet<>();
        metro.getLines().stream().map(Line::getStations).forEach(stations -> stations.stream()
                .filter(station -> !station.getConnections().isEmpty()).forEach(allStations::add));
        for (Station station : allStations) {
            if (connectedStations.contains(station)) {
                continue;
            }
            JSONArray jStAr = new JSONArray();
            jStAr.add(createJSONObj(station));
            station.getConnections().forEach(connection -> {
                connectedStations.add(connection);
                jStAr.add(createJSONObj(connection));
            });
            jConnections.add(jStAr);
        }
        return jConnections;
    }

    private static JSONObject createJSONObj(Station station) {
        JSONObject jStation = new JSONObject();
        jStation.put("line", station.getLine().getNumber());
        jStation.put("station", station.getName());
        return jStation;
    }

    public static Metro parseJSON() {
        Metro metro = new Metro();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jData = (JSONObject) parser.parse(getJSONData());
            JSONArray jLines = (JSONArray) jData.get("lines");
            JSONObject jStations = (JSONObject) jData.get("stations");
            JSONArray jConnections = (JSONArray) jData.get("connections");
            parseLines(jLines, metro);
            parseStations(jStations, metro);
            parseConnections(jConnections, metro);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return metro;
    }

    private static void parseConnections(JSONArray jConnections, Metro metro) {
        for (Object o : jConnections) {
            JSONArray jConNode = (JSONArray) o;
            List<Station> consNode = createConnectionNode(jConNode, metro);
            for (int i = 0; i < consNode.size() - 1; i++) {
                Station curStation = consNode.get(i);
                for (int j = i + 1; j < consNode.size(); j++) {
                    curStation.addConnection(consNode.get(j));
                }
            }
        }
    }

    private static List<Station> createConnectionNode(JSONArray jConNode, Metro metro) {
        // TODO: exception if not found
        List<Station> connectionsList = new ArrayList<>();
        jConNode.forEach(jNode -> {
            JSONObject jConStation = (JSONObject) jNode;
            String lineNumber = (String) jConStation.get("line");
            String stationName = (String) jConStation.get("station");
            Station curStation = metro.getLine(lineNumber).getStation(stationName);
            connectionsList.add(curStation);
        });
        return connectionsList;
    }

    private static void parseStations(JSONObject jStations, Metro metro) {
        // TODO: exception if not found
        for (Object key : jStations.keySet()) {
            String lineNumber = (String) key;
            Line line = metro.getLine(lineNumber);
            JSONArray jLineStations = (JSONArray) jStations.get(lineNumber);
            for (Object o : jLineStations) {
                Station station = new Station((String) o, line);
                line.addStation(station);
            }
        }
    }

    private static void parseLines(JSONArray jLines, Metro metro) {
        for (Object o : jLines) {
            JSONObject jLine = (JSONObject) o;
            Line line = new Line((String) jLine.get("name"), (String) jLine.get("number"));
            metro.addLine(line);
        }
    }

    private static String getJSONData() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(JSONFILE_PATH));
            lines.forEach(builder::append);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

}
