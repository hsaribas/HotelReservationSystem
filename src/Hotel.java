import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private List<Room> roomList = new ArrayList<>();
    private final List<Guest> guestList = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(int roomNumber, RoomType type) {
        Room room = new Room(roomNumber, type, true);
        this.roomList.add(room);
    }

    public boolean checkRoomAvailability(int roomNumber, LocalDate desiredCheckInDate, LocalDate desiredCheckOutDate) {
        Room room = getRoomByRoomNumber(roomNumber);

        if (room != null) {
            if (!room.isAvailable()) {
                return false;
            }
            for (Reservation reservation : room.getReservationList()) {
                LocalDateTime reservationCheckInDateTime = reservation.getCheckInDate();
                LocalDateTime reservationCheckOutDateTime = reservation.getCheckOutDate();

                LocalDateTime desiredCheckInDateTime = desiredCheckInDate.atTime(12, 0);
                LocalDateTime desiredCheckOutDateTime = desiredCheckOutDate.atTime(12, 0);

                boolean overlap = !desiredCheckOutDateTime.isBefore(reservationCheckInDateTime)
                        && !desiredCheckInDateTime.isAfter(reservationCheckOutDateTime);

                if (overlap) {
                    return false;
                }
            }
            return true;
        }

        System.out.println("- Room doesn't exist. -");
        return false;
    }

    public Room getRoomByRoomNumber(int roomNumber) {
        for (Room room : this.getRoomList()) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void getGuestsInfo() {
        System.out.println("<< Information of guests currently staying at the hotel. >>");
        System.out.println("-----------------------------------");
        for (Guest guest : this.getGuestList()) {
            System.out.println("- Name: " + guest.getName());
            System.out.println("- Phone Number: " + guest.getPhoneNumber());
            System.out.println("- Email: " + guest.getEmail());
            System.out.println("-----------------------------------");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(Guest guest) {
        this.guestList.add(guest);
    }
}
