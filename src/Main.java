//import customer.Customer;
//import history.ReserveDuration;
//import hotel.Hotel;
//import room.Furniture;
//import room.Room;
//import room.RoomType;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Scanner;
//
//// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
//// then press Enter. You can now see whitespace characters in your code.
//public class Main {
//    public static void main(String[] args) {
//
//        Hotel hotel1 = new Hotel();
//        Furniture prop1 = new Furniture("prop", 2);
//        Furniture prop2 = new Furniture("prop", 6);
//        Room room = new Room(Arrays.asList(prop1, prop2), RoomType.SINGLE_ROOM);
//        Customer consumer = new Customer("name", "email");
//        hotel1.addCustomer(consumer);
//
//
//        LocalDate startDate1 = LocalDate.of(2023, 7, 15);
//        LocalDate endDate1 = LocalDate.of(2023, 7, 17);
//
//        LocalDate startDate2 = LocalDate.of(2023, 7, 16);
//        LocalDate endDate2 = LocalDate.of(2023, 7, 18);
//        ReserveDuration startEndDate1 = ReserveDuration.checkingDate(startDate1, endDate1);
//        ReserveDuration startEndDate2 = ReserveDuration.checkingDate(startDate2, endDate2);
//
//        hotel1.reserveRoom(consumer, startEndDate1, RoomType.SINGLE_ROOM);
//
//
//        Scanner scanner = new Scanner(System.in);
//        boolean exit = false;
//
//        while (!exit) {
//            System.out.println("Welcome to the Hotel Room entities.Booking System!");
//            System.out.println("Please select an option:");
//            System.out.println("1. Add a room");
//            System.out.println("2. Add a customer");
//            System.out.println("3. Book a room");
//            System.out.println("4. Generate bill");
//            System.out.println("5. Generate report");
//            System.out.println("6. Save system state");
//            System.out.println("7. Load system state");
//            System.out.println("8. Exit");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume newline character
//
//            switch (choice) {
//                case 1:
//                    System.out.println("Enter room ID:");
//                    int roomId = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    System.out.println("Enter room type (Single, Double, Deluxe):");
//                    String roomType = scanner.nextLine();
//
//                    hotel1.addRoom(roomType);
//                    break;
//                case 2:
//                    System.out.println("Enter customer ID:");
//                    int customerId = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    System.out.println("Enter customer name:");
//                    String name = scanner.nextLine();
//
//                    System.out.println("Enter customer email:");
//                    String email = scanner.nextLine();
//
//                    customerRegistration.addCustomer(customerId, name, email);
//                    break;
//                case 3:
//                    System.out.println("Enter customer ID:");
//                    int bookingCustomerId = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    System.out.println("Enter room type (Single, Double, Deluxe):");
//                    String bookingRoomType = scanner.nextLine();
//
//                    System.out.println("Enter start date (yyyy-MM-dd):");
//                    String startDateStr = scanner.nextLine();
//                    Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
//
//                    System.out.println("Enter end date (yyyy-MM-dd):");
//                    String endDateStr = scanner.nextLine();
//                    Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);
//
//                    roomBooking.bookRoom(bookingRoomType, bookingCustomerId, startDate, endDate);
//                    break;
//                case 4:
//                    System.out.println("Enter booking ID:");
//                    int bookingId = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    System.out.println("Enter room type (Single, Double, Deluxe):");
//                    String billRoomType = scanner.nextLine();
//
//                    System.out.println("Enter file path to save the bill:");
//
//                    String billFilePath = scanner.nextLine();
//                    billGenerator.generateBill(bookingId, billRoomType, billFilePath);
//                    break;
//                case 5:
//                    System.out.println("Enter room ID:");
//                    int roomIdForReport = scanner.nextInt();
//                    scanner.nextLine(); // Consume newline character
//
//                    System.out.println("Enter file path to save the report:");
//                    String reportFilePath = scanner.nextLine();
//
//                    reportsGenerator.generateReport(roomIdForReport, reportFilePath);
//                    break;
//                case 6:
//                    System.out.println("Enter file path to save the system state:");
//                    String saveFilePath = scanner.nextLine();
//
//                    systemStateManager.saveState(saveFilePath);
//                    break;
//                case 7:
//                    System.out.println("Enter file path to load the system state:");
//                    String loadFilePath = scanner.nextLine();
//
//                    systemStateManager.loadState(loadFilePath);
//                    break;
//                case 8:
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//                    break;
//            }
//        }
//    }
//}



