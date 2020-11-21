import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;

public class CopyFilesMain {

    public static void main(String[] args) {

        try {
            System.out.print("Input the source path: ");
            String sourceDirPath = getDirectoryPath();
            File sourceDirectory = new File(sourceDirPath);

            System.out.print("Input the destination path: ");
            String destinationDirPath = getDirectoryPath();

            FileHelper.copyFolder(sourceDirectory, destinationDirPath);

            System.out.println("Finished. Check " + destinationDirPath + " for copied files.");
        } catch (Exception ex) {
            ex.printStackTrace();
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

}
