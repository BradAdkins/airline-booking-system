public class Booking {
    private String reference;
    private Flight flight;
    private Passenger passenger;

    public Booking(String reference, Flight flight, Passenger passenger) {
        this.reference = reference;
        this.flight = flight;
        this.passenger = passenger;
    }

    public String getReference() { return reference; }
    public Flight getFlight() { return flight; }
    public Passenger getPassenger() { return passenger; }
}
