// Organisations.java
public class Organisations extends Customer {
    private String organisationName;
    private String registrationNumber;

    public Organisations(String customerID, String address, String organisationName, String registrationNumber) {
        super(customerID, address);
        this.organisationName = organisationName;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String getName() {
        return organisationName;
    }
}
