import DataModel.Metro;

public class MosMetro {

    public static void main(String[] args) {
        Metro metro = HTMLParsing.parseHTML();
        metro.output();
        JsonHelper.writeToJSON(metro);
        Metro metroFromJSON = JsonHelper.parseJSON();
        metroFromJSON.output();
    }

}
