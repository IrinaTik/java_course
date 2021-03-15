import java.util.concurrent.ForkJoinPool;

public class ForkJoinMain {

//    private static final String SITE_ROOTURL = "http://secure-headland-59304.herokuapp.com/";
    private static final String SITE_ROOTURL = "https://skillbox.ru/";

    public static void main(String[] args) {
        SiteNode rootNode = new ForkJoinPool().invoke(new SiteLinksParser(new SiteNode(SITE_ROOTURL, 0)));
        System.out.println(rootNode.toString());
    }

}
