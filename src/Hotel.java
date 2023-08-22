import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name;

    private List<Room> rooms= new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(int roomNumber, RoomType type, boolean isAvailable) {
        Room room = new Room(roomNumber, type, true);
        this.rooms.add(room);
    }

    public boolean checkRoomAvailability(int roomNumber) {
        for(Room room : rooms) {
            if(room.getRoomNumber() == roomNumber && room.isAvailable()) {
                return true;
            }
        }
        return false;
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
