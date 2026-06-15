import java.util.List;
import java.util.ArrayList;

public class DataSetup {
    public static List<Route> createRoutes() {
        List<Route> routes = new ArrayList<>();

        Aircraft syberJet = new Aircraft("SyberJet SJ30i", 6);
        Aircraft cirrus1 = new Aircraft("Cirrus SF50 - 1", 4);
        Aircraft cirrus2 = new Aircraft("Cirrus SF50 - 2", 4);
        Aircraft honda1 = new Aircraft("HondaJet Elite 1", 5);
        Aircraft honda2 = new Aircraft("HondaJet Elite 2", 5);

        // Prices are just example values
        routes.add(new Route("Dairy Flat", "Melbourne", List.of("Friday"), syberJet, 650.00));
        routes.add(new Route("Melbourne", "Dairy Flat", List.of("Sunday"), syberJet, 620.00));

        routes.add(new Route("Dairy Flat", "Rotorua", List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"), cirrus1, 120.00));
        routes.add(new Route("Rotorua", "Dairy Flat", List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"), cirrus1, 110.00));

        routes.add(new Route("Dairy Flat", "Claris", List.of("Monday", "Wednesday", "Friday"), cirrus2, 80.00));
        routes.add(new Route("Claris", "Dairy Flat", List.of("Tuesday", "Thursday", "Saturday"), cirrus2, 75.00));

        routes.add(new Route("Dairy Flat", "Tuuta", List.of("Tuesday", "Friday"), honda1, 300.00));
        routes.add(new Route("Tuuta", "Dairy Flat", List.of("Wednesday", "Saturday"), honda1, 290.00));

        routes.add(new Route("Dairy Flat", "Tekapo", List.of("Monday"), honda2, 260.00));
        routes.add(new Route("Tekapo", "Dairy Flat", List.of("Tuesday"), honda2, 250.00));

        return routes;
    }
}

