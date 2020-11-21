import java.io.*;

public class FileHelper {

    public static void copyFile(File file, String destinationPath) throws IOException {
        String fileName = file.getName();
        File destinationFile = new File(escapeEnd(destinationPath) + fileName);
        String content = getFileContents(file);
        writeFileContents(destinationFile, content);

    }

    public static void copyFolder(File folder, String destinationPath) throws IOException {
        File[] folderFiles = folder.listFiles();
        for (File folderFile : folderFiles) {
            if (folderFile.isDirectory()) {
                String path = escapeEnd(destinationPath) + folderFile.getName();
                File destinationFolder = new File(path);
                if (destinationFolder.exists()) {
                    throw new FolderAlreadyExists("Folder already exists " + path);
                } else if (destinationFolder.mkdir()) {
                    copyFolder(folderFile, path);
                } else {
                    throw new FolderNotCreated("Folder wasn't created " + path);
                }
            } else {
                copyFile(folderFile, destinationPath);
            }
        }
    }

    public static String getFileContents(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder builder = new StringBuilder();

        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            builder.append(line + "\n");
        }

        reader.close();
        return builder.toString();
    }

    public static void writeFileContents(File file, String content) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public static String escapeEnd(String s) {
        if (s.endsWith("/") || s.endsWith("\\")) {
            return s;
        } else {
            return s + "/";
        }
    }
}
