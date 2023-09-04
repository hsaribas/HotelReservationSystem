import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;
    private final List<Room> roomList;
    private final List<Guest> guestList = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
        this.roomList = new ArrayList<>();
    }

    public void addRoom(int roomNumber, RoomType type, boolean isAvailable) {
        Room room = new Room(roomNumber, type, isAvailable);
        this.setRoomList(room);
    }

    public void allRooms() {
        addRoom(1102, RoomType.SINGLE, true);
        addRoom(1103, RoomType.DOUBLE, true);
        addRoom(1104, RoomType.SINGLE, true);
        addRoom(1105, RoomType.DOUBLE, true);
        addRoom(1106, RoomType.SINGLE, true);
        addRoom(1107, RoomType.SUITE, true);
        addRoom(1108, RoomType.DOUBLE, true);
        addRoom(1109, RoomType.SINGLE, true);
        addRoom(1110, RoomType.DOUBLE, true);
        addRoom(1111, RoomType.SUITE, true);
        addRoom(1112, RoomType.DOUBLE, true);
        addRoom(1113, RoomType.SINGLE, true);

        System.out.println(">> Available rooms in our hotel: <<");
        System.out.println("-----------------------------------");

        for (Room room : this.roomList) {
            if (room.isAvailable()) {
                System.out.println("- Number: " + room.getRoomNumber());
                System.out.println("- Type: " + room.getType());
                System.out.println("- Availability: " + room.isAvailable());
                System.out.println("-----------------------------------");
            }
        }
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

    public void updateRoomAvailability(int roomNumber, boolean isAvailable) {
        for (Room room : roomList) {
            if (room.getRoomNumber() == roomNumber) {
                room.setAvailable(isAvailable);
                break;
            }
        }
    }

    public void getGuestsInfo() {
        System.out.println("<< Information of guests currently staying at the hotel: >>");
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

    public void setRoomList(Room room) {
        this.roomList.add(room);
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(Guest guest) {
        this.guestList.add(guest);
    }
}
