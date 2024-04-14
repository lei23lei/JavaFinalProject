import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Search implements SearchInterface {
    private String departure_id;
    private String arrival_id;
    private String outbound_date;
    private final String apiKey = "b7747dbc2ea3ea4b597fe18e5a799bbc895a3bed271caeb4349a26c3eef9db95";

    public Search(String departure_id, String arrival_id, String outbound_date) {
        this.departure_id = departure_id;
        this.arrival_id = arrival_id;
        this.outbound_date = outbound_date;
    }

    @Override
    public String getDepartureId() {
        return departure_id;
    }

    @Override
    public String getArrivalId() {
        return arrival_id;
    }

    @Override
    public String getOutboundDate() {
        return outbound_date;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public Boolean performSearch() {
        String url = "https://serpapi.com/search.json?engine=google_flights&departure_id=" + departure_id
                + "&arrival_id=" + arrival_id + "&outbound_date=" + outbound_date + "&type=2" + "&api_key=" + apiKey;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            String keyToSearch = "best_flights";

            if (jsonObject.has(keyToSearch)) {
                JsonElement value = jsonObject.get(keyToSearch);
                if (value.isJsonArray()) {
                    JsonArray array = value.getAsJsonArray();
                    if (array.size() > 0) {
                        for (int i = 0; i < array.size(); i++) {
                            JsonObject element = array.get(i).getAsJsonObject();

                            JsonObject firstFlight = element.getAsJsonArray("flights").get(0).getAsJsonObject();
                            JsonObject departureAirport = firstFlight.getAsJsonObject("departure_airport");

                            String departureAirportName = departureAirport.get("name").getAsString();
                            String departureAirportTime = departureAirport.get("time").getAsString();
                            String travelClass = firstFlight.get("travel_class").getAsString();
                            String flightNumber = firstFlight.get("flight_number").getAsString();
                            String price = element.get("price").getAsString();
                            String totalDuration = element.get("total_duration").getAsString();

                            System.out.println();
                            System.out.println(i + 1 + ". Flight:");
                            System.out.println("Departure airport name: " + departureAirportName);
                            System.out.println("Departure airport time: " + departureAirportTime);
                            System.out.println("Travel class: " + travelClass);
                            System.out.println("Flight number: " + flightNumber);
                            System.out.println("Price: " + price + " USD");
                            System.out.println("Total duration: " + totalDuration);
                            System.out.println();
                        }
                        // Return true after all flights have been processed
                        return true;
                    } else {
                        System.out.println("Array " + keyToSearch + " is empty.");
                        return false;
                    }
                } else {
                    System.out.println("Value of " + keyToSearch + " is not an array.");
                    return false;
                }
            } else {
                System.out.println("no flights found");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] addFlight(int index) {
        String url = "https://serpapi.com/search.json?engine=google_flights&departure_id=" + departure_id
                + "&arrival_id=" + arrival_id + "&outbound_date=" + outbound_date + "&type=2" + "&api_key=" + apiKey;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            String keyToSearch = "best_flights";

            if (jsonObject.has(keyToSearch)) {
                JsonElement value = jsonObject.get(keyToSearch);
                if (value.isJsonArray()) {
                    JsonArray array = value.getAsJsonArray();
                    if (array.size() > 0) {
                        JsonObject element = array.get(index - 1).getAsJsonObject();
                        JsonObject firstFlight = element.getAsJsonArray("flights").get(0).getAsJsonObject();
                        JsonObject departureAirport = firstFlight.getAsJsonObject("departure_airport");

                        String departureAirportName = departureAirport.get("name").getAsString();
                        String departureAirportTime = departureAirport.get("time").getAsString();
                        String travelClass = firstFlight.get("travel_class").getAsString();
                        String flightNumber = firstFlight.get("flight_number").getAsString();
                        String price = element.get("price").getAsString();
                        String totalDuration = element.get("total_duration").getAsString();
                        String type = "one-way";
                        String returnDate = "unknown";

                        // declare a string array to store the flight details
                        String[] flightDetails = new String[9];
                        flightDetails[0] = departureAirportName;
                        flightDetails[1] = departureAirportTime;
                        flightDetails[2] = travelClass;
                        flightDetails[3] = flightNumber;
                        flightDetails[4] = price;
                        flightDetails[5] = totalDuration;
                        flightDetails[6] = type;
                        flightDetails[7] = returnDate;
                        flightDetails[8] = "false";

                        return flightDetails;

                    } else {
                        System.out.println("Array " + keyToSearch + " is empty.");
                        return null;
                    }
                } else {
                    System.out.println("Value of " + keyToSearch + " is not an array.");
                    return null;
                }
            } else {
                System.out.println("Key " + keyToSearch + " not found.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
