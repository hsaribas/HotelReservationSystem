import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
    private boolean flag = true;

    public void welcome() {
        hotel = new Hotel("SKY Hotel Resort");
        System.out.println("* * * Welcome to " + hotel.getName() + " * * *");
        System.out.println();

        System.out.println("> Before picking a room we need some information: ");
        System.out.println();

        main();
    }

    public void main() {
        System.out.print("- Enter your name: ");
        String name = scan.nextLine().toUpperCase();

        String phoneNumber = enterPhoneNumber();
        String emailAddress = enterEmailAddress();

        guest = new Guest();
        guest.setName(name);
        guest.setPhoneNumber(phoneNumber);
        guest.setEmail(emailAddress);

        hotel.setGuestList(guest);

        while (flag) {
            System.out.println();
            hotel.allRooms();

            System.out.println();
            int selectedRoomNumber = selectRoom();
            LocalDate checkInDate = getDesiredCheckInDate();
            LocalDate checkOutDate = getDesiredCheckOutDate();

            System.out.println();
            presentDateOptions(selectedRoomNumber, checkInDate, checkOutDate);

            System.out.println();
            nextMove(selectedRoomNumber, checkInDate, checkOutDate);
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

            System.out.println("-----------------------------------");
            System.out.println("* Room " + roomNumber + " is successfully reserved for new guest.\n" +
                    "* From " + formattedCheckInDateTime + " to " + formattedCheckOutDateTime + ".");
            guest.guestInfo();

            hotel.updateRoomAvailability(roomNumber, false);

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

            System.out.println("> Invalid phone number! Try again.");
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

            System.out.println("> Invalid email address! Try again.");
            System.out.println();
        }
    }

    public boolean isValidEmailAddress(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$";
        return email.matches(regex);
    }

    public int selectRoom() {
        int roomNumber;

        while (true) {
            System.out.print("- Enter the number of the room you choose: ");
            roomNumber = scan.nextInt();
            scan.nextLine();

            boolean isValidRoomNumber = false;
            for (Room room : hotel.getRoomList()) {
                if (room.getRoomNumber() == roomNumber) {
                    isValidRoomNumber = true;
                    this.room = room;
                    break;
                }
            }

            if (isValidRoomNumber) {
                break;
            } else {
                System.out.println("> Invalid room number! Please enter a valid room number.");
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
            System.out.println("> Invalid date format! Please enter the date in the format dd-MM-yyyy.");
            return getDesiredCheckInDate();
        }
    }

    public LocalDate getDesiredCheckOutDate() {
        System.out.print("- Enter the desired check-out date (dd-MM-yyyy): ");
        String inputDate = scan.nextLine();

        try {
            return LocalDate.parse(inputDate, formatter2);
        } catch (DateTimeParseException e) {
            System.out.println("> Invalid date format! Please enter the date in the format dd-MM-yyyy.");
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

        System.out.println("<< The days when the room is available in the date range you specify: >>");
        for (int i = 0; i < availableDates.size(); i++) {
            System.out.println("- " + (i + 1) + ". " + availableDates.get(i));
        }
    }

    public void nextMove(int selectedRoomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        System.out.print("> Do you want to continue to reserving a room or do you want to check a new room?\n" +
                         "To continue (press '1'), to check a new room (press '2'): ");
        String decision = scan.nextLine();

        if (decision.equals("1")) {
            System.out.println();
            reservingRoom(selectedRoomNumber, checkInDate, checkOutDate);
            System.out.println();
            selectExtraRoom();
        } else if (decision.equals("2")) {
            System.out.println();
        } else {
            System.out.println();
            System.out.println("> Invalid input! Try again.");
            System.out.println();
            nextMove(selectedRoomNumber, checkInDate, checkOutDate);
        }
    }

    public void selectExtraRoom() {
        System.out.print("> If you want to select an extra room (press '1'), to quit (press '2'): ");
        String pick = scan.nextLine();

        if (pick.equals("1")) {
            System.out.println();
            main();
        } else if (pick.equals("2")) {
            System.out.println();
            System.out.println("* * * Have a nice vacation! * * *");
            flag = false;
        } else {
            System.out.println("> Invalid input! Try again.");
            System.out.println();
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
