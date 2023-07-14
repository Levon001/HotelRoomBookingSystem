package hotel;

import customer.Customer;
import history.CustomerHistory;
import history.HistoryBookingRoom;
import history.ReserveDuration;
import room.Room;
import room.RoomType;

import java.io.*;
import java.util.*;

public class Hotel implements Serializable {
    private List<HistoryBookingRoom> historyBookingRoomList;
    private List<CustomerHistory> customerHistoryList;

    public Hotel() {
        this.historyBookingRoomList = new ArrayList<>();
        this.customerHistoryList = new ArrayList<>();
    }

    public void addRoom(Room room) {
        historyBookingRoomList.add(new HistoryBookingRoom(room));
    }

    public void addCustomer(Customer customer) {
        customerHistoryList.add(new CustomerHistory(customer));
    }

    public boolean reserveRoom(Customer customer, ReserveDuration reserveDuration, RoomType roomType) {
        Optional<HistoryBookingRoom> searchRoom = searchFreeRoom(roomType, reserveDuration);
        if (searchRoom.isEmpty()) {
            return false;
        }

        Optional<CustomerHistory> findedCustomer = searchCustomer(customer);
        if (findedCustomer.isEmpty()) {
            return false;
        }
        searchRoom.get().addReservedHistory(reserveDuration);
        findedCustomer.get().addRoomDuration(searchRoom.get().getRoom(), reserveDuration);
        System.out.println(generateBill(findedCustomer.get(), searchRoom.get(), reserveDuration));
        return true;
    }

    private Optional<HistoryBookingRoom> searchFreeRoom(RoomType roomType, ReserveDuration reserveDuration) {
        return historyBookingRoomList.stream()
                .filter(room -> room.getRoom().getRoomType().equals(roomType))
                .filter(finding -> finding.checkingDate(reserveDuration))
                .findFirst();
    }

    private Optional<CustomerHistory> searchCustomer(Customer customer) {
        return customerHistoryList.stream().filter(val -> val.getCustomer().equals(customer)).findFirst();
    }


    public Customer findCustomer(String email) {
        for (CustomerHistory customerHistory : customerHistoryList) {
            if (customerHistory.getCustomer().getEmail().equals(email)) {
                return customerHistory.getCustomer();
            }
        }
        return null;
    }


    private String generateBill(CustomerHistory foundedCustomer, HistoryBookingRoom room, ReserveDuration startEndDateTime) {
        String context = "Consumer Name: " + foundedCustomer.getCustomer().getName() + " " +
                "Email : " + foundedCustomer.getCustomer().getEmail() + " " + "Room Number: " + room.getRoom().getRoomNumber() + " " +
                "Room price: " + room.getRoom().getPrice() + " " + "Time book" + startEndDateTime.toString();
        return context;
    }

    public void generateReport() {
        Scanner scanner = new Scanner(System.in);
        int roomNumber = inputRoomNumber(scanner);
        String context = report(roomNumber);
        System.out.print("Enter file name to save the report: ");
        scanner.nextLine();
        String fileName = scanner.nextLine();
        fileName += ".txt";
        System.out.println(context);
        try (Writer writer = new FileWriter(fileName)) {
            writer.write(context);
            System.out.println("Report generated successfully.");
        } catch (IOException e) {
            System.out.println("Failed to generate report.");
        }
    }

    private String report(int roomNumber) {
        String context = " ";
        for (CustomerHistory customerHistory : customerHistoryList) {
            for (Room room : customerHistory.getCustomerSetMap().keySet()) {
                if (room.getRoomNumber() == roomNumber) {
                    context = "Customer name " + customerHistory.getCustomer().getName() + " "
                            + ":Room number " + roomNumber + " " + customerHistory.getCustomerSetMap().get(room).toString() + "\n";
                }
            }
        }
        return context;
    }

    private int inputRoomNumber(Scanner scanner) {
        int roomNumber;
        while (true) {
            try {
                System.out.print("Enter room number: ");
                roomNumber = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter an integer.");
            }
        }
        return roomNumber;
    }

    public void serializeHotel(String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(this);
            System.out.println("Hotel object serialized and saved to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static method to deserialize Hotel object from a file
    public static Hotel deserializeHotel(String filePath) {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            Hotel hotel = (Hotel) objectIn.readObject();
            System.out.println("Hotel object deserialized from file: " + filePath);
            return hotel;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
