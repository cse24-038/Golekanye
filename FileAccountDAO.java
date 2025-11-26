import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileAccountDAO {
    private final Path dataDir = Paths.get("data");
    private final Path accountsFile = dataDir.resolve("accounts.csv");

    public FileAccountDAO() {
        ensureSeed();
    }

    private void ensureSeed() {
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(accountsFile)) {
                List<String> lines = new ArrayList<>();
                lines.add("accountNumber,type,branch,balance,customerId,password");
                lines.add("S001,Savings,Central,5000.00,C1001,");
                lines.add("I001,Investment,Central,15000.00,C1001,");
                lines.add("C001,Cheque,Corporate,0.00,C2001,");
                Files.write(accountsFile, lines, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to seed accounts file", e);
        }
    }

    public static class AccountRow {
        public final String accountNumber;
        public final String type;
        public final String branch;
        public final double balance;
        public final String customerId;
        public final String password; // may be null if legacy row
        public AccountRow(String accountNumber, String type, String branch, double balance, String customerId, String password) {
            this.accountNumber = accountNumber; this.type = type; this.branch = branch; this.balance = balance; this.customerId = customerId; this.password = password;
        }
    }

    private Optional<AccountRow> parse(String line) {
        if (line == null || line.trim().isEmpty() || line.startsWith("accountNumber")) return Optional.empty();
        String[] parts = line.split(",");
        if (parts.length < 5) return Optional.empty();
        try {
            String pwd = parts.length >= 6 ? parts[5].trim() : null;
            return Optional.of(new AccountRow(
                parts[0].trim(), parts[1].trim(), parts[2].trim(), Double.parseDouble(parts[3].trim()), parts[4].trim(), pwd
            ));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }

    public List<String> listAccountNumbersByCustomer(String customerId) {
        try {
            return Files.readAllLines(accountsFile).stream()
                .map(this::parse).filter(Optional::isPresent).map(Optional::get)
                .filter(r -> r.customerId.equals(customerId))
                .map(r -> r.accountNumber)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AccountRow getAccount(String accountNumber) {
        try {
            for (String line : Files.readAllLines(accountsFile)) {
                Optional<AccountRow> row = parse(line);
                if (row.isPresent() && row.get().accountNumber.equals(accountNumber)) return row.get();
            }
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double updateBalance(String accountNumber, double newBalance) {
        try {
            List<String> lines = Files.readAllLines(accountsFile);
            List<String> out = new ArrayList<>();
            boolean headerWritten = false;
            for (String line : lines) {
                if (line.startsWith("accountNumber")) { out.add("accountNumber,type,branch,balance,customerId,password"); headerWritten = true; continue; }
                Optional<AccountRow> row = parse(line);
                if (!row.isPresent()) { out.add(line); continue; }
                AccountRow r = row.get();
                if (r.accountNumber.equals(accountNumber)) {
                    out.add(String.join(",",
                        r.accountNumber, r.type, r.branch, String.format(Locale.US, "%.2f", newBalance), r.customerId, (r.password==null?"":r.password)
                    ));
                } else {
                    out.add(String.join(",",
                        r.accountNumber, r.type, r.branch, String.format(Locale.US, "%.2f", r.balance), r.customerId, (r.password==null?"":r.password)
                    ));
                }
            }
            Files.write(accountsFile, out, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            return newBalance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAccount(String accountNumber, String type, String branch, double initialBalance, String customerId) {
        String line = String.join(",",
            accountNumber, type, branch, String.format(Locale.US, "%.2f", initialBalance), customerId, ""
        );
        try {
            if (!Files.exists(accountsFile)) ensureSeed();
            Files.write(accountsFile, Collections.singletonList(line), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create account", e);
        }
    }

    public String nextAccountNumber(String type) {
        // Simple prefix by type: Savings=S, Investment=I, Cheque=C, then numeric + 1
        String prefix = type.startsWith("Sav") ? "S" : type.startsWith("Inv") ? "I" : "C";
        int max = 0;
        try {
            for (String line : Files.readAllLines(accountsFile)) {
                Optional<AccountRow> row = parse(line);
                if (row.isPresent() && row.get().accountNumber.startsWith(prefix)) {
                    String num = row.get().accountNumber.replaceAll("[^0-9]", "");
                    if (!num.isEmpty()) max = Math.max(max, Integer.parseInt(num));
                }
            }
        } catch (IOException ignored) {}
        return String.format(prefix + "%03d", max + 1);
    }

    public void setCustomerPassword(String customerId, String password) {
        try {
            List<String> lines = Files.readAllLines(accountsFile);
            List<String> out = new ArrayList<>();
            out.add("accountNumber,type,branch,balance,customerId,password");
            boolean any = false;
            for (String line : lines) {
                Optional<AccountRow> row = parse(line);
                if (!row.isPresent()) continue;
                AccountRow r = row.get();
                String pwd = r.customerId.equals(customerId) ? password : (r.password==null?"":r.password);
                out.add(String.join(",",
                    r.accountNumber, r.type, r.branch, String.format(Locale.US, "%.2f", r.balance), r.customerId, pwd
                ));
                if (r.customerId.equals(customerId)) any = true;
            }
            if (!any) {
                // If no rows for this customer yet, create a placeholder zero-balance Savings account to store password
                out.add(String.join(",",
                    nextAccountNumber("Savings"), "Savings", "Central", String.format(Locale.US, "%.2f", 0.0), customerId, password
                ));
            }
            Files.write(accountsFile, out, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to set password", e);
        }
    }

    public boolean validateCredentials(String customerId, String password) {
        try {
            for (String line : Files.readAllLines(accountsFile)) {
                Optional<AccountRow> row = parse(line);
                if (!row.isPresent()) continue;
                AccountRow r = row.get();
                if (r.customerId.equals(customerId)) {
                    String pwd = (r.password==null?"":r.password);
                    if (!pwd.isEmpty() && pwd.equals(password)) return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
