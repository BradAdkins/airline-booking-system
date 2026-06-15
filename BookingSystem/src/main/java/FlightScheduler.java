import java.time.LocalDate;
import java.util.*;

public class FlightScheduler {
    private static Map<String, Flight> flightMap = new HashMap<>();

    public static List<Flight> getFlightsForDate(LocalDate date, List<Route> routes) {
        List<Flight> flights = new ArrayList<>();
        String day = date.getDayOfWeek().toString();

        for (Route route : routes) {
            for (String validDay : route.getOperatingDays()) {
                if (validDay.equalsIgnoreCase(day)) {
                    boolean isRotorua = route.getTo().equals("Rotorua") || route.getFrom().equals("Rotorua");

                    if (isRotorua) {
                        flights.add(getOrCreateFlight(date, route, "#1"));
                        flights.add(getOrCreateFlight(date, route, "#2"));
                    } else {
                        flights.add(getOrCreateFlight(date, route, ""));
                    }
                    break;
                }
            }
        }

        return flights;
    }

    private static String makeKey(Route route, LocalDate date, String suffix) {
        return route.getFrom() + "-" + route.getTo() + "-" + date.toString() + suffix;
    }

    public static Flight getOrCreateFlight(LocalDate date, Route route) {
        return getOrCreateFlight(date, route, "");
    }

    public static Flight getOrCreateFlight(LocalDate date, Route route, String suffix) {
        String key = makeKey(route, date, suffix);
        if (!flightMap.containsKey(key)) {
            flightMap.put(key, new Flight(route, date));
        }
        return flightMap.get(key);
    }
}


