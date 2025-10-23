// Investment.java
// Assuming InterestBearing.java and WithdrawalCapability.java interfaces are available
/**
 * Concrete class for Investment accounts.
 * Implements WithdrawalCapability and InterestBearing.
 */
public class Investment extends Account implements WithdrawalCapability, InterestBearing {

    public Investment(String accountNumber, String branch) {
        super(accountNumber, branch);
    }

    @Override
    public void withdraw(double amount) {
        // Investment-specific withdrawal: e.g., only allow withdrawal if it leaves >= $1000 minimum
        if (getBalance() - amount >= 1000.00) {
            super.Withdraw(amount);
        } else {
            System.out.println("❌ Investment withdrawal failed. Must maintain a minimum balance of $1000.");
        }
    }

    @Override
    public double calculateInterest() {
        // Placeholder for investment return/interest calculation (higher risk, higher return)
        double returnRate = 0.05; // 5% APY
        double investmentReturn = getBalance() * returnRate / 12;
        Deposit(investmentReturn);
        return investmentReturn;
    }
}