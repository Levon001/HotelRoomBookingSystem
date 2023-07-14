package history;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import room.Room;

public class HistoryBookingRoom implements Serializable {
    private Room room;
    private List<ReserveDuration> reserveDurationList;

    public HistoryBookingRoom(Room room) {
        this.room = room;
        reserveDurationList = new ArrayList<>();
    }

    public Room getRoom() {
        return room;
    }

    public boolean addReservedHistory(ReserveDuration reserveDuration) {
        if (checkingDate(reserveDuration)) {
            reserveDurationList.add(reserveDuration);
            return true;
        }
        System.out.println("The time is already scheduled");
        return false;
    }

    public boolean checkingDate(ReserveDuration reserveDuration) {
        if (reserveDurationList.isEmpty()) {
            return true;
        }
        for (ReserveDuration duration : reserveDurationList) {
            if (checkDate(duration, reserveDuration)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDate(ReserveDuration current, ReserveDuration input) {
        LocalDate date1St = current.getStartDate();
        LocalDate date1End = current.getEndDate();

        LocalDate date2St = input.getStartDate();
        LocalDate date2End = input.getEndDate();

        boolean check1 = date1St.isBefore(date2End) && date1End.isAfter(date2St);
        boolean check2 = date1St.isBefore(date2St) && date1End.isAfter(date2St);
        boolean check3 = date1St.isAfter(date2St) && date1St.isBefore(date2End);

        return !(check1 || check2 || check3);
    }

    public List<ReserveDuration> getReserveDurationList() {
        return reserveDurationList;
    }
}
