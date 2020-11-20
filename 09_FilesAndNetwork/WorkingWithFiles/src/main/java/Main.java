import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");

    public static void main(String[] args) {
        try {
            File directory = new File(getDirectoryPath());
            System.out.println("Размер папки (без рекурсии): "
                    + readableSizes(getDirSize_woutRecursion(directory)));
            System.out.println("Размер папки (с рекурсией): "
                    + readableSizes(getDirSize_wListFiles(directory)));
        } catch (Exception e) {
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

    public static long getDirSize_wListFiles(File dir) {
        long sum = 0;

        File[] arrFiles = dir.listFiles();
        for (File arrFile : arrFiles) {
            if (arrFile.isDirectory()) {
                sum += getDirSize_wListFiles(arrFile);
            } else {
                sum += arrFile.length();
            }
        }

        return sum;
    }

    public static long getDirSize_woutRecursion(File file) {
        long sum = 0;

        List<File> dir = new ArrayList<>();
        if (file.isDirectory()) {
            dir.add(file);
            while (dir.size() > 0) {
                File folder = dir.get(0);
                dir.remove(0);
                File[] folderFiles = folder.listFiles();
                for (File folderFile : folderFiles) {
                    if (folderFile.isDirectory()) {
                        dir.add(folderFile);
                    } else {
                        sum += folderFile.length();
                    }
                }
            }
        } else {
            sum += file.length();
        }

        return sum;
    }

    public static long getDirSize_wFiles(File file) throws IOException {
        String path = file.getAbsolutePath();
        return Files.walk(Paths.get(path), 10)
                .filter(Files::isRegularFile)
                .map(p -> p.toFile().length())
                .reduce(Long::sum).orElse((long) 0);
    }

    private static String readableSizes(long size) {
        if (size == 0) {
            return "0";
        }
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int)(Math.log10(size) / Math.log10(1024));
        return DECIMAL_FORMAT.format(size / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

}
