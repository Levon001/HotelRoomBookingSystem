

import customer.Customer;
import history.CustomerHistory;
import history.ReserveDuration;
import history.HistoryBookingRoom;
import hotel.Hotel;
import room.Furniture;
import room.Room;
import room.RoomType;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Hotel hotel = new Hotel();

    public static void main(String[] args) {

        String filePath = "hotel.ser";


        hotel = Hotel.deserializeHotel(filePath);

        Scanner scanner = new Scanner(System.in);

        if (hotel != null) {
            int choice;

            do {
                System.out.println("1. Add a room");
                System.out.println("2. Add a customer");
                System.out.println("3. Book a room");
                System.out.println("4. Generate report for a room");
                System.out.println("0. Exit");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        addRoom(scanner);
                        break;
                    case 2:
                        addCustomer(scanner);
                        break;
                    case 3:
                        bookRoom(scanner);
                        break;
                    case 4:
                        hotel.generateReport();
                        break;
                    case 0:
                        hotel.serializeHotel(filePath);
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                System.out.println();
            } while (choice != 0);

            scanner.close();
        }
    }

    private static void addRoom(Scanner scanner) {
        RoomType roomType;
        while (true) {
            System.out.print("Enter room type (SINGLE_ROOM, DOUBLE_ROOM, DELUXE_ROOM): ");
            String roomTypeStr = scanner.nextLine();
            try {
                roomType = RoomType.valueOf(roomTypeStr);
                break;
            } catch (IllegalArgumentException e) {
            }
        }


        List<Furniture> furnitureList = new ArrayList<>();
        char addFurnitureChoice;
        do {
            System.out.print("Enter furniture name: ");
            String furnitureName = scanner.nextLine();

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            furnitureList.add(new Furniture(furnitureName, quantity));

            System.out.print("Add another furniture? (y/n): ");
            addFurnitureChoice = scanner.nextLine().charAt(0);
        } while (addFurnitureChoice == 'y' || addFurnitureChoice == 'Y');

        Room room = new Room(furnitureList, roomType);
        hotel.addRoom(room);
        System.out.println("Room added successfully.");
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(name, email);
        hotel.addCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    private static void bookRoom(Scanner scanner) {
        System.out.print("Enter customer email: ");
        String name = scanner.nextLine();

        Customer customer = hotel.findCustomer(name);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        RoomType roomType;
        while (true) {
            System.out.print("Enter room type (SINGLE_ROOM, DOUBLE_ROOM, DELUXE_ROOM): ");
            String roomTypeStr = scanner.nextLine();
            try {
                roomType = RoomType.valueOf(roomTypeStr);
                break;
            } catch (IllegalArgumentException e) {
            }
        }

        LocalDate startDate;
        LocalDate endDate;
        while (true) {

            try {
                System.out.print("Enter start date (yyyy-mm-dd): ");
                String startDateStr = scanner.nextLine();
                startDate = LocalDate.parse(startDateStr);


                System.out.print("Enter end date (yyyy-mm-dd): ");
                String endDateStr = scanner.nextLine();
                endDate = LocalDate.parse(endDateStr);
                break;
            } catch (DateTimeException e) {
            }
        }

        ReserveDuration reserveDuration;
        try {
            reserveDuration = ReserveDuration.checkingDate(startDate, endDate);
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
            return;
        }


        boolean room = hotel.reserveRoom(customer, reserveDuration, roomType);
        if (!room) {
            System.out.println("No available rooms of the specified type.");
            return;
        }

        System.out.println("Room booked successfully.");
    }

}
