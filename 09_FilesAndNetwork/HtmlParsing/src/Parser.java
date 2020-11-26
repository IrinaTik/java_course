import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    private static final String url = "https://lenta.ru/";

    public static void main(String[] args) {

        try {
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("img[src]");
            links.stream().map(l -> l.attr("abs:src")).forEach(System.out::println);
            System.out.println(links.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

}
