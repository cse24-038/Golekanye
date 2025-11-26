// Transaction.java
import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private double postBalance;
    private String description;

    public Transaction(String type, double amount, double postBalance) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.postBalance = postBalance;
    }

    public Transaction(String type, double amount, double postBalance, String description) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.postBalance = postBalance;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: P%.2f. New Balance: P%.2f", 
                             timestamp.toLocalDate(), type, Math.abs(amount), postBalance);
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getPostBalance() {
        return postBalance;
    }

    public String getDescription() {
        return description;
    }
}