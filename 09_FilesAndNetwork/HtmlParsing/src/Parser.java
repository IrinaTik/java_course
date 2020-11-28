import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
            Elements links = document.select("img[src]");
            for (int i = 0; i < links.size(); i++) {
                String link = links.get(i).attr("abs:src");
                String fileNameOrig = link.substring(link.lastIndexOf('/') + 1);
                String fileName = fileNameOrig.replaceAll(checkExtension(fileNameOrig), "_");
                String sPath = TARGET + fileName;
                Path path = Paths.get(sPath);
                savePicFromURL(link, path);
            }
            System.out.println("Saving images is complete. Check the " + TARGET + " directory");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String checkExtension(String link) {
        if (link.contains(".jpg") || link.contains(".png")
                || link.contains(".jpeg") || link.contains(".bmp")) {
            return "[?=]";
        } else {
            return "[?=.]";
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
