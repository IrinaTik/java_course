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

        // пул потоков
        ExecutorService service = Executors.newFixedThreadPool(processorsCount);
        File[] splitFiles;
        for (int i = 0; i < processorsCount; i++) {
            if (i != processorsCount - 1) {
                splitFiles = new File[limit];
            } else {
                // последний массив чуть больше остальных, чтобы вместить остаток от деления
                int lastLimit = files.length - limit*i;
                splitFiles = new File[lastLimit];
            }
            System.arraycopy(files, limit * i, splitFiles, 0, splitFiles.length);
            service.submit(new ImageResizer(splitFiles, dstFolder));
        }

        service.shutdown();
    }
}
