import com.skillbox.airport.Airport;

public class Main {

    public static void main(String[] args) {
        Airport airport = Airport.getInstance();
        System.out.println("Количество самолетов в аэропорту: " + airport.getAllAircrafts().size());
    }

}
