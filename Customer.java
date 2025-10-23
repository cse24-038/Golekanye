// Customer.java
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all customer types (Individual or Organisation).
 */
public abstract class Customer {
    private String customerID;
    private String address;
    // Association: 1 Customer to * Account
    private List<Account> accounts;

    public Customer(String customerID, String address) {
        this.customerID = customerID;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public void openAccount(Account account) {
        this.accounts.add(account);
        System.out.println("⭐ Account " + account.getAccountNumber() + " opened for customer " + customerID);
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }
    
    // Getter for ID
    public String getCustomerID() {
        return customerID;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract String getName();
}

// Concrete Customer Subclasses (These should ideally be in Individual.java and Organisations.java files)

class Individual extends Customer {
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

class Organisations extends Customer {
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