import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SiteNode {

    private static final String cssQuery = "a[href]";

    private String url;
    private List<SiteNode> childSites;

    public SiteNode(String url) {
        this.url = url;
        this.childSites = new ArrayList<>();
        parseLinks();
    }

    public String getUrl() {
        return url;
    }

    public List<SiteNode> getchildSites() {
        return childSites;
    }

    private void parseLinks() {
        try {
            Document doc = Jsoup.connect(this.url).maxBodySize(0).get();
            Elements links = doc.select(cssQuery); // все ссылки на сайте по шаблону
            fillChildSites(links); // заполнение коллекции с дочерними сайтами
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillChildSites(Elements links) {
        for (Element link : links) {
            SiteNode siteNode = new SiteNode(link.attr("abs:href")); // абсолютный путь
            this.childSites.add(siteNode);
        }
    }
}
