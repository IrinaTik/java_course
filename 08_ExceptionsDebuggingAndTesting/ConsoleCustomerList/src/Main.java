import java.util.Scanner;

public class Main
{
    private static String addCommand = "add Василий Петров " +
            "vasily.petrov@gmail.com +79215637722";
    private static String deleteCommand = "delete Василий Петров";
    private static String commandExamples = "\t" + addCommand + "\n" +
            "\tlist\n\tcount\n\t" + deleteCommand;
    private static String commandError = "Wrong command! Available command examples: \n" +
            commandExamples;
    private static String helpText = "Command examples:\n" + commandExamples;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        CustomerStorage executor = new CustomerStorage();
        for(;;)
        {
            String command = scanner.nextLine();
            String[] tokens = command.split("\\s+", 2);
            if(tokens[0].equals("add")) {
                addCustomer(executor, tokens);
            }
            else if(tokens[0].equals("list")) {
                executor.listCustomers();
            }
            else if(tokens[0].equals("remove"))
            {
                deleteCustomer(executor, tokens);
            }
            else if(tokens[0].equals("count")) {
                System.out.println("There are " + executor.getCount() + " customers");
            }
            else if(tokens[0].equals("help")) {
                System.out.println(helpText);
            }
            else {
                System.out.println(commandError);
            }
        }
    }

    private static void deleteCustomer(CustomerStorage executor, String[] tokens) {
        try {
            executor.removeCustomer(tokens[1]);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong delete command format! Example: \n" + deleteCommand);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void addCustomer(CustomerStorage executor, String[] tokens) {
        try {
            executor.addCustomer(tokens[1]);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Wrong add command format! Example: \n" + addCommand);
        }
    }
}
