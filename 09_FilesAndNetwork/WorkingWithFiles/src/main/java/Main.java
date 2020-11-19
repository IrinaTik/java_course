import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");

    public static void main(String[] args) {
        try {
            long dirSize = getDirSize(getDirectoryPath());
            System.out.println("Размер папки: ");
            System.out.println(dirSize + " bytes");
            System.out.println(getSizeInKB(dirSize) + " Kb");
            System.out.println(getSizeInMB(dirSize) + " Mb");
            System.out.println(getSizeInGB(dirSize) + " Gb");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDirectoryPath() {
        Scanner scanner = new Scanner(System.in);
        String dirPath;
        while (true) {
            dirPath = scanner.next();
            File dir = Paths.get(dirPath).toFile();
            if (dir.exists() && dir.isDirectory()) {
                break;
            }
            System.out.println("Not found or not a directory. Try again.");
        }
        return dirPath;
    }

    public static long getDirSize(String path) throws IOException {
        return Files.walk(Paths.get(path), 10)
                .filter(Files::isRegularFile)
                .map(p -> p.toFile().length())
                .reduce(Long::sum).orElse((long) 0);
    }

    private static String getSizeInKB(long size) {
        return DECIMAL_FORMAT.format((double) size/1024);
    }

    private static String getSizeInMB(long size) {
        return DECIMAL_FORMAT.format((double) size/(1024*1024));
    }

    private static String getSizeInGB(long size) {
        return DECIMAL_FORMAT.format((double) size/(1024*1024*1024));
    }



}
