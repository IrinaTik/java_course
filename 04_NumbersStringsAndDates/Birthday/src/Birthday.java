import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class Birthday {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat inputDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat dateFormatRus = new SimpleDateFormat("dd.MM.yyyy - EEEE");
        DateFormat dateFormatEng = new SimpleDateFormat("dd.MM.yyyy - E", Locale.ENGLISH);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDateFormat.parse(scanner.nextLine()));

        Calendar currentCalendar = Calendar.getInstance();

        for (int i = 0; currentCalendar.after(calendar); i++) {
            System.out.println(i + " - " + dateFormatRus.format(calendar.getTime()));
            System.out.println(i + " - " + dateFormatEng.format(calendar.getTime()));
            calendar.add(Calendar.YEAR, 1);
        }
    }
}
