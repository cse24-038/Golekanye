public void deposit(double amount) {
    if (amount > 0) {
        this.accountBalance += amount;
        System.out.printf("Deposited %.2f into account %s. New balance: %.2f%n", 
                          amount, this.accountNumber, this.accountBalance);
    } else {
        System.out.println("Deposit amount must be positive.");
    }
}


