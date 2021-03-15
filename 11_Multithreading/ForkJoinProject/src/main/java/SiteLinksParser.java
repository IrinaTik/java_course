import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SiteLinksParser extends RecursiveTask<SiteNode> {

    private final SiteNode node;

    public SiteLinksParser(SiteNode node) {
        this.node = node;
    }

    @Override
    protected SiteNode compute() {
        node.parseLinks();

        List<SiteLinksParser> tasks = new ArrayList<>();

        for (SiteNode childNode : node.getchildSites()) {
            SiteLinksParser task = new SiteLinksParser(childNode);
            task.fork();
            tasks.add(task);
        }

        for (SiteLinksParser task : tasks) {
            task.join();
        }

        return node;
    }
}
