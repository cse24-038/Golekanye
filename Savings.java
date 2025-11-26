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
        double interestRate = 0.0005; // 0.05% monthly
        double interest = getBalance() * interestRate; // Monthly interest
        Deposit(interest);
        return interest;
    }
    
    // Savings accounts do not allow withdrawals per spec
    @Override
    public void Withdraw(double amount) {
        System.out.println("❌ Savings withdrawal not allowed.");
    }
}