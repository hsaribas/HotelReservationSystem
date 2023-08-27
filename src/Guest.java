public class Guest {

    private String name;
    private String phoneNumber;
    private String email;

    public void guestInfo() {
        System.out.println("-----------------------------------");
        System.out.println(">> Guest Information: ");
        System.out.println("- Name: " + this.getName());
        System.out.println("- Phone Number: " + this.getPhoneNumber());
        System.out.println("- Email: " + this.getEmail());
        System.out.println("-----------------------------------");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
