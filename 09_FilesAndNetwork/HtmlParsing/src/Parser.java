import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Parser {

    private static final String SITE_URL = "https://lenta.ru/";
    private static final String TARGET = "D:/Pics/";

    public static void main(String[] args) {

        try {
            Document document = Jsoup.connect(SITE_URL).get();
            Elements links = document.select("img[src~=.(png|jpe?g|bmp)]");
            for (Element link : links) {
                String absLink = link.attr("abs:src");
                String fileName = absLink.substring(absLink.lastIndexOf('/') + 1);
                String sPath = TARGET + fileName;
                Path path = Paths.get(sPath);
                savePicFromURL(absLink, path);
            }
            System.out.println("Saving images is complete. Check the " + TARGET + " directory");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void savePicFromURL(String url, Path filePath) {
        try {
            URL imgLink = new URL(url);
            InputStream inputStream = imgLink.openStream();
            Files.copy(inputStream,  filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
