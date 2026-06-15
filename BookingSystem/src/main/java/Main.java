import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Route> routes = DataSetup.createRoutes();

        BookingManager.loadBookings(routes);

        WebServer.start(routes);
    }
}




