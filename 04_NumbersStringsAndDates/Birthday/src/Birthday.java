import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Birthday {

    public static void main(String[] args) {
        int day = 19;
        int month = 0;
        int year = 1989;


        DateFormat dateFormatRus = new SimpleDateFormat("dd.MM.yyyy - EEEE");
        DateFormat dateFormatEng = new SimpleDateFormat("dd.MM.yyyy - E", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        int i = 0;
        while (calendar.get(Calendar.YEAR) <= currentYear) {
            System.out.println(i + " - " + dateFormatRus.format(calendar.getTime()));
            System.out.println(i + " - " + dateFormatEng.format(calendar.getTime()));
            calendar.add(Calendar.YEAR, 1);
            i++;
        }
    }

}
