import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MosMetro {

    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";
    private static final String JSONFILE_PATH = "./09_FilesAndNetwork/MoscowMetro/src/main/resources/MoscowMetroMap.json";
    private static Metro metro;

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(SITE_URL).maxBodySize(0).get();
        metro = new Metro();
        fillTheLinesInfo(doc);
        fillTheStationsInfo(doc);
        fillConnections(doc);
        for (Line line : metro.getLines()) {
            System.out.println(line.toString());
            for (Station station : line.getStations()) {
                System.out.println("\t" + station.toString());
            }
        }
    }

    private static void parseHTML(Document doc) {
        try {
            doc = Jsoup.connect(SITE_URL).maxBodySize(0).get();
            Elements metroInfo = doc.select("div[id=\"metrodata\"]");
            Elements linesInfo = doc.select("div[class*=\"js-toggle-depend\"]");
            Elements stationsInfo  = doc.select("span[class=\"name\"]");
            Elements stationsInfoLine1 = doc.select("div[data-line=1]").select("span[class=name]");

            metroInfo.forEach(System.out::println);

            JSONObject metro = new JSONObject(); //общий объект

            JSONArray linesArray = new JSONArray(); //массив с линиями
            linesInfo.forEach(line -> {
                System.out.println(line.text());
                System.out.println(line.select("span[data-line]").attr("data-line"));
                JSONObject jLine = new JSONObject();
                jLine.put("name", line.text());
                jLine.put("number", line.select("span[data-line]").attr("data-line"));
                linesArray.add(jLine);
            });
            metro.put("lines", linesArray);

            //заполнение линии станциями
            System.out.println("=====================");
            JSONObject jAllStations = new JSONObject();
            for (int i = 0; i < linesArray.size(); i++) {
                JSONObject l = (JSONObject) linesArray.get(i);
                String name = (String) l.get("name");
                String number = (String) l.get("number");
                Elements stationsCurLine = doc.select("div[data-line=" + number + "]").select("span[class=name]");
                JSONArray jStations = new JSONArray();
                stationsCurLine.forEach(station -> jStations.add(station.text()));
                jAllStations.put(name, jStations);
            }
            metro.put("stations", jAllStations);


            //запись в JSON файл
            FileWriter file = new FileWriter(JSONFILE_PATH);
            file.write(metro.toJSONString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    private static void fillConnections(Document doc) {
//        Elements cons = doc.select("a[data-metrost]");
//
//        cons.forEach(System.out::println);
//        System.out.println("=================");
//        for (Element con : cons) {
//            Elements metroLink = con.select("span[title*=переход на]");
//            if (!metroLink.isEmpty()) {
//                metroLink.forEach(link -> {
//                    String lineName = con.select("span.name").text();
//                    String conStation = getConStationName(link.attr("title"));
//                    String conLine = getConLineNumber(link.attr("class"));
//                    System.out.println(lineName + " - " + conStation + " : " + conLine);
//                });
//            }
//        }
//    }

    private static String getConLineNumber(String number) {
        Pattern pattern = Pattern.compile("(t-icon-metroln ln-)(.+)");
        return getConInfo(number, pattern);
    }

    private static String getConStationName(String name) {
        Pattern pattern = Pattern.compile("(переход на станцию )«(.+)»(.+)");
        return getConInfo(name, pattern);
    }

    private static String getConInfo(String s, Pattern pattern) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(2).trim();
        }
        return "";
    }

    private static void fillTheLinesInfo(Document doc) {
        Elements linesInfo = doc.select("span[class*=\"js-metro-line\"]");
//        linesInfo.forEach(System.out::println);
        for (Element lineInfo : linesInfo) {
            Line curLine = new Line(lineInfo.text(), lineInfo.attr("data-line"));
            metro.addLine(curLine);
        }
//        metro.getLines().forEach(System.out::println);
    }

    private static void fillConnections(Document doc) {
        Elements linesStationsInfo = doc.select("div[class*=\"js-metro-stations\"]");

        for (Element lineStationsInfo : linesStationsInfo) {
            String lineNumber = lineStationsInfo.attr("data-line");
            Elements lineStationInfo = lineStationsInfo.select("a[data-metrost]");
            for (Element info : lineStationInfo) {
                String stationName = info.select("span[class=name]").text();
                Station curStation = metro.getLine(lineNumber).getStation(stationName);
                fillStationConnections(info, curStation);
            }
        }
    }

    private static void fillStationConnections(Element lineStationInfo, Station station) {

        Elements metroLink = lineStationInfo.select("span[title*=переход на]");
        if (!metroLink.isEmpty()) {
            for (Element link : metroLink) {
                String conStationName = getConStationName(link.attr("title"));
                String conLineNumber = getConLineNumber(link.attr("class"));
                Station conStation = metro.getLine(conLineNumber).getStation(conStationName);
                station.addConnection(conStation);
            }
        }
    }

    private static void fillTheStationsInfo(Document doc) {
        Elements linesStationsInfo = doc.select("div[class*=\"js-metro-stations\"]");
//        linesStationsInfo.forEach(System.out::println);

        // линии
        for (Element lineStationsInfo : linesStationsInfo) {
            String lineNumber = lineStationsInfo.attr("data-line");
            Line curLine = findLineByNumber(lineNumber);
            Elements stationsInfo = lineStationsInfo.select("span.name");
            stationsInfo.forEach(stationInfo -> {
                curLine.addStation(new Station(stationInfo.text(), curLine));
            });
//            System.out.println(lineNumber);
//            curLine.getStations().forEach(System.out::println);
//            System.out.println("-----------");
        }

    }

    private static Line findLineByNumber(String lNumber ) {
        List<Line> lines = metro.getLines();
        for (Line line : lines) {
            if (line.getNumber().equals(lNumber)) {
                return line;
            }
        }
        return null;
    }

    private static void jsonWrite() {

    }

}
