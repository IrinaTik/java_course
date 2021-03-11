import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SiteNode {

    private static final String cssQuery = "a[href^=/rubrics/russia]";

    private String url;
    private List<SiteNode> childSites;
    private List<String> parentUrls;
    private int tabNumber;

    public SiteNode(String url, List<String> parentUrls, int tabNumber) {
        this.url = url;
        this.parentUrls = new ArrayList<>(parentUrls);
        this.parentUrls.add(this.url);
        this.tabNumber = tabNumber;
        this.childSites = new ArrayList<>();
        parseLinks();
    }

    public String getUrl() {
        return this.url;
    }

    public List<SiteNode> getchildSites() {
        return this.childSites;
    }

    public List<String> getParentUrls() {
        return this.parentUrls;
    }

    private void parseLinks() {
        try {
            Document doc = Jsoup.connect(this.url).maxBodySize(0).get();
            Elements links = doc.select(cssQuery); // все ссылки на сайте по шаблону
            clearLinks(links); // подчищаем список ссылок
            fillChildSites(links); // заполнение коллекции с дочерними сайтами
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // убираем из списка дочерних ссылок те ссылки, которые дублируют текущую ссылку, чтобы не попасть в бесконечный цикл
    private void clearLinks(Elements links) {
        Elements cloneLinks = new Elements(links);
        for (Element link : cloneLinks) {
            if (this.url.equals(link.attr("abs:href"))) {
                links.remove(link);
            }
        }
    }

    private void fillChildSites(Elements links) {
        for (Element link : links) {
            SiteNode siteNode = new SiteNode(link.attr("abs:href"), this.parentUrls, this.tabNumber + 1); // абсолютный путь
            this.childSites.add(siteNode);
        }
    }

    public String getUrlWithTabs() {
        String tabs = "";
        for (int i = 0; i < tabNumber; i++) {
            tabs = tabs + "\t";
        }
        return tabs + this.url;
    }

    private String getParentUrl(String url) {
        url = escapeEnd(url);
        return url.substring(0, url.lastIndexOf("/"));
    }

    private String escapeEnd(String s) {
        int escapeIndex = s.lastIndexOf("/");
        if (escapeIndex == s.length() - 1) {
            s = s.substring(0, escapeIndex);
        }
        return s;
    }
}
