import DataModel.Metro;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeToJSON(Metro metro, String jsonfilePath){
        Map<String, Object> metroMap = new HashMap<>();
        metroMap.put("lines", metro.getLines());
        metroMap.put("stations", metro.getStationsByLines());
        metroMap.put("connections", metro.getConnections());
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(jsonfilePath), metroMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Metro parseJSON(String jsonfilePath) {
        Metro metroMap = new Metro();
        try {
            metroMap = mapper.readValue(new File(jsonfilePath), Metro.class);
            System.out.println("Reading from JSON file " + Paths.get(jsonfilePath).toAbsolutePath() + " is complete");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return metroMap;
    }

}
