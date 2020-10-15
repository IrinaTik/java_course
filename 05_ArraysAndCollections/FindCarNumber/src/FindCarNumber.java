import java.util.*;

public class FindCarNumber {

    private static final int MAX_REGION = 199;
    private static final String[] LETTERS = {"C", "M", "T", "B", "A", "P", "O", "H", "E", "Y"};

    public static void main(String[] args) {

        ArrayList<String> arrCars = initialize();
        HashSet<String> hsetCars = new HashSet<>();
        hsetCars.addAll(arrCars);
        TreeSet<String> tsetCars = new TreeSet<>();
        tsetCars.addAll(arrCars);

        String query = getQuery();

        System.out.print("Поиск перебором: ");
        doSearch(arrCars, query);
        System.out.print("Бинарный поиск: ");
        searchBinary(arrCars, query);
        System.out.print("Поиск в HashSet: ");
        doSearch(hsetCars, query);
        System.out.print("Поиск в TreeSet: ");
        doSearch(tsetCars, query);
    }

    private static void doSearch(Collection<String> collection, String query) {
        long start = getTime();
        System.out.print(searchCollection(collection, query) ? "номер найден, " : "номер не найден, ");
        long end = getTime();
        System.out.println("поиск занял " + getDuration(start, end) + " нс");
    }

    private static long getTime() {
        return System.nanoTime();
    }

    private static long getDuration(long start, long end) {
        return (end - start);
    }

    private static boolean searchCollection(Collection<String> collection, String query) {
        return collection.contains(query);
    }

    private static void searchBinary(List<String> list, String query) {
        Collections.sort(list);
        long start = getTime();
        System.out.print(Collections.binarySearch(list, query) >= 0 ? "номер найден, " : "номер не найден, ");
        long end = getTime();
        System.out.println("поиск занял " + getDuration(start, end) + " нс");
    }

    private static ArrayList<String> initialize() {
        ArrayList<String> list = new ArrayList<>();

        for (int r = 1; r <= MAX_REGION; r++ ) {
            for (int i = 0; i < 10; i++) {
                fillArray(r, i, list);
            }
        }

        return list;
    }

    private static String getRegion(int num) {
        if (num < 10) {
            return  ("0" + num);
        } else {
            return Integer.toString(num);
        }
    }

    private static void fillArray(int r, int i, ArrayList<String> list) {
        for (int first = 0; first < LETTERS.length; first++) {
            for (int second = 0; second < LETTERS.length; second++) {
                for (int third = 0; third < LETTERS.length; third++) {
                    String carNumber = LETTERS[first] + i + i + i + LETTERS[second] + LETTERS[third] + getRegion(r);
                    list.add(carNumber);
                }
            }
        }
    }

    public static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
