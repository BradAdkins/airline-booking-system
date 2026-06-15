import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FlightsHandler implements HttpHandler {
    private final List<Route> routes;

    public FlightsHandler(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        Map<String, String> queryParams = Utils.queryToMap(requestURI.getQuery());

        String response;

        if (!queryParams.containsKey("date")) {
            response = "Missing ?date=YYYY-MM-DD";
            exchange.sendResponseHeaders(400, response.length());
        } else {
            LocalDate date = LocalDate.parse(queryParams.get("date"));
            List<Flight> flights = FlightScheduler.getFlightsForDate(date, routes);

            StringBuilder html = new StringBuilder("<html><body><h2>Flights on " + date + "</h2><ul>");
            for (Flight f : flights) {
                html.append("<li>")
                        .append(f.getRoute().getFrom()).append(" -> ")
                        .append(f.getRoute().getTo()).append(" | $")
                        .append(f.getRoute().getPrice())
                        .append("</li>");
            }
            html.append("</ul></body></html>");
            response = html.toString();
            exchange.sendResponseHeaders(200, response.length());
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
