package banking;

import java.util.ArrayList;
import java.util.List;

public class Individual {
    private String customerId;
    private String firstName;
    private String lastName;
    private List<Account> accounts;

    public Individual(String customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
