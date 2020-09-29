import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Birthday {

    public static void main(String[] args) {
        int day = 19;
        int month = 0;
        int year = 1989;


        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, EEEE");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        while (calendar.get(Calendar.YEAR) <= currentYear) {
            System.out.println(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.YEAR, 1);
        }
    }

}
