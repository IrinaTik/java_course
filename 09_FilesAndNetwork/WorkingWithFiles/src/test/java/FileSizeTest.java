import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileSizeTest {

    private static final String DIRECTORY_PATH = "../files/";

    @Test
    public void testDirSizeCalculation() {
        try {
            long expected = 30_298;
            System.out.println(Paths.get(DIRECTORY_PATH).toAbsolutePath());
            long actual = Main.getDirSize(DIRECTORY_PATH);
            assertEquals(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
