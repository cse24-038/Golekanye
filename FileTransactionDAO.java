import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileTransactionDAO {
    private final Path dataDir = Paths.get("data");
    private final Path txFile = dataDir.resolve("transactions.csv");
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public FileTransactionDAO() {
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(txFile)) {
                Files.write(txFile,
                        Collections.singletonList("timestamp,accountNumber,type,amount,postBalance,description"),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to init transactions file", e);
        }
    }

    public void append(String accountNumber, String type, double amount, double postBalance, String description) {
        String line = String.join(",",
                LocalDateTime.now().format(fmt),
                accountNumber,
                type,
                String.format(Locale.US, "%.2f", amount),
                String.format(Locale.US, "%.2f", postBalance),
                description == null ? "" : description.replace(",", " ")
        );
        try {
            Files.write(txFile, Collections.singletonList(line), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> listByAccount(String accountNumber) {
        try {
            List<String> lines = Files.readAllLines(txFile);
            List<String[]> out = new ArrayList<>();
            for (String l : lines) {
                if (l.startsWith("timestamp")) continue;
                String[] parts = l.split(",", -1);
                if (parts.length < 6) continue;
                if (parts[1].equals(accountNumber)) out.add(parts);
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
