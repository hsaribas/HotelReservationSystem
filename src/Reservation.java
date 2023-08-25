import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Reservation {

    private Hotel hotel;
    private Guest guest;
    private Room room;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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

        selectRoom();

        getDesiredCheckInDate();
        getDesiredCheckOutDate();

        presentDateOptions(getDesiredCheckInDate(), getDesiredCheckOutDate());
        reservingRoom(selectRoom(), getDesiredCheckInDate(), getDesiredCheckOutDate());
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

    public void reservingRoom(int roomNumber, LocalDate desiredCheckInDate, LocalDate desiredCheckOutDate) {
        if (hotel.checkRoomAvailability(roomNumber, desiredCheckInDate, desiredCheckOutDate)) {
            LocalDateTime checkInDateTime = desiredCheckInDate.atStartOfDay();
            LocalDateTime checkOutDateTime = desiredCheckOutDate.atTime(23, 59, 59);

            String formattedCheckInDateTime = checkInDateTime.format(formatter1);
            String formattedCheckOutDateTime = checkOutDateTime.format(formatter1);

            this.setCheckInDate(checkInDateTime);
            this.setCheckOutDate(checkOutDateTime);

            System.out.println("* Room " + roomNumber + " is reserved to Mr&Mrs" + guest.getName() + " from " +
                    formattedCheckInDateTime + " to " + formattedCheckOutDateTime + " *");

            for (Room r : hotel.getRooms()) {
                if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                    r.setAvailable(false);
                }
            }
        } else {
            System.out.println("> The room is not available for the specified dates.");
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

    public int selectRoom() {
        System.out.print("- Enter the number of the room you chose: ");
        int roomNumber = scan.nextInt();

        for (Room room : hotel.getRooms()) {
            if(room.getRoomNumber() == roomNumber) {
                this.room = room;
            }
        }

        return roomNumber;
    }

    public LocalDate getDesiredCheckInDate() {
        while (true) {
            System.out.print("- Enter the desired check-in date (dd-MM-yyyy): ");
            String inputDate = scan.nextLine();

            try {
                return LocalDate.parse(inputDate, formatter2);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format dd-MM-yyyy.");
            }
        }
    }

    public LocalDate getDesiredCheckOutDate() {
        while (true) {
            System.out.print("- Enter the desired check-out date (dd-MM-yyyy): ");
            String inputDate = scan.nextLine();

            try {
                return LocalDate.parse(inputDate, formatter2);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format dd-MM-yyyy.");
            }
        }
    }

    public List<LocalDate> generateAvailableDates(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> availableDates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (hotel.checkRoomAvailability(room.getRoomNumber(), currentDate, endDate)) {
                availableDates.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return availableDates;
    }

    public void presentDateOptions(LocalDate desiredCheckIn, LocalDate desiredCheckOut) {
        List<LocalDate> availableDates = generateAvailableDates(desiredCheckIn, desiredCheckOut);

        System.out.println("Available Check-in Dates:");
        for (int i = 0; i < availableDates.size(); i++) {
            System.out.println((i + 1) + ". " + availableDates.get(i));
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
