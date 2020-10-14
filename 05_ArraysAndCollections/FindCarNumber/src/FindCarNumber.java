import java.util.*;

public class FindCarNumber {

    private static final int MAX_REGION = 199;
    private static final String[] LETTERS = {"С", "М", "Т", "В", "А", "Р", "О", "Н", "Е", "У"};

    public static void main(String[] args) {

        ArrayList<String> arrCars = initialize();
        HashSet<String> hsetCars = new HashSet<>();
        hsetCars.addAll(arrCars);
        TreeSet<String> tsetCars = new TreeSet<>();
        tsetCars.addAll(arrCars);



    }

    private static boolean searchBinary(List<String> list, String query) {
        if (Collections.binarySearch(list, query) == -1) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean searchSet(Set<String> set, String query) {

        return false;
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

    private static ArrayList<String> fillArray(int r, int i, ArrayList<String> list) {
        for (int first = 0; first < LETTERS.length; first++) {
            for (int second = 0; second < LETTERS.length; second++) {
                for (int third = 0; third < LETTERS.length; third++) {
                    String carNumber = LETTERS[first] + i + i + i + LETTERS[second] + LETTERS[third] + getRegion(r);
                    list.add(carNumber);
                }
            }
        }
        return list;
    }

    public static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
