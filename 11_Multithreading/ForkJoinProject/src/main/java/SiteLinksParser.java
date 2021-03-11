import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SiteLinksParser extends RecursiveTask<StringBuffer> {

    private final SiteNode node;

    public SiteLinksParser(SiteNode node) {
        this.node = node;
    }

    @Override
    protected StringBuffer compute() {
        StringBuffer buffer = new StringBuffer(node.getUrlWithTabs());

        List<SiteLinksParser> tasks = new ArrayList<>();

        for (SiteNode childNode : node.getchildSites()) {
            SiteLinksParser task = new SiteLinksParser(childNode);
            task.fork();
            tasks.add(task);
        }

        for (SiteLinksParser task : tasks) {
            buffer.append(task.join());
        }

        return buffer;
    }
}
