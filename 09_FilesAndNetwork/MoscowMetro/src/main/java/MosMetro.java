import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;

public class MosMetro {

    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";
    private static final String JSONFILE_PATH = "./09_FilesAndNetwork/MoscowMetro/src/main/resources/MoscowMetroMap.json";
    private static Metro metro;

    public static void main(String[] args) {
        try {
            metro = new Metro();
            parseHTML();
            writeToJSON();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Line line : metro.getLines()) {
            System.out.println(line.toString());
            for (Station station : line.getStations()) {
                System.out.println("\t" + station.toString());
            }
        }
    }

    private static void parseHTML() throws IOException {
        Document doc = Jsoup.connect(SITE_URL).maxBodySize(0).get();
        HTMLParsing.fillTheLinesInfo(doc, metro);
        HTMLParsing.fillTheStationsInfo(doc, metro);
        HTMLParsing.fillConnections(doc, metro);
    }


    private static void writeToJSON() throws IOException {
        JSONObject jMetro = new JSONObject();
        jMetro.put("lines", InOutJSON.linesToJSON(metro));
        jMetro.put("stations", InOutJSON.stationsToJSON(metro));
        jMetro.put("connections", InOutJSON.connectionsToJSON(metro));
        FileWriter file = new FileWriter(JSONFILE_PATH);
        file.write(jMetro.toJSONString());
        file.flush();
        file.close();
    }

}
