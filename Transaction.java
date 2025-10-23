// Transaction.java
import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private double postBalance;

    public Transaction(String type, double amount, double postBalance) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.postBalance = postBalance;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f. New Balance: $%.2f", 
                             timestamp.toLocalDate(), type, Math.abs(amount), postBalance);
    }

    // Getters (omitted for brevity)
}