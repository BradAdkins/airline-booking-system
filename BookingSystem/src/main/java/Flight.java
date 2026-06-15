import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private Route route;
    private LocalDate date;
    private List<Passenger> passengers;

    public Flight(Route route, LocalDate date) {
        this.route = route;
        this.date = date;
        this.passengers = new ArrayList<>();
    }

    public boolean isFull() {
        return passengers.size() >= route.getAircraft().getCapacity();
    }

    public void addPassenger(Passenger p) {
        if (!isFull()) passengers.add(p);
    }

    public Route getRoute() { return route; }
    public LocalDate getDate() { return date; }
    public List<Passenger> getPassengers() { return passengers; }
}
