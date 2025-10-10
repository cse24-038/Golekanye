public class Cheque extends Account implements WithdrawingMoney {
    
    private String employerName;
    private String employerAddress;

    
    public Cheque(String accountNumber, String accountBranch, double initialBalance, String employerName, String employerAddress) {
        super(accountNumber, accountBranch, initialBalance);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {            
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            System.out.printf("Withdrew %.2f from Cheque account %s. New balance: %.2f%n", amount, getAccountNumber(), getBalance());
        } else {
            System.out.printf("ERROR: Insufficient funds in Cheque account %s. Current balance: %.2f%n", getAccountNumber(), getBalance());
        }
    }

    
    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }
