import DataModel.Connection;
import DataModel.Line;
import DataModel.Metro;
import DataModel.Station;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParsing {

    public static Metro parseHTML(String siteUrl ) {
        Metro metro = new Metro();
        try {
            Document doc = Jsoup.connect(siteUrl).maxBodySize(0).get();
            fillInfo(doc, metro);
            System.out.println("HTML parsing complete");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return metro;
    }

    public static void fillInfo(Document doc, Metro metro) {
        Elements lines = doc.select("span.js-metro-line");
        Elements stationsByLines = doc.select("div.js-metro-stations");

        for (int i = 0; i < lines.size(); i++) {
            Element line = lines.get(i);
            Elements lineStations = stationsByLines.get(i).select("p");
            String lineNumber = line.attr("data-line");
            metro.addLine(new Line(line.text(), lineNumber));

            for (int j = 0; j < lineStations.size(); j++) {
                Element elStation = lineStations.get(j);
                String stationName = elStation.select("span[class=name]").text();
                Station station = new Station(stationName, lineNumber);
                metro.addStation(station);
                Elements links = elStation.select("span[title*=переход на]");
                if (!links.isEmpty()) {
                    Connection connection = new Connection(station);
                    for (Element link : links) {
                        String linkLineNumber = link.attr("class").replaceAll(".+ln-", "");
                        String linkStationName = link.attr("title").replaceAll(".+«(.+)».+", "$1");
                        Station conStation = new Station(linkStationName, linkLineNumber);
                        connection.addConnection(conStation);
                    }
                    metro.addConnection(connection);
                }
            }
        }
    }
}
