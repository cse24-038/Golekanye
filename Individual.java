// Individual.java
public class Individual extends Customer {
    private String firstName;
    private String lastName;

    public Individual(String customerID, String address, String firstName, String lastName) {
        super(customerID, address);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}
