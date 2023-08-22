public class Room {

    private int roomNumber;

    private RoomType type;

    private boolean isAvailable;

    public Room(int roomNumber, RoomType type, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
