// Cheque.java
// Assuming WithdrawalCapability.java interface is available
/**
 * Concrete class for Cheque (Current) accounts.
 * Implements WithdrawalCapability.
 */
public class Cheque extends Account implements WithdrawalCapability {
    private String employerName;
    private String employerAddress;
    private static final double OVERDRAFT_LIMIT = 500.00;

    public Cheque(String accountNumber, String branch, String employerName, String employerAddress) {
        super(accountNumber, branch);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        // Cheque-specific withdrawal logic: allows withdrawal up to the overdraft limit
        if (getBalance() + OVERDRAFT_LIMIT >= amount) {
            super.Withdraw(amount);
        } else {
            System.out.println("❌ Cheque account withdrawal failed. Exceeds overdraft limit of $" + OVERDRAFT_LIMIT);
        }
    }

    // Getters for specific attributes (omitted for brevity)
}