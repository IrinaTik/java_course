import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MosMetro {

    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";

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
            linesInfo.forEach(line -> {
                System.out.println(line.text());
                System.out.println(line.select("span[data-line]").attr("data-line"));
            });
            System.out.println("=====================");
            Line line1 = new Line(linesInfo.get(0).text(),
                    linesInfo.get(0).select("span[data-line]").attr("data-line"));
            stationsInfoLine1.forEach(element -> {
                System.out.println(element.text());
                line1.addStation(element.text());
            });
            System.out.println("=====================");
            System.out.println(line1.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
