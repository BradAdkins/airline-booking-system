import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.DayOfWeek;

public class BookingManager {
    private static int counter = 1000;
    private static List<Booking> allBookings = new ArrayList<>();

    private static final String FILE_NAME = "bookings.csv";

    public static Booking createBooking(Flight flight, String passengerName) {
        if (flight.isFull()) {
            System.out.println("Sorry, this flight is full.");
            return null;
        }

        String ref = "B" + counter++;
        Passenger p = new Passenger(passengerName, ref);
        flight.addPassenger(p);

        Booking booking = new Booking(ref, flight, p);
        allBookings.add(booking);

        saveBookings();

        return booking;
    }

    public static void printInvoice(Booking booking) {
        double price = booking.getFlight().getRoute().getPrice();

        System.out.println("----- Booking Confirmation -----");
        System.out.println("Booking Ref: " + booking.getReference());
        System.out.println("Passenger: " + booking.getPassenger().getName());
        System.out.println("From: " + booking.getFlight().getRoute().getFrom());
        System.out.println("To: " + booking.getFlight().getRoute().getTo());
        System.out.println("Date: " + booking.getFlight().getDate());
        System.out.println("Aircraft: " + booking.getFlight().getRoute().getAircraft().getType());
        System.out.println("Price: $" + String.format("%.2f", price));
        System.out.println("Thank you for booking!");
    }


    public static Booking getBookingByRef(String ref) {
        for (Booking b : allBookings) {
            if (b.getReference().equals(ref)) return b;
        }
        return null;
    }

    public static boolean cancelBooking(String ref) {
        Booking toRemove = null;
        for (Booking b : allBookings) {
            if (b.getReference().equals(ref)) {
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            toRemove.getFlight().getPassengers().remove(toRemove.getPassenger());
            allBookings.remove(toRemove);
            saveBookings();
            return true;
        }
        return false;
    }

    public static void saveBookings() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : allBookings) {
                writer.println(b.getReference() + "," +
                        b.getPassenger().getName() + "," +
                        b.getFlight().getRoute().getFrom() + "," +
                        b.getFlight().getRoute().getTo() + "," +
                        b.getFlight().getDate() + "," +
                        b.getFlight().getRoute().getAircraft().getType() + "," +
                        b.getFlight().getRoute().getPrice());

            }
        } catch (IOException e) {
            System.out.println("Error saving bookings: " + e.getMessage());
        }
    }

    public static void loadBookings(List<Route> routes) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String ref = parts[0];
                String name = parts[1];
                String from = parts[2];
                String to = parts[3];
                LocalDate date = LocalDate.parse(parts[4]);

                Route route = findRoute(routes, from, to);
                if (route == null) continue;

                Flight flight = FlightScheduler.getOrCreateFlight(date, route);
                Passenger p = new Passenger(name, ref);
                flight.addPassenger(p);

                Booking b = new Booking(ref, flight, p);
                allBookings.add(b);

                try {
                    int num = Integer.parseInt(ref.substring(1));
                    if (num >= counter) counter = num + 1;
                } catch (Exception ignore) {}
            }
        } catch (IOException e) {
            System.out.println("Error loading bookings: " + e.getMessage());
        }
    }

    private static Route findRoute(List<Route> routes, String from, String to) {
        for (Route r : routes) {
            if (r.getFrom().equals(from) && r.getTo().equals(to)) return r;
        }
        return null;
    }
}

