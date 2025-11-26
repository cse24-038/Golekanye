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
            System.out.println("❌ Investment withdrawal failed. Must maintain a minimum balance of P1000.");
        }
    }

    @Override
    public double calculateInterest() {
        // 5% monthly interest per spec
        double returnRate = 0.05; // 5% monthly
        double investmentReturn = getBalance() * returnRate;
        Deposit(investmentReturn);
        return investmentReturn;
    }

    // Enforce initial deposit >= 500
    @Override
    public void Deposit(double amount) {
        if (getBalance() == 0.0 && amount < 500.0) {
            System.out.println("❌ Investment account requires a minimum initial deposit of P500.00.");
            return;
        }
        super.Deposit(amount);
    }
}