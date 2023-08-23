import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> rooms = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(int roomNumber, RoomType type, boolean isAvailable) {
        Room room = new Room(roomNumber, type, true);
        this.rooms.add(room);
    }

    public boolean checkRoomAvailability(int roomNumber, LocalDate desiredCheckInDate) {
        Room room = getRoomByRoomNumber(roomNumber);
        if (room != null) {
            if (!room.isAvailable()) {
                return false;
            }
            /*for (Reservation reservation : room.getReservationList()) {
                if (desiredCheckInDate.isAfter(reservation.getCheckInDate())
                        && (desiredCheckInDate.isBefore(reservation.getCheckOutDate())
                        || desiredCheckInDate.isEqual(reservation.getCheckOutDate()))) {
                    return false;
                }
            }*/
            return true;
        }
        System.out.println("Room doesn't exist.");
        return false;
    }

    public Room getRoomByRoomNumber(int roomNumber) {
        for(Room room : this.getRooms()) {
            if(room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
