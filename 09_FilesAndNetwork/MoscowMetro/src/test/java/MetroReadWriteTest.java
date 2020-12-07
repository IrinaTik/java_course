import DataModel.Metro;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetroReadWriteTest {

    private static final String JSONFILE_PATH = "./src/main/resources/MoscowMetroMap.json";
    private static final String SITE_URL = "https://www.moscowmap.ru/metro.html#lines";
    private static Metro metroHTML;
    private static Metro metroJSON;

    @BeforeAll
    public static void setUp() {
        metroHTML = HTMLParsing.parseHTML(SITE_URL);
        metroJSON = JsonHelper.parseJSON(JSONFILE_PATH);
    }

    @Test
    @DisplayName("Compare metro from HTML and metro from JSON")
    public void testMetro() {
        assertEquals(metroHTML, metroJSON);
    }

}
