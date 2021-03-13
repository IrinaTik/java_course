import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SiteNode {

    private static final String cssQuery = "a[href]";
    private static final String[] filters = {"/skillbox.ru", "#"};
    private static Set<String> visitedUrls = new HashSet<>();

    private String url;
    private List<SiteNode> childSites;
    private int tabNumber;

    public SiteNode(String url, int tabNumber) {
        this.url = url;
        visitedUrls.add(this.url);
        this.tabNumber = tabNumber;
        this.childSites = new ArrayList<>();
//        parseLinks();
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
        Set<String> urlSet = links.stream().map(l -> l.attr("abs:href")).collect(Collectors.toSet());
        Set<String> cloneLinks = new HashSet<>(urlSet);
        for (String url : cloneLinks) {
            if (this.url.equals(url) || isDoubleLink(url) || !isValidLink(url)) {
                urlSet.remove(url);
            }
        }
        return urlSet;
    }

    private boolean isValidLink(String currentUrl) {
        if (currentUrl.contains(filters[0]) && !currentUrl.contains(filters[1])) {
            return true;
        }
        return false;
    }

    private boolean isDoubleLink(String currentUrl) {
        for (String visitedUrl : visitedUrls) {
            if (visitedUrl.equals(currentUrl)) {
                return true;
            }
        }
        return false;
    }

    private void fillChildSites(Set<String> urls) {
        visitedUrls.addAll(urls);
        for (String url : urls) {
            SiteNode siteNode = new SiteNode(url, this.tabNumber + 1); // абсолютный путь
            this.childSites.add(siteNode);
        }
    }

}
