package w2051819_CinemaManagement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CinemaManagement {
    static Ticket[] tickets = new Ticket[48];
    //Create a 2D array with 3 rows and 16 columns
    static int[][] rows = new int[3][16];

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                rows[i][j] = 0;
            }
        }
        Scanner input = new Scanner(System.in);
        int userOption = 1;
        while (userOption != 0) {
            System.out.println("Welcome to the London Lumiere");
            System.out.println("*****************************");
            System.out.println("         MENU OPTIONS        ");
            System.out.println("*****************************");
            System.out.println("Please select an option:     ");

            String[] menu = {
                    "1) Buy a ticket",
                    "2) Cancel a ticket",
                    "3) See seating plan",
                    "4) Find first seat available",
                    "5) Print tickets information and total price",
                    "6) Search ticket",
                    "7) Sort tickets by price",
                    "8) Exit"
            };
            //Printing the menu.
            for (String temp : menu) {
                System.out.println(temp);
            }
            System.out.println("*****************************");
            System.out.println("Select option:               ");
            try {
                userOption = input.nextInt();
                switch (userOption) {
                    case 1:
                        buy_ticket();
                        break;
                    case 2:
                        cancel_ticket();
                        break;
                    case 3:
                        print_seating_area(tickets, rows);
                        break;
                    case 4:
                        find_first_available();
                        break;
                    case 5:
                        print_ticket_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 7:
                        sort_tickets();
                        break;
                    case 8:
                        System.out.println("Exiting the program...... ");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please try again. ");
                }
            } catch (java.util.InputMismatchException e) {
                // Clear the invalid input from the scanner
                input.next();
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
        input.close(); //Close the scanner when done.
    }

    public static void buy_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row number:");
        String rowNumber = input.next().toUpperCase();

        if (!rowNumber.matches("[1-3]")) {
            System.out.println("Row number must be between 1 and 3.");
            return; // Exits the method early if the row letter is invalid.
        }

        System.out.println("Enter the seat number:");
        while (!input.hasNextInt()) {
            System.out.println("Invalid seat number.");
            input.next(); // Consume the non-integer input
            return;
        }
        int seatNumber = input.nextInt();
        if (seatNumber <= 0 || seatNumber > 16) {
            System.out.println("Seat number must be between 1 and 16.");
            return; // Exits the method early if the seat number is invalid.
        }

        int rowIndex = Integer.parseInt(rowNumber) - 1;

        if (rows[rowIndex][seatNumber - 1] == 0) {
            rows[rowIndex][seatNumber - 1] = 1;
        } else {
            System.out.println("Sorry, the seat is not available. Please choose another seat.");
            return;
        }

        // Collect Person details and create a Ticket
        System.out.println("Enter name:");
        String name = input.next();
        System.out.println("Enter surname:");
        String surname = input.next();
        System.out.println("Enter email:");
        String email = input.next();

        double price = calculatePrice(rowNumber, seatNumber);
        Person person = new Person(name, surname, email);
        Ticket ticket = new Ticket(rowNumber, seatNumber, price, person);

        // Attempt to book a seat
        boolean seatBooked = false;
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] == null) {
                tickets[i] = ticket;
                System.out.println("Seat booked successfully.");
                ticket.save(); // Save ticket to a file
                seatBooked = true;
                break;
            }
        }
        if (!seatBooked) {
            System.out.println("Sorry, no available seats.");
        }
    }

    public static double calculatePrice(String row, int seatNumber) {
        double price = 0.0;
        switch (row) {
            case "1":
                price = 12.0;
                break;
            case "2":
                price = 10.0;
                break;
            case "3":
                price = 8.0;
                break;
            default:
                System.out.println("Invalid row number. Price set to 0.");
                break;
        }
        return price;
    }

    public static void cancel_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row number:");
        String rowNumber = input.next().toUpperCase();

        if (!rowNumber.matches("[1-3]")) {
            System.out.println("Row number must be between 1 and 3.");
            return; // Exits the method early if the row letter is invalid.
        }

        System.out.println("Enter the seat number:");
        while (!input.hasNextInt()) {
            System.out.println("Invalid seat number.");
            input.next(); // Consume the non-integer input
            return;
        }
        int seatNumber = input.nextInt();
        if (seatNumber <= 0 || seatNumber > 16) { // Assuming all rows have the same max length as Row1 for simplification
            System.out.println("Seat number must be between 1 and 16.");
            return; // Exits the method early if the seat number is invalid.
        }

        int rowIndex = Integer.parseInt(rowNumber) - 1;

        if (rows[rowIndex][seatNumber - 1] == 1) {
            rows[rowIndex][seatNumber - 1] = 0;
            System.out.println("Seat cancelled successfully.");
        } else {
            System.out.println("seat is free.");
            return;
        }
    }

    public static void print_seating_area(Ticket[] ticket, int[][] rows) {
        System.out.println("*****************");
        System.out.println("*    SCREEN     *");
        System.out.println("*****************");

        // Print the 2D array with a gap in the middle
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 16; j++) {
                if (j == 8) {
                    System.out.print(" ");  // Add a space in the middle
                }
                if(rows[i][j] == 0 ){
                    System.out.print("O");
                }
                else{
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    public static void find_first_available() {
        boolean seatFound = false;

        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length; col++) {
                if (rows[row][col] == 0) {
                    System.out.println("Seat found.");
                    System.out.println("First available seat is " + (col + 1) + " at Row " + (row + 1) + ".");
                    seatFound = true;
                    break;
                }
            }
            if (seatFound) {
                break;
            }
        }

        if (!seatFound) {
            System.out.println("No available seats found.");
        }

    }

    public static void print_ticket_info() {
        double totalSales = 0;
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.printTicketInfo();
                totalSales += ticket.getPrice();
            }
        }
        System.out.println("Total sales: £" + totalSales);

    }

    public static void search_ticket() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the row number:");
        String rowNumber = input.next().toUpperCase();
        System.out.println("Enter the seat number:");
        int seatNumber = input.nextInt();

        boolean found = false;
        for (Ticket ticket : tickets) {
            if (ticket != null && ticket.getRow().equals(rowNumber) && ticket.getSeat() == seatNumber) {
                System.out.println("This seat is sold.");
                System.out.println("<--Ticket details-->");
                ticket.printTicketInfo();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("This seat is available.");
        }

    }

    public static void sort_tickets() {
        for (int i = 0; i < tickets.length - 1; i++) {
            for (int j = 0; j < tickets.length - 1; j++) {
                if (tickets[j] != null && tickets[j + 1] != null && tickets[j].getPrice() > tickets[j = 1].getPrice()) {
                    //swap tickets[j] and tickets[j=1]
                    Ticket temp = tickets[j];
                    tickets[j] = tickets[j + 1];
                    tickets[j + 1] = temp;
                }
            }
        }

        //Price sorted tickets information
        System.out.println("sorted tickets by price: ");
        for (Ticket ticket : tickets) {
            if (ticket != null) {
                ticket.printTicketInfo();
            }
        }

    }

}