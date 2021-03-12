import java.util.concurrent.ForkJoinPool;

public class ForkJoinMain {

    private static final String SITE_ROOTURL = "https://skillbox.ru/design/";

    public static void main(String[] args) {
        System.out.println(new ForkJoinPool().invoke(new SiteLinksParser(new SiteNode(SITE_ROOTURL, 0))));

    }

}
