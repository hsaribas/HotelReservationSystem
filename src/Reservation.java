import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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
        System.out.println();

        System.out.print("- Enter your name: ");
        String name = scan.nextLine().toUpperCase();

        enterPhoneNumber();
        enterEmailAddress();

        guest = new Guest();
        guest.setName(name);
        guest.setPhoneNumber(enterPhoneNumber());
        guest.setEmail(enterEmailAddress());

        System.out.print("- Enter the number of the room you chose: ");
        int roomNumber = scan.nextInt();

        System.out.print("- How many days you want to stay? ");
        int durationOfStay = scan.nextInt();
        System.out.println();

        reservingRoom(roomNumber, durationOfStay, LocalDate.now());
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
        for (Room r : hotel.getRooms()) {
            System.out.println("- Number: " + r.getRoomNumber());
            System.out.println("- Type: " + r.getType());
            System.out.println("- Availability: " + r.isAvailable());
            System.out.println("-----------------------------------");
        }
    }

    public void reservingRoom(int roomNumber, int durationOfStay, LocalDate desiredCheckInDate) {
        if (hotel.checkRoomAvailability(roomNumber, desiredCheckInDate)) {
            LocalDateTime checkInDate = desiredCheckInDate.atStartOfDay();
            LocalDateTime checkOutDate = checkInDate.plusDays(durationOfStay);

            String formattedCheckInDateTime = checkInDate.format(formatter);
            String formattedCheckOutDateTime = checkOutDate.format(formatter);

            this.setCheckInDate(LocalDateTime.parse(formattedCheckInDateTime));
            this.setCheckOutDate(LocalDateTime.parse(formattedCheckOutDateTime));

            System.out.println("* Room " + roomNumber + " is reserved to Mr&Mrs" + guest.getName() + " on " +
                    formattedCheckInDateTime + " until " + formattedCheckOutDateTime + " *");

            for (Room r : hotel.getRooms()) {
                if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                    r.setAvailable(false);
                }
            }
        }
    }

    public String enterPhoneNumber() {
        System.out.print("- Enter your phone number: ");
        String phoneNumber = scan.nextLine();
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");

        if (digitsOnly.length() == 11) {
            return String.format(
                    "%s-%s-%s-%s",
                    digitsOnly.substring(0, 4),
                    digitsOnly.substring(4, 7),
                    digitsOnly.substring(7, 9),
                    digitsOnly.substring(9)
            );
        } else {
            System.out.println("Invalid phone number! Try again.");
            System.out.println();
            return enterPhoneNumber();
        }
    }

    public String enterEmailAddress() {
        System.out.print("- Enter your email address: ");
        String email = scan.nextLine();

        if (isValidEmailAddress(email)) {
            return email.toLowerCase();
        } else {
            System.out.println("Invalid email address! Try again.");
            System.out.println();
            return enterEmailAddress();
        }
    }

    public boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
        return email.matches(regex);
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
