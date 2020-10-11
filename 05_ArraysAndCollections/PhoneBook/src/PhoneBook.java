import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PhoneBook {

    public static void main(String[] args) {
        TreeMap<String, String> phoneBase = initialize();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String query = scanner.nextLine();
            if (query.equals("EXIT")) {
                return;
            }
            executeQuery(phoneBase, query);
        }

    }

    private static void executeQuery(Map<String, String> map, String query) {
        if (map.containsKey(query)) {
            // выводим инфу о контакте
            System.out.println(query + " => " + nicePhoneOutput(map.get(query)));
            return;
        }
        // вносим новую запись в базу
        inputNewEntry(map, query);
    }

    private static void inputNewEntry(Map<String, String> map, String query) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("No such name in the phone book. Please provide the phone number for it: ");
        while (true) {
            String phone = scanner.nextLine();
            if (phone.equals("EXIT")) {
                // если опечатался и не хочешь на самом деле вводить ничего нового
                return;
            }
            if (isPhoneCorrect(phone)) {
                map.put(query, unifyPhone(phone));
                System.out.println("Acknowledged");
                return;
            } else {
                System.out.println("Wrong phone format, try again");
            }
        }
    }

    private static String unifyPhone (String phone) {
        return phone.replaceAll("\\D", "");
    }

    private static boolean isPhoneCorrect(String phone) {
        String str = unifyPhone(phone);
        if (str.length() < 10 || str.length() > 11) {
            return false;
        }
        if (str.length() == 10) {
            str = "7" + str;
        } else {
            return str.matches("^[87]\\d+");
        }
        return true;
    }

    private static String nicePhoneOutput(String phone) {
        return phone.replaceAll("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "+7 ($2) $3-$4-$5");
    }

    private static TreeMap<String, String> initialize() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("Иванов","79851112233");
        map.put("Петров","79854145577");
        map.put("Сидоров","79857559088");
        map.put("Кац","79857812236");
        map.put("Плахов","79994567890");
        return map;
    }
}
