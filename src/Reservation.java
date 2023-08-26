import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Reservation {

    private final Hotel hotel = new Hotel("SKY Hotel Resort");
    private Guest guest = new Guest();
    private Room room;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final Scanner scan = new Scanner(System.in);

    public void welcome() {
        System.out.println("* * * Welcome to " + hotel.getName() + " * * *");
        System.out.println();

        System.out.println(">> Before picking a room we need some information. <<");
        System.out.println();

        System.out.print("- Enter your name: ");
        String name = scan.nextLine().toUpperCase();

        String phoneNumber = enterPhoneNumber();
        String emailAddress = enterEmailAddress();

        guest.setName(name);
        guest.setPhoneNumber(phoneNumber);
        guest.setEmail(emailAddress);

        hotel.setGuestList(guest);

        while (true) {
            System.out.println();
            allRooms();

            System.out.println();
            int selectedRoomNumber = selectRoom();
            LocalDate checkInDate = getDesiredCheckInDate();
            LocalDate checkOutDate = getDesiredCheckOutDate();

            System.out.println();
            presentDateOptions(selectedRoomNumber, checkInDate, checkOutDate);

            System.out.println();
            System.out.print("Do you want to continue or check out a new room?\n" +
                             "To continue (press '1'), to check a new room (press '2'): ");
            String decision = scan.nextLine();
            if (decision.equals("1")) {
                System.out.println();
                reservingRoom(selectedRoomNumber, checkInDate, checkOutDate);

                System.out.println();
                selectExtraRoom();
                break;
            } else if (decision.equals("2")) {
            } else {
                System.out.println();
                System.out.println("Invalid input! Please enter '1' or '2'.");
            }
        }
    }

    public void allRooms() {
        hotel.addRoom(1100, RoomType.SINGLE);
        hotel.addRoom(1101, RoomType.DOUBLE);
        hotel.addRoom(1102, RoomType.SINGLE);
        hotel.addRoom(1103, RoomType.DOUBLE);
        hotel.addRoom(1104, RoomType.SINGLE);
        hotel.addRoom(1105, RoomType.DOUBLE);
        hotel.addRoom(1106, RoomType.SINGLE);
        hotel.addRoom(1107, RoomType.SUITE);
        hotel.addRoom(1108, RoomType.DOUBLE);
        hotel.addRoom(1109, RoomType.SINGLE);
        hotel.addRoom(1110, RoomType.DOUBLE);
        hotel.addRoom(1111, RoomType.SUITE);
        hotel.addRoom(1112, RoomType.DOUBLE);
        hotel.addRoom(1113, RoomType.SINGLE);

        System.out.println(">> Here are the rooms in our hotel. <<");
        System.out.println("-----------------------------------");
        for (Room r : hotel.getRoomList()) {
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

            System.out.println("* Room " + roomNumber + " is reserved.\n" +
                    "* Mr&Mrs " + guest.getName() + "\n" +
                    "* From " + formattedCheckInDateTime + " to " + formattedCheckOutDateTime);

            for (Room r : hotel.getRoomList()) {
                if (Objects.equals(r.getRoomNumber(), roomNumber)) {
                    r.setAvailable(false);
                }
            }
        } else {
            System.out.println("> The room is not available for the specified dates.");
        }
    }

    public String enterPhoneNumber() {
        while (true) {
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
            }

            System.out.println("Invalid phone number! Try again.");
            System.out.println();
        }
    }

    public String enterEmailAddress() {
        while (true) {
            System.out.print("- Enter your email address: ");
            String email = scan.nextLine();

            if (isValidEmailAddress(email)) {
                return email.toLowerCase();
            }

            System.out.println("Invalid email address! Try again.");
            System.out.println();
        }
    }

    public boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";

        return email.matches(regex);
    }

    public int selectRoom() {
        System.out.print("- Enter the number of the room you chose: ");
        int roomNumber = scan.nextInt();
        scan.nextLine();

        for (Room room : hotel.getRoomList()) {
            if (room.getRoomNumber() == roomNumber) {
                this.room = room;
            }
        }

        return roomNumber;
    }

    public LocalDate getDesiredCheckInDate() {
        System.out.print("- Enter the desired check-in date (dd-MM-yyyy): ");
        String inputDate = scan.nextLine();

        try {
            return LocalDate.parse(inputDate, formatter2);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please enter the date in the format dd-MM-yyyy.");
            return getDesiredCheckInDate();
        }
    }

    public LocalDate getDesiredCheckOutDate() {
        System.out.print("- Enter the desired check-out date (dd-MM-yyyy): ");
        String inputDate = scan.nextLine();

        try {
            return LocalDate.parse(inputDate, formatter2);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please enter the date in the format dd-MM-yyyy.");
            return getDesiredCheckOutDate();
        }
    }

    public List<LocalDate> generateAvailableDates(int roomNumber, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> availableDates = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            if (hotel.checkRoomAvailability(roomNumber, startDate, endDate)) {
                availableDates.add(startDate);
            }
            startDate = startDate.plusDays(1);
        }

        return availableDates;
    }

    public void presentDateOptions(int roomNumber, LocalDate desiredCheckInDate, LocalDate desiredCheckOutDate) {
        List<LocalDate> availableDates = generateAvailableDates(roomNumber, desiredCheckInDate, desiredCheckOutDate);

        System.out.println("Available Check-in Dates:");
        for (int i = 0; i < availableDates.size(); i++) {
            System.out.println((i + 1) + ". " + availableDates.get(i));
        }
    }

    public void selectExtraRoom() {
        System.out.print("If you want to select extra room (press '1'), to quit (press '2'): ");
        String pick = scan.next();

        if (pick.equals("1")) {
            System.out.println();
            welcome();
        } else if (pick.equals("2")) {
            System.out.println("* * * Have a nice holiday! * * *");
        } else {
            System.out.println("Invalid input! Try again.");
            selectExtraRoom();
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
