// Main.java
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // --- Setup ---
        Individual alice = new Individual("C1001", "123 Maple St", "Alice", "Smith");
        Organisations corp = new Organisations("C2001", "456 Corp Ave", "TechSolutions Inc.", "TS1990");

        // --- Account Creation ---
        Savings aliceSavings = new Savings("S001", "Central Branch");
        Investment aliceInvest = new Investment("I001", "Central Branch");
        Cheque corpCheque = new Cheque("C001", "Corporate HQ", "HR Dept", "789 Employee Ln");

        // --- Association ---
        alice.openAccount(aliceSavings);
        alice.openAccount(aliceInvest);
        corp.openAccount(corpCheque);

        System.out.println("\n--- Initial Deposits ---");
        aliceSavings.Deposit(5000.00);
        aliceInvest.Deposit(15000.00);
        corpCheque.Deposit(20000.00);
        
        // --- Operations and Polymorphism ---
        System.out.println("\n--- Alice's Transactions ---");
        aliceSavings.Withdraw(500.00); // Uses Account.Withdraw
        
        // Uses Investment.withdraw() with minimum balance rule
        aliceInvest.withdraw(13500.00); // Fails (15000 - 13500 < 1000)
        aliceInvest.withdraw(13000.00); // Succeeds (15000 - 13000 >= 1000)

        System.out.println("\n--- Corporation Transactions (Overdraft Test) ---");
        corpCheque.withdraw(20000.00); // Succeeds (Balance: 0.00)
        corpCheque.withdraw(400.00);   // Succeeds (Balance: -400.00, within $500 limit)
        corpCheque.withdraw(200.00);   // Fails (Balance: -600.00, exceeds $500 limit)
        
        // --- Interface Usage (Interest) ---
        System.out.println("\n--- Interest Calculation ---");
        System.out.printf("Alice's Savings Interest added: $%.2f%n", aliceSavings.calculateInterest());
        System.out.printf("Alice's Investment Return added: $%.2f%n", aliceInvest.calculateInterest());

        // --- Final Balances and History ---
        System.out.println("\n--- Final Balances ---");
        printAccountSummary(aliceSavings);
        printAccountSummary(aliceInvest);
        printAccountSummary(corpCheque);
    }
    
    public static void printAccountSummary(Account account) {
        System.out.printf("Account %s (Type: %s): $%.2f%n", 
            account.getAccountNumber(), account.getClass().getSimpleName(), account.getBalance());
        
        System.out.println("  --- History ---");
        List<Transaction> history = account.getTransactions();
        history.forEach(t -> System.out.println("    " + t));
    }
}