import DataModel.Metro;

public class MosMetro {

    private static final String JSONFILE_PATH = "./09_FilesAndNetwork/MoscowMetro/src/main/resources/MoscowMetroMap.json";
    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";

    public static void main(String[] args) {
        Metro metro = HTMLParsing.parseHTML(SITE_URL);
        metro.output();
        JsonHelper.writeToJSON(metro, JSONFILE_PATH);
        Metro metroFromJSON = JsonHelper.parseJSON(JSONFILE_PATH);
        metroFromJSON.output();
    }
}
