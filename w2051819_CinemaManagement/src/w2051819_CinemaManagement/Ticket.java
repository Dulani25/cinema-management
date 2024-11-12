package w2051819_CinemaManagement;
import java.io.FileNotFoundException;
import java.io.PrintWriter ;
import java.util.*;
public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters
    public String getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    // Setters
    public void setRow(String row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printTicketInfo() {
        System.out.println("Ticket for row: " + row);
        System.out.println("Ticket for seat: " + seat);
        System.out.println("Price: £" + price);
        person.printInfo();
    }

    // Method to save ticket info to a file
    public void save() {
        String filename = row + seat + ".txt";
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.println("Seat: " + row + seat);
            writer.println("Price: £" + price);
            writer.println("Name: " + person.getName() + " " + person.getSurname());
            writer.println("Email: " + person.getEmail());
            writer.close();
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        }
    }

}
