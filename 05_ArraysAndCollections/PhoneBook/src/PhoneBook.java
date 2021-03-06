import java.util.*;

public class PhoneBook {

    public static void main(String[] args) {
        TreeMap<String, String> phoneBase = initialize();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String query = scanner.nextLine();
            if (query.equals("EXIT")) {
                return;
            }
            if (query.equals("LIST")) {
                baseOutput(phoneBase);
            } else {
                executeQuery(phoneBase, query);
            }
        }

    }

    private static void executeQuery(Map<String, String> map, String name) {
        if (map.containsValue(name)) {
            // выводим инфу о контакте
            ArrayList<String> contactPhones = searchforContact(map, name);
            contactInfoOutput(name, contactPhones);
            return;
        }
        // вносим новую запись в базу
        inputNewEntry(map, name);
    }

    private static void contactInfoOutput(String name, ArrayList<String> phoneList) {
        System.out.print(name + " => ");
        for (int i = 0; i < phoneList.size(); i++) {
            if (i == 0) {
                System.out.println(nicePhoneOutput(phoneList.get(i)));
            } else {
                System.out.println("\t=> " + nicePhoneOutput(phoneList.get(i)));
            }
        }
    }

    private static ArrayList<String> searchforContact(Map<String, String> map, String name) {
        ArrayList<String> phoneList = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(name)) {
                 phoneList.add(entry.getKey());
            }
        }
        return phoneList;
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
                map.put(unifyPhone(phone), query);
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

    private static void baseOutput(Map<String, String> map) {
        TreeSet<String> names = makeSet(map);
        for (String name : names) {
            executeQuery(map, name);
        }
    }

    private static TreeSet<String> makeSet(Map<String, String> map) {
        TreeSet<String> set = new TreeSet<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            set.add(entry.getValue());
        }
        return set;
    }

    private static TreeMap<String, String> initialize() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("79851112233","Иванов");
        map.put("79994567890","Кац");
        map.put("79854145577","Петров");
        map.put("79857559088","Сидоров");
        map.put("79857812236","Кац");
        return map;
    }
}