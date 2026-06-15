import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HomeHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String response = """
            <html><body>
            <h1>Welcome to Dairy Flat Airline</h1>
            <p>Use <code>/flights?date=YYYY-MM-DD</code> to see flights</p>
            </body></html>
        """;
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

