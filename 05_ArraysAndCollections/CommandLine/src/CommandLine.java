import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLine {

    public static void main(String[] args) {
        ArrayList<String> choreList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String fullCommand = scanner.nextLine();

            String[] fullCommandSplit = fullCommand.split("\\s+");
            String commandWord = fullCommandSplit[0];
            switch (commandWord) {
                case "ADD" :
                    choreAdd(fullCommand, choreList);
                    break;
                case "EDIT" :
                    choreEdit(fullCommand, choreList);
                    break;
                case "DELETE" :
                    deleteEdit(fullCommandSplit, choreList);
                    break;
                case "LIST" :
                    output(choreList);
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

    private static void deleteEdit(String[] fullCommandSplit, ArrayList<String> list) {
        try {
            int choreIndex = Integer.parseInt(fullCommandSplit[1]);
            list.remove(choreIndex);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage() + " - wrong choreIndex for deleting, try again");
        } catch (NumberFormatException ex) {
            System.out.println("Wrong index format, try again");
        } catch (Exception ex) {
            System.out.println("Houston, we have a problem");
            ex.printStackTrace();
        }
    }

    private static void choreEdit(String fullCommand, ArrayList<String> list) {
        Pattern addPattern = Pattern.compile("^(([A-Z]{4})(\\s+)(\\d+)(\\s+)([a-zA-Z0-9\\s\\pP]+))$"); //паттерн для изменения элемента списка
        Matcher matcher = addPattern.matcher(fullCommand);
        if (matcher.matches()) {
            try {
                int choreIndex = Integer.parseInt(fullCommand.replaceAll(addPattern.toString(), "$4"));
                String chore = fullCommand.replaceAll(addPattern.toString(), "$6");
                list.set(choreIndex, chore);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage() + " - wrong choreIndex for editing, try again");
            } catch (Exception ex) {
                System.out.println("Houston, we have a problem");
                ex.printStackTrace();
            }
        } else {
            System.out.println("Wrong format for EDIT command");
        }
    }

    private static void choreAdd(String fullCommand, ArrayList<String> list) {
        Pattern addPattern = Pattern.compile("^(([A-Z]{3})(\\s+)(\\d+)(\\s+)([a-zA-Z0-9\\s\\pP]+))$"); //паттерн для добавления в определенное место
        Matcher matcher = addPattern.matcher(fullCommand);
        if (matcher.matches()) {
            int choreIndex = Integer.parseInt(fullCommand.replaceAll(addPattern.toString(), "$4"));
            String chore = fullCommand.replaceAll(addPattern.toString(), "$6");
            try {
                list.add(choreIndex, chore);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println(ex.getMessage() + " - wrong choreIndex for adding, try again");
            } catch (Exception ex) {
                System.out.println("Houston, we have a problem");
                ex.printStackTrace();
            }
        } else {
            list.add(fullCommand.substring(4));
        }
    }

    private static void output(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " - " + list.get(i));
        }
    }

}
