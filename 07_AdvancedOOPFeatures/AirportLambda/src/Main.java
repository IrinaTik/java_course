import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        System.out.println("Количество самолетов в аэропорту: " + airport.getAllAircrafts().size());

        Calendar now = Calendar.getInstance();
        Calendar in2hours = Calendar.getInstance();
        in2hours.add(Calendar.HOUR, 2);

        System.out.println("=====In 2 hours=====");
        airport.getTerminals().stream()
                .flatMap(t -> t.getFlights().stream())
                .filter(f -> f.getDate().before(in2hours.getTime()) && f.getDate().after(now.getTime()))
                .filter(f -> f.getType().equals(Flight.Type.DEPARTURE))
                .forEach(System.out::println);

    }

}
