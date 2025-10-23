// Savings.java
// Assuming InterestBearing.java interface is in the same directory/package
/**
 * Concrete class for Savings accounts.
 * Implements InterestBearing.
 */
public class Savings extends Account implements InterestBearing {

    public Savings(String accountNumber, String branch) {
        super(accountNumber, branch);
    }

    @Override
    public double calculateInterest() {
        double interestRate = 0.015; // 1.5% APY
        double interest = getBalance() * interestRate / 12; // Monthly interest
        Deposit(interest);
        return interest;
    }
    
    // Uses the default Account.Withdraw()
}