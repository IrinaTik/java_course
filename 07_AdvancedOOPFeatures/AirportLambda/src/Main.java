import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        System.out.println("Количество самолетов в аэропорту: " + airport.getAllAircrafts().size());

        // формирование списка всех поетов
        List<Flight> flights = new ArrayList<>();
        airport.getTerminals().forEach(t -> flights.addAll(t.getFlights()));

        // определение временного интервала
        Calendar now = Calendar.getInstance();
        Calendar in2hours = Calendar.getInstance();
        in2hours.add(Calendar.HOUR, 2);

        System.out.println("=====In 2 hours=====");
        flights.stream().filter(f -> f.getDate().before(in2hours.getTime()) && f.getDate().after(now.getTime())).forEach(System.out::println);

    }

}
