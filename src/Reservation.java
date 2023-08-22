import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Reservation {

    private Hotel hotel;

    private Guest guest;

    private Room room;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final Scanner scan = new Scanner(System.in);

    public void welcome() {
        allRooms();
        System.out.println();

        System.out.println(">> Before picking a room we need some information.");
        System.out.print("- Enter your name: ");
        String name = scan.nextLine().toUpperCase();
        System.out.print("- Enter your phone number: ");
        String phoneNum = scan.nextLine();
        System.out.print("- Enter your email: ");
        String email = scan.nextLine();
        guest = new Guest(name, phoneNum, email);

        System.out.print("- Enter the number of the room you chose: ");
        int roomNum = scan.nextInt();
        System.out.print("- How many days you want to stay? ");
        int durationOfStay = scan.nextInt();
        System.out.println();

        reservingRoom(roomNum, durationOfStay);
    }

    public void allRooms() {
        hotel = new Hotel("SKY Hotel Resort");
        hotel.addRoom(1100, RoomType.SINGLE, true);
        hotel.addRoom(1101, RoomType.DOUBLE, true);
        hotel.addRoom(1102, RoomType.SINGLE, true);
        hotel.addRoom(1103, RoomType.DOUBLE, true);
        hotel.addRoom(1104, RoomType.SINGLE, true);
        hotel.addRoom(1105, RoomType.DOUBLE, true);
        hotel.addRoom(1106, RoomType.SINGLE, true);
        hotel.addRoom(1107, RoomType.SUITE, true);
        hotel.addRoom(1108, RoomType.DOUBLE, true);
        hotel.addRoom(1109, RoomType.SINGLE, true);
        hotel.addRoom(1110, RoomType.DOUBLE, true);
        hotel.addRoom(1111, RoomType.SUITE, true);
        hotel.addRoom(1112, RoomType.DOUBLE, true);
        hotel.addRoom(1113, RoomType.SINGLE, true);

        System.out.println("* * * Welcome to " + hotel.getName() + " * * *");
        System.out.println();

        System.out.println(">> Here are the rooms in our hotel");
        System.out.println("-----------------------------------");
        for(Room r : hotel.getRooms()) {
            System.out.println("- Number: " + r.getRoomNumber());
            System.out.println("- Type: " + r.getType());
            System.out.println("- Availability: " + r.isAvailable());
            System.out.println("-----------------------------------");
        }
    }

    public void reservingRoom(int roomNum, int durationOfStay) {
        if(hotel.checkRoomAvailability(roomNum)) {
            LocalDateTime checkInDate = LocalDateTime.now();
            String formattedDateTime1 = checkInDate.format(formatter);
            LocalDateTime checkOutDate = checkInDate.plusDays(durationOfStay);
            String formattedDateTime2 = checkOutDate.format(formatter);
            System.out.println("* Room " + roomNum + " is reserved to " + guest.getName() + " on " +
                    formattedDateTime1 + " until " + formattedDateTime2 + " *");

            for(Room r : hotel.getRooms()) {
                if(r.getRoomNumber() == roomNum) {
                    r.setAvailable(false);
                }
            }
        } else {
            System.out.println("> Room " + roomNum + " is not available at the moment.");
        }
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
