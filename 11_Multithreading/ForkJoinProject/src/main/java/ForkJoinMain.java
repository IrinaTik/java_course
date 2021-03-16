import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinMain {

    private static final String OUTPUTFILE_PATH = "C:/ZugZug/1.txt";
//    private static final String SITE_ROOTURL = "http://secure-headland-59304.herokuapp.com/";
    private static final String SITE_ROOTURL = "https://skillbox.ru/";

    public static void main(String[] args) {
        SiteNode rootNode = new ForkJoinPool().invoke(new SiteLinksParser(new SiteNode(SITE_ROOTURL, 0, new HashSet<>())));
        FileHelper fh = new FileHelper(OUTPUTFILE_PATH);
        if (fh.createFile()) {
            System.out.println("=== Карта сайта " + SITE_ROOTURL + " записана в файл " + OUTPUTFILE_PATH + " ===");
            fh.writeToFile(rootNode.toString());
        } else {
            System.out.println("=== Запись в файл не удалась, вывожу на консоль ===");
            System.out.println(rootNode.toString());
        }
    }

}