import customer.Customer;
import history.CustomerHistory;
import history.ReserveDuration;
import history.HistoryBookingRoom;
import room.Furniture;
import room.Room;
import room.RoomType;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE_PATH = "data.txt";
    private static List<Customer> customers = new ArrayList<>();
    private static List<Room> rooms = new ArrayList<>();
    private static List<HistoryBookingRoom> bookingHistory = new ArrayList<>();

    public static void main(String[] args) {
        loadState();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Add a room");
            System.out.println("2. Add a customer");
            System.out.println("3. Book a room");
            System.out.println("4. Save state to file");
            System.out.println("5. Load state from file");
            System.out.println("6. Generate report for a room");
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
                    saveState();
                    break;
                case 5:
                    loadState();
                    break;
                case 6:
                    generateReport(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);

        scanner.close();
    }

    private static void addRoom(Scanner scanner) {
        System.out.print("Enter room type (SINGLE_ROOM, DOUBLE_ROOM, DELUXE_ROOM): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr);

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
        rooms.add(room);
        System.out.println("Room added successfully.");
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(name, email);
        customers.add(customer);
        System.out.println("Customer added successfully.");
    }

    private static void bookRoom(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        Customer customer = findCustomer(name);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.print("Enter room type (SINGLE_ROOM, DOUBLE_ROOM, DELUXE_ROOM): ");
        String roomTypeStr = scanner.nextLine();
        RoomType roomType = RoomType.valueOf(roomTypeStr);

        Room room = findAvailableRoom(roomType);
        if (room == null) {
            System.out.println("No available rooms of the specified type.");
            return;
        }

        System.out.print("Enter start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr);

        ReserveDuration reserveDuration;
        try {
            reserveDuration = ReserveDuration.checkingDate(startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        HistoryBookingRoom historyBookingRoom = findBookingHistory(room);
        if (historyBookingRoom == null) {
            historyBookingRoom = new HistoryBookingRoom(room);
            bookingHistory.add(historyBookingRoom);
        }

        if (historyBookingRoom.addReservedHistory(reserveDuration)) {
//            CustomerHistory customerHistory = findCustomerHistory(customer);
//            if (customerHistory == null) {
//                customerHistory = new CustomerHistory(customer);
//                customerSetMap.put(room, new HashSet<>());
//            }

//            customerHistory.addRoomDuration(room, reserveDuration);
            System.out.println("Room booked successfully.");

            double totalBill = calculateBill(room, reserveDuration);
            System.out.println("Total Bill: $" + totalBill);

            System.out.print("Save the bill in the file system? (y/n): ");
            char saveBillChoice = scanner.nextLine().charAt(0);
            if (saveBillChoice == 'y' || saveBillChoice == 'Y') {
                saveBillToFile(customer, room, reserveDuration, totalBill);
            }
        } else {
            System.out.println("The time is already scheduled.");
        }
    }

    private static void saveBillToFile(Customer customer, Room room, ReserveDuration reserveDuration, double totalBill) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("bill.txt", true))) {
            writer.println("Customer: " + customer.getName());
            writer.println("Email: " + customer.getEmail());
            writer.println("Room: " + room.getRoomNumber());
            writer.println("Reservation Period: " + reserveDuration.getStartDate() + " - " + reserveDuration.getEndDate());
            writer.println("Total Bill: $" + totalBill);
            writer.println();
            System.out.println("Bill saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save bill.");
        }
    }

    private static double calculateBill(Room room, ReserveDuration reserveDuration) {
        long days = reserveDuration.getStartDate().until(reserveDuration.getEndDate()).getDays();
        return room.getPrice() * days;
    }

    private static void saveState() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_PATH))) {
            outputStream.writeObject(customers);
            outputStream.writeObject(rooms);
            outputStream.writeObject(bookingHistory);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save state.");
        }
    }

    private static void loadState() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_PATH))) {
            customers = (List<Customer>) inputStream.readObject();
            rooms = (List<Room>) inputStream.readObject();
            bookingHistory = (List<HistoryBookingRoom>) inputStream.readObject();
            System.out.println("State loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing state found. Starting with an empty state.");
        }
    }

    private static void generateReport(Scanner scanner) {
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("Room not found.");
            return;
        }

        HistoryBookingRoom historyBookingRoom = findBookingHistory(room);
        if (historyBookingRoom == null) {
            System.out.println("No booking history found for the specified room.");
            return;
        }

        System.out.print("Enter file path to save the report: ");
        String filePath = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Room Number: " + room.getRoomNumber());
            writer.println("Room Type: " + room.getRoomType());
            writer.println("Booking History:");

            List<ReserveDuration> reserveDurationList = historyBookingRoom.getReserveDurationList();
            for (ReserveDuration reserveDuration : reserveDurationList) {
//                writer.println("Customer Name: " + reserveDuration.getCustomer().getName());
                writer.println("Booking Period: " + reserveDuration.getStartDate() + " - " + reserveDuration.getEndDate());
                writer.println();
            }

            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate report.");
        }
    }

    private static Customer findCustomer(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    private static Room findAvailableRoom(RoomType roomType) {
        for (Room room : rooms) {
            if (room.getRoomType() == roomType) {
                boolean isBooked = false;
                for (HistoryBookingRoom booking : bookingHistory) {
                    if (booking.getRoom().equals(room)) {
                        isBooked = true;
                        break;
                    }
                }
                if (!isBooked) {
                    return room;
                }
            }
        }
        return null;
    }

    private static Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private static HistoryBookingRoom findBookingHistory(Room room) {
        for (HistoryBookingRoom booking : bookingHistory) {
            if (booking.getRoom().equals(room)) {
                return booking;
            }
        }
        return null;
    }

//    private static CustomerHistory findCustomerHistory(Customer customer) {
//        for (HistoryBookingRoom booking : bookingHistory) {
//            if (booking.getRoom().equals(room)) {
//                return booking.getCustomerHistory(customer);
//            }
//        }
//        return null;
//    }
}
