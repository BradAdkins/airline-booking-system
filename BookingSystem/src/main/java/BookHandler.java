import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BookHandler implements HttpHandler {
    private final List<Route> routes;

    public BookHandler(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, 0);
            return;
        }

        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map<String, String> params = Utils.queryToMap(URLDecoder.decode(formData, "UTF-8"));

        String from = params.get("from");
        String to = params.get("to");
        String dateStr = params.get("date");
        String name = params.get("name");

        Route selectedRoute = null;
        for (Route r : routes) {
            if (r.getFrom().equals(from) && r.getTo().equals(to)) {
                selectedRoute = r;
                break;
            }
        }

        String response;
        if (selectedRoute == null || name == null || dateStr == null) {
            response = "Missing or invalid parameters.";
            exchange.sendResponseHeaders(400, response.length());
        } else {
            LocalDate date = LocalDate.parse(dateStr);
            Flight flight = FlightScheduler.getOrCreateFlight(date, selectedRoute);

            Booking booking = BookingManager.createBooking(flight, name);
            if (booking == null) {
                response = "Flight is full.";
                exchange.sendResponseHeaders(409, response.length());
            } else {
                response = "Booking successful. Reference: " + booking.getReference();
                exchange.sendResponseHeaders(200, response.length());
            }
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
