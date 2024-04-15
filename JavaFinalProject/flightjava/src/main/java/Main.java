import java.util.Scanner;

public class Main {
    // declare an array of strings to store the list[]
    static String myTickets[][] = new String[11][8];
    static Scanner input = new Scanner(System.in);
    static int attempt = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the flight booking system!");
        while (true) {
            System.out.println();
            System.out.println("Enter 1 for search");
            System.out.println("Enter 2 for manage tickets");
            System.out.println("Enter 3 for exit");
            input = new Scanner(System.in);
            try {
                int choice = input.nextInt();
                if (choice == 1) {
                    bookFlight();
                } else if (choice == 2) {
                    manageTickets();
                } else if (choice == 3) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }

    }

    public static void bookFlight() {
        System.out.println();
        System.out.println("Enter 1 for one-way or 2 for round-trip: ");
        input = new Scanner(System.in);
        int type = input.nextInt();
        if (type == 1) {
            System.out.println("Enter departure airport code: ");
            input = new Scanner(System.in);
            String departureId = input.nextLine().toUpperCase();
            System.out.println("Enter arrival airport code: ");
            input = new Scanner(System.in);
            String arrivalId = input.nextLine().toUpperCase();
            System.out.println("Enter outbound date (YYYY-MM-DD): ");
            input = new Scanner(System.in);
            String outboundDate = input.nextLine();
            if (oneWay(departureId, arrivalId, outboundDate, attempt) == true) {
                attempt++;
            }
        } else if (type == 2) {
            System.out.println("Enter departure airport code: ");
            input = new Scanner(System.in);
            String departureId = input.nextLine().toUpperCase();
            System.out.println("Enter arrival airport code: ");
            input = new Scanner(System.in);
            String arrivalId = input.nextLine().toUpperCase();
            System.out.println("Enter outbound date (YYYY-MM-DD): ");
            input = new Scanner(System.in);
            String outboundDate = input.nextLine();
            System.out.println("Enter return date (YYYY-MM-DD): ");
            input = new Scanner(System.in);
            String returnDate = input.nextLine();
            if (roundTrip(departureId, arrivalId, outboundDate, returnDate, attempt) == true) {
                attempt++;
            }
        } else {
            System.out.println("Invalid input");
        }
    }

    public static Boolean oneWay(String departureId, String arrivalId, String outboundDate, int attempt) {
        Search search = new Search(departureId, arrivalId, outboundDate);
        if (search.performSearch() == true) {
            System.out.println("input which flight you want to book (e.g. 1 for the first flight) ");
            System.out.println("or enter 0 to cancel to go back: ");
            input = new Scanner(System.in);
            int index = input.nextInt();
            if (index == 0) {
                return false;
            }
            String list[] = search.addFlight(index);
            myTickets[attempt] = list;
            System.out.println("Ticket booked!");
            System.out.println();
            return true;
        } else {
            return false;
        }
    }

    public static Boolean roundTrip(String departureId, String arrivalId, String outboundDate, String returnDate,
            int attempt) {
        Search search = new SearchRoundTrip(departureId, arrivalId, outboundDate, returnDate);
        if (search.performSearch() == true) {
            System.out.println("input which flight you want to book (e.g. 1 for the first flight) ");
            System.out.println("or enter 0 to cancel to go back: ");
            input = new Scanner(System.in);
            int index = input.nextInt();
            if (index == 0) {
                return false;
            }
            String list[] = search.addFlight(index);
            myTickets[attempt] = list;
            System.out.println("Ticket booked!");
            System.out.println();
            return true;
        } else {
            return false;
        }
    }

    public static void manageTickets() {
        double totalPrice = 0;
        System.out.println();
        for (int i = 0; i < myTickets.length; i++) {
            if (myTickets[i][0] != null) {
                if (myTickets[i][8].equals("false")) {
                    totalPrice += Double.parseDouble(myTickets[i][4]);
                }
                System.out.println("Ticket " + (i + 1) + ":");
                System.out.println("Departure airport name: " + myTickets[i][0]);
                System.out.println("Departure airport time: " + myTickets[i][1]);
                System.out.println("Travel class: " + myTickets[i][2]);
                System.out.println("Flight number: " + myTickets[i][3]);
                System.out.println("Price: " + myTickets[i][4]);
                System.out.println("Total duration: " + myTickets[i][5]);
                System.out.println("Type: " + myTickets[i][6]);
                System.out.println("Return date: " + myTickets[i][7]);
                System.out.println("paid: " + myTickets[i][8]);
                System.out.println();
            }
        }

        System.out.println("Total price: " + totalPrice);
        System.out.println();

        System.out.println("Enter 1 to pay for tickets");
        System.out.println("Enter 2 to cancel a ticket");
        System.out.println("Enter other to go back");
        input = new Scanner(System.in);
        int choice = input.nextInt();
        if (choice == 1) {
            System.out.println();
            System.out.println("Total price: " + totalPrice);
            if (totalPrice == 0) {
                System.out.println("No tickets to pay for");
                return;
            } else {
                System.out.println("Enter card number: ");
                input = new Scanner(System.in);
                String cardNumber = input.nextLine();
                for (int i = 0; i < myTickets.length; i++) {
                    if (myTickets[i][0] != null) {
                        myTickets[i][8] = "true";
                    }
                }
                System.out.println("Tickets paid for!");
                System.out.println();
            }

        } else if (choice == 2) {
            System.out.println("Enter ticket number to cancel: ");
            input = new Scanner(System.in);
            int ticketNumber = input.nextInt();
            // remove the ticket from the list
            myTickets[ticketNumber - 1] = new String[8];
            System.out.println("Ticket cancelled!");
        } else {
            return;
        }

    }
}
