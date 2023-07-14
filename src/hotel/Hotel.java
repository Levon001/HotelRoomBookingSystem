package hotel;

import customer.Customer;
import history.CustomerHistory;
import history.HistoryBookingRoom;
import history.ReserveDuration;
import room.Room;
import room.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hotel {
    private List<HistoryBookingRoom> historyBookingRoomList;
    private List<CustomerHistory> customerHistoryList;

    public Hotel() {
        this.historyBookingRoomList = new ArrayList<>();
        this.customerHistoryList = new ArrayList<>();
    }

    public void addRoom(Room room){
        historyBookingRoomList.add(new HistoryBookingRoom(room));
    }

    public void addCustomer(Customer customer) {
        customerHistoryList.add(new CustomerHistory(customer));
    }

    public boolean reserveRoom(Customer customer, ReserveDuration reserveDuration, RoomType roomType) {
        Optional<HistoryBookingRoom> searchRoom = searchFreeRoom(roomType, reserveDuration);
        if(searchRoom.isEmpty()) {
            return false;
        }

        Optional<CustomerHistory> findedCustomer = searchCustomer(customer);
        if(findedCustomer.isEmpty()){
            return false;
        }
        searchRoom.get().addReservedHistory(reserveDuration);
        findedCustomer.get().addRoomDuration(searchRoom.get().getRoom(), reserveDuration);
        System.out.println(generateBill(findedCustomer.get(),searchRoom.get(),reserveDuration));
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

    private String generateBill(CustomerHistory foundedCustomer, HistoryBookingRoom room, ReserveDuration startEndDateTime) {
        String context = "Consumer Name: " + foundedCustomer.getCustomer().getName() + " " +
            "Email : " + foundedCustomer.getCustomer().getEmail() + " " + "Room Number: " + room.getRoom().getRoomNumber() + " " +
            "Room price: " + room.getRoom().getPrice() + " " + "Time book" + startEndDateTime.toString();
        return context;
    }


}
