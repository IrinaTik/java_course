import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

public class SiteNode {

    private static final String cssQuery = "a[href]";
    private static final String[] filters = {"//skillbox.ru", "#"};

    private String url;
    private Set<String> visitedUrls;
    private List<SiteNode> childSites;
    private int tabNumber;

    public SiteNode(String url, int tabNumber, Set<String> visitedUrls) {
        this.url = url;
        this.visitedUrls = visitedUrls;
        this.tabNumber = tabNumber;
        this.childSites = new ArrayList<>();
    }

    public SiteNode(String url, int tabNumber) {
        this.url = url;
        this.visitedUrls = Collections.synchronizedSet(new HashSet<>());
        this.visitedUrls.add(this.url);
        this.tabNumber = tabNumber;
        this.childSites = new ArrayList<>();
    }

    public String getUrl() {
        return this.url;
    }

    public String getUrlWithTabs() {
        String tabs = "";
        for (int i = 0; i < tabNumber; i++) {
            tabs = tabs + "\t";
        }
        return tabs + this.url;
    }

    public List<SiteNode> getchildSites() {
        return this.childSites;
    }

    public void parseLinks() {
        try {
            Thread.sleep(150);
            Document doc = Jsoup.connect(this.url).maxBodySize(0).ignoreContentType(true).get();
            Elements links = doc.select(cssQuery); // все ссылки на сайте
            fillChildSites(clearLinks(links)); // подчищаем список ссылок и заполняем коллекции с дочерними сайтам
        } catch (HttpStatusException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // убираем из списка дочерних ссылок те ссылки, которые дублируют текущую ссылку и уже посещенные ссылки, чтобы не попасть в бесконечный цикл,
    //      а также убираем несоответствующие условию ссылки
    private Set<String> clearLinks(Elements links) {
        return links
                .stream()
                .map(l -> l.attr("abs:href"))
                .filter(url1 -> !this.url.equals(url1) && !isDoubleLink(url1) && isValidLink(url1))
                .collect(Collectors.toSet());
    }

    private boolean isValidLink(String currentUrl) {
        return currentUrl.contains(filters[0]) && !currentUrl.contains(filters[1]);
    }

    private boolean isDoubleLink(String currentUrl) {
        if (visitedUrls.contains(currentUrl)) {
            return true;
        }
        this.visitedUrls.add(currentUrl);
        return false;
    }

    private void fillChildSites(Set<String> urls) {
        for (String url : urls) {
            SiteNode siteNode = new SiteNode(url, this.tabNumber + 1, this.visitedUrls);
            this.childSites.add(siteNode);
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        this.getchildSites().forEach(siteNode -> buffer.append(siteNode.toString()));
        return this.getUrlWithTabs() + "\n" + buffer.toString();
    }
}
