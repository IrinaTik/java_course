import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumbers {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String phoneNumber = reader.readLine();

        String formattedPhone = phoneNumber.replaceAll("[^0-9]", "");

        // формат типа "79993332211"
        Pattern pattern = Pattern.compile("^(?<head>7)(?<brackets>\\d{3})(?<three>\\d{3})(?<twoM>\\d{2})(?<twoE>\\d{2})$");

        while (true) {
            Matcher matcher = pattern.matcher(formattedPhone);
            if (matcher.matches()) {
                System.out.println("+" + matcher.group("head") + " (" + matcher.group("brackets") + ") " + matcher.group("three")
                        + "-" + matcher.group("twoM") + "-" + matcher.group("twoE"));
                break;
            } else {
                if (formattedPhone.length() == 10) {
                    formattedPhone = "7" + formattedPhone;
                } else {
                    if (formattedPhone.length() == 11) {
                        if (formattedPhone.charAt(0) == '8') {
                            formattedPhone = formattedPhone.replaceFirst("8", "7");
                        }
                    } else {
                        System.out.println("Неверный формат номера");
                        break;
                    }
                }
            }
        }
    }


    private static void oldWays() {
        String phoneNumber = "8-905-1234567";

        String formattedPhone = phoneNumber.replaceAll("[^0-9]", "");

        if (formattedPhone.length() == 10) {
            formattedPhone = "7" + formattedPhone;
        } else {
            if (formattedPhone.length() == 11) {
                if (formattedPhone.charAt(0) == '8') {
                    formattedPhone = formattedPhone.replaceFirst("8", "7");
                }
            } else {
                formattedPhone = "Неверный формат номера";
            }
        }

        System.out.println(formattedPhone);
    }

}