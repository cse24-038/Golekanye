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