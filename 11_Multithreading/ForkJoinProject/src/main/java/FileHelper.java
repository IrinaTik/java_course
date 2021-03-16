import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class FileHelper {

    private final String path;
    private final File file;

    public FileHelper(String path) {
        this.path = path;
        this.file = Paths.get(this.path).toFile();
    }

    public boolean createFile() {
        try {
            return this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void writeToFile(String lines) {
        try {
            PrintWriter writer = new PrintWriter(path);
            writer.write(lines);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
