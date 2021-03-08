import java.util.Collection;

public class SiteNode {

    private String url;
    private Collection<SiteNode> links;

    public SiteNode(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Collection<SiteNode> getLinks() {
        return links;
    }

    public void parseLinks() {

    }
}
