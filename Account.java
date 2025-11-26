// Account.java
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all account types.
 */
public abstract class Account {
    // Attributes
    private String AccountNumber;
    private double Balance;
    private String Branch;
    // Transaction history (Composition)
    private List<Transaction> transactions; 

    // Constructor
    public Account(String accountNumber, String branch) {
        this.AccountNumber = accountNumber;
        this.Branch = branch;
        this.Balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Public Methods
    public void Deposit(double amount) {
        if (amount > 0) {
            this.Balance += amount;
            this.transactions.add(new Transaction("Deposit", amount, this.Balance));
            System.out.println("✅ Deposited P" + amount + " to account " + AccountNumber);
        }
    }
    
    // Default withdraw method (can be overridden)
    public void Withdraw(double amount) {
        if (Balance >= amount) {
            this.Balance -= amount;
            this.transactions.add(new Transaction("Withdrawal", -amount, this.Balance));
            System.out.println("⬇️ Withdrew P" + amount + " from account " + AccountNumber);
        } else {
            System.out.println("❌ Withdrawal failed. Insufficient funds in account " + AccountNumber);
        }
    }

    public double getBalance() {
        return this.Balance;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // For loading persisted balance without creating a transaction
    protected void setBalanceForLoad(double amount) {
        this.Balance = amount;
    }
}


