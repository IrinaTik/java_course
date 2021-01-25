import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    public static void main(String[] args)
    {
//        String srcFolder = "/users/sortedmap/Desktop/src";
//        String dstFolder = "/users/sortedmap/Desktop/dst";
        String srcFolder = "d:/Pictures/";
        String dstFolder = "d:/Pics/";

        // получение массива файлов
        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();

        // количество доступных процессоров
        int processorsCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Processors: " + processorsCount);

        // делим файлы по массивам длиной limit, чтобы уместиться в количество процессоров
        int limit = files.length / processorsCount;
        int remainder = files.length % processorsCount;

        // пул потоков
        ExecutorService service = Executors.newFixedThreadPool(processorsCount);
        File[] splitFiles;
        for (int i = 0; i < processorsCount; i++) {
            if (i < remainder) {
                splitFiles = new File[limit + 1];
                System.arraycopy(files, (limit + 1) * i, splitFiles, 0, splitFiles.length);
            } else {
                splitFiles = new File[limit];
                System.arraycopy(files, (limit + 1) * remainder + limit * (i - remainder), splitFiles, 0, splitFiles.length);
            }
            service.submit(new ImageResizer(splitFiles, dstFolder));
        }

        service.shutdown();
    }
}
