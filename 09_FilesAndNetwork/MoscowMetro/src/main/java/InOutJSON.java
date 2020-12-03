import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class InOutJSON {

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
        jStation.put(station.getLine().getNumber(), station.getName());
        return jStation;
    }

}
