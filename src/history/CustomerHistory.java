package history;

import customer.Customer;
import room.Room;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomerHistory {
    private Customer customer;
    private Map<Room, Set<ReserveDuration>> customerSetMap;

    public CustomerHistory(Customer customer) {
        this.customer = customer;
        this.customerSetMap = new HashMap<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean addRoomDuration(Room room, ReserveDuration reserveDuration) {
        if(!customerSetMap.containsKey(room)){
            customerSetMap.put(room,new HashSet<>());
        }
        Set<ReserveDuration> roomDurationSet = customerSetMap.get(room);
        if(roomDurationSet.add(reserveDuration)){
            System.out.println("The Reserved is done successfully");
            return true;
        }
        System.out.println("That time is already saved");
        return false;
    }

}
