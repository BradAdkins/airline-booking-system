import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;

public class CancelHandler implements HttpHandler {
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
        String ref = params.get("ref");

        String response;
        if (ref == null) {
            response = "Missing booking reference.";
            exchange.sendResponseHeaders(400, response.length());
        } else {
            boolean success = BookingManager.cancelBooking(ref);
            if (success) {
                response = "Booking " + ref + " cancelled.";
                exchange.sendResponseHeaders(200, response.length());
            } else {
                response = "Booking not found.";
                exchange.sendResponseHeaders(404, response.length());
            }
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

