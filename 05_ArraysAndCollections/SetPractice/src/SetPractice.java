import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPractice {

    public static void main(String[] args) {
        TreeSet<String> set = new TreeSet<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String fullCommand = scanner.nextLine();

            String[] fullCommandSplit = fullCommand.split("\\s+");
            String commandWord = fullCommandSplit[0];
            switch (commandWord) {
                case "ADD" :
                    addAction(set, fullCommandSplit);
                    break;
                case "LIST" :
                    output(set);
                    break;
                case "EXIT" :
                    System.out.println("God is leaving the building...");
                    return;
                default:
                    System.out.println("Wrong command, try again");
                    break;
            }
        }
    }

    private static void addAction(TreeSet<String> set, String[] fullCommandSplit) {
        if (fullCommandSplit.length < 2) {
            System.out.println("Wrong ADD command format, try again");
        } else {
            String mail = fullCommandSplit[1].trim();
            mailAdd(mail, set);
        }
    }

    private static void mailAdd(String mail, TreeSet<String> set) {
        Pattern pattern = Pattern.compile("[\\d\\w]+@[\\d\\w]+\\.[\\w]+");
        Matcher matcher = pattern.matcher(mail);
        if (matcher.matches()) {
            set.add(mail);
        } else {
            System.out.println("Wrong email format, try again");
        }
    }

    private static void output(TreeSet<String> set) {
        for (String s : set) {
            System.out.println(s);
        }
    }

}
