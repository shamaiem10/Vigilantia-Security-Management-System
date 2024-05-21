
public class Resident extends Person {
    // Attributes
    protected String residentId;
    protected String residentPassword;
    protected String houseNumber;

    // Constructor
    Resident(String name, String contact, String residentId, String residentPassword, String houseNumber) {
        super(name, contact);
        this.residentId = residentId;
        this.residentPassword = residentPassword;
        this.houseNumber = houseNumber;
    }
    @Override
    public String toString() {
        return "RESIDENT NAME: " + name + "\nRESIDENT CONTACT: " + contact + "\nRESIDENT ID: " + residentId
                + "\nRESIDENT PASSWORD: " + residentPassword + "\nRESIDENT HOUSE NUMBER: " + houseNumber + "\n";
    }

}



