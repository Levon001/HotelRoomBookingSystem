package room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private static int ID;
    private List<Furniture> furnitures;
    private RoomType roomType;

    private double price;
    private int roomNumber;

    public Room(List<Furniture> furnitures,RoomType roomType) {
        this.furnitures = new ArrayList<>();
        this.roomType = roomType;
        this.price = takeOutPrice(roomType);
        this.roomNumber = ID++;
    }

    private double takeOutPrice(RoomType roomType) {
        return switch (roomType) {
            case DELUXE_ROOM -> 55;
            case DOUBLE_ROOM -> 35;
            case SINGLE_ROOM -> 20;
        };
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Double.compare(room.price, price) == 0 && roomNumber == room.roomNumber && Objects.equals(furnitures, room.furnitures) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(furnitures, roomType, price, roomNumber);
    }
}
