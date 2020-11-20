import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileSizeTest {

    private static final String DIRECTORY_PATH = "../files/";
    private static final long expected = 30_298;


    @Test
    @DisplayName("Recursion method (file.listFiles used)")
    public void testDirSizeCalculation_wListFiles() {
        try {
            long actual = Main.getDirSize_wListFiles(new File(DIRECTORY_PATH));
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Method without recursion (cycles and collections used)")
    public void testDirSizeCalculation_woutRecursion() {
        try {
            long actual = Main.getDirSize_woutRecursion(new File(DIRECTORY_PATH));
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Method with Files library (Files.walk used)")
    public void testDirSizeCalculation_wFiles() {
        try {
            long actual = Main.getDirSize_wFiles(new File(DIRECTORY_PATH));
            assertEquals(expected, actual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
