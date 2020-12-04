import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParsing {

    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";

    public static Metro parseHTML() {
        Metro metro = new Metro();
        try {
            Document doc = Jsoup.connect(SITE_URL).maxBodySize(0).get();
            HTMLParsing.fillTheLinesInfo(doc, metro);
            HTMLParsing.fillTheStationsInfo(doc, metro);
            HTMLParsing.fillConnections(doc, metro);
            System.out.println("HTML parsing complete");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return metro;
    }

    public static void fillTheLinesInfo(Document doc, Metro metro) {
        Elements linesInfo = doc.select("span[class*=\"js-metro-line\"]");
        for (Element lineInfo : linesInfo) {
            Line curLine = new Line(lineInfo.text(), lineInfo.attr("data-line"));
            metro.addLine(curLine);
        }
    }

    public static void fillTheStationsInfo(Document doc, Metro metro) {
        Elements linesStationsInfo = doc.select("div[class*=\"js-metro-stations\"]");

        for (Element lineStationsInfo : linesStationsInfo) {
            String lineNumber = lineStationsInfo.attr("data-line");
            Line curLine = findLineByNumber(lineNumber, metro);
            Elements stationsInfo = lineStationsInfo.select("span.name");
            stationsInfo.forEach(stationInfo -> {
                curLine.addStation(new Station(stationInfo.text(), curLine));
            });
        }
    }

    public static void fillConnections(Document doc, Metro metro) {
        Elements linesStationsInfo = doc.select("div[class*=\"js-metro-stations\"]");

        for (Element lineStationsInfo : linesStationsInfo) {
            String lineNumber = lineStationsInfo.attr("data-line");
            Elements lineStationInfo = lineStationsInfo.select("a[data-metrost]");
            for (Element info : lineStationInfo) {
                String stationName = info.select("span[class=name]").text();
                Station curStation = metro.getLine(lineNumber).getStation(stationName);
                fillStationConnections(info, curStation, metro);
            }
        }
    }

    private static void fillStationConnections(Element lineStationInfo, Station station, Metro metro) {
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

    private static Line findLineByNumber(String lNumber, Metro metro) {
        List<Line> lines = metro.getLines();
        for (Line line : lines) {
            if (line.getNumber().equals(lNumber)) {
                return line;
            }
        }
        return null;
    }
}
