import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class HTMLParsing extends RecursiveTask<String> {

    private static final String SITE_ROOTURL = "https://lenta.ru/";

    public void getAllLinks() {
        try {
            Document doc = Jsoup.connect(SITE_ROOTURL).maxBodySize(0).get();
            Elements links = doc.select("a[href*=skillbox]");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String compute() {
        return null;
    }
}
