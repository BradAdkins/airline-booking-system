import java.util.List;

public class Route {
    private String from;
    private String to;
    private List<String> operatingDays;
    private Aircraft aircraft;
    private double price;

    public Route(String from, String to, List<String> operatingDays, Aircraft aircraft, double price) {
        this.from = from;
        this.to = to;
        this.operatingDays = operatingDays;
        this.aircraft = aircraft;
        this.price = price;
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }
    public List<String> getOperatingDays() { return operatingDays; }
    public Aircraft getAircraft() { return aircraft; }
    public double getPrice() { return price; }
}

