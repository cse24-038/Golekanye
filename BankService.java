import java.util.*;

public class BankService {
    private final FileAccountDAO accountDAO;
    private final FileTransactionDAO txDAO;

    public BankService(FileAccountDAO accountDAO, FileTransactionDAO txDAO) {
        this.accountDAO = accountDAO;
        this.txDAO = txDAO;
    }

    public List<String> getAccountNumbersByCustomer(String customerId) {
        return accountDAO.listAccountNumbersByCustomer(customerId);
    }

    private Account instantiateAccount(FileAccountDAO.AccountRow row) {
        Account account;
        switch (row.type) {
            case "Savings":
                account = new Savings(row.accountNumber, row.branch);
                break;
            case "Investment":
                account = new Investment(row.accountNumber, row.branch);
                break;
            case "Cheque":
                account = new Cheque(row.accountNumber, row.branch, "Employer", "Address");
                break;
            default:
                throw new IllegalArgumentException("Unknown account type: " + row.type);
        }
        account.setBalanceForLoad(row.balance);
        return account;
    }

    public double deposit(String accountNumber, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        FileAccountDAO.AccountRow row = accountDAO.getAccount(accountNumber);
        Account acc = instantiateAccount(row);
        double before = acc.getBalance();
        acc.Deposit(amount);
        double after = acc.getBalance();
        if (after != before) {
            accountDAO.updateBalance(accountNumber, after);
            txDAO.append(accountNumber, "Deposit", amount, after, "");
        }
        return after;
    }

    public double withdraw(String accountNumber, double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        FileAccountDAO.AccountRow row = accountDAO.getAccount(accountNumber);
        Account acc = instantiateAccount(row);
        double before = acc.getBalance();
        // Use capability if available, else default
        if (acc instanceof Cheque) {
            ((Cheque) acc).withdraw(amount);
        } else if (acc instanceof Investment) {
            ((Investment) acc).withdraw(amount);
        } else {
            acc.Withdraw(amount);
        }
        double after = acc.getBalance();
        if (after != before) {
            accountDAO.updateBalance(accountNumber, after);
            txDAO.append(accountNumber, "Withdrawal", -amount, after, "");
        }
        return after;
    }
}
