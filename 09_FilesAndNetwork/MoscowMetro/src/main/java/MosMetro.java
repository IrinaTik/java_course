import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

public class MosMetro {

    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";
    private static final String JSONFILE_PATH = "./09_FilesAndNetwork/MoscowMetro/src/main/resources/MoscowMetroMap.json";

    public static void main(String[] args) {
        parseHTML();


    }

    private static void parseHTML() {
        try {
            Document doc = Jsoup.connect(SITE_URL).maxBodySize(0).get();
            Elements metroInfo = doc.select("div[id=\"metrodata\"]");
            Elements linesInfo = doc.select("div[class*=\"js-toggle-depend\"]");
            Elements stationsInfo  = doc.select("span[class=\"name\"]");
            Elements stationsInfoLine1 = doc.select("div[data-line=1]").select("span[class=name]");

            JSONObject metro = new JSONObject();
            JSONArray linesArray = new JSONArray();
            linesInfo.forEach(line -> {
                System.out.println(line.text());
                System.out.println(line.select("span[data-line]").attr("data-line"));
                JSONObject jLine = new JSONObject();
                jLine.put("name", line.text());
                jLine.put("number", line.select("span[data-line]").attr("data-line"));
                linesArray.add(jLine);
            });
            metro.put("lines", linesArray);

            System.out.println("=====================");
            Line line1 = new Line(linesInfo.get(0).text(),
                    linesInfo.get(0).select("span[data-line]").attr("data-line"));
            stationsInfoLine1.forEach(element -> {
                System.out.println(element.text());
                line1.addStation(element.text());
            });
            System.out.println("=====================");
            System.out.println(line1.toString());

            FileWriter file = new FileWriter(JSONFILE_PATH);
            file.write(metro.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void jsonWrite() {

    }

}
