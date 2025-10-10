public class Individual extends Customer {
    
    private String firstName;
    private String lastName;

    
    public Individual(String customerID, String firstName, String lastName) {
        // Concatenate names for the base class (assuming CustomerName is full name)
        super(customerID, firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
