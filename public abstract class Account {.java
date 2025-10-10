public abstract class Account {
    // Attributes
    private String accountNumber;
    private double accountBalance;
    private String accountBranch;


    public Account(String accountNumber, String accountBranch, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountBranch = accountBranch;
        this.accountBalance = initialBalance;
    }


    public void deposit(double amount) {
        if (amount > 0) {
            this.accountBalance += amount;
            System.out.printf("Deposited %.2f into account %s. New balance: %.2f%n", amount, this.accountNumber, this.accountBalance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    
    public abstract void withdraw(double amount); // Abstract as per the diagram's design

    
    public double getBalance() {
        return accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;


    protected void setBalance(double newBalance) {
        this.accountBalance = newBalance;
    }
}

