public interface SearchInterface {
    String departure_id = "";
    String arrival_id = "";
    String outbound_date = "";

    String getDepartureId();

    String getArrivalId();

    String getOutboundDate();

    String getApiKey();

    Boolean performSearch();

    String[] addFlight(int index);

}
