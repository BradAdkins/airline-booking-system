public class Passenger {
    private String name;
    private String bookingRef;

    public Passenger(String name, String bookingRef) {
        this.name = name;
        this.bookingRef = bookingRef;
    }

    public String getName() { return name; }
    public String getBookingRef() { return bookingRef; }
}
