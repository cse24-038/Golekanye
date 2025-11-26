import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileCustomerDAO {
    private final Path dataDir = Paths.get("data");
    private final Path customersFile = dataDir.resolve("customers.csv");

    public FileCustomerDAO() {
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
            if (!Files.exists(customersFile)) {
                // header: username,customerId,firstName,lastName,phone,address,nextOfKinName,nextOfKinPhone
                Files.write(customersFile,
                    List.of("username,customerId,firstName,lastName,phone,address,nextOfKinName,nextOfKinPhone"),
                    StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize customers storage", e);
        }
    }

    public void upsertProfile(String username,
                              String customerId,
                              String firstName,
                              String lastName,
                              String phone,
                              String address,
                              String nextOfKinName,
                              String nextOfKinPhone) {
        try {
            List<String> lines = Files.readAllLines(customersFile);
            boolean updated = false;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line == null || line.trim().isEmpty() || line.startsWith("username")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].trim().equals(username)) {
                    lines.set(i, csv(username, customerId, firstName, lastName, phone, address, nextOfKinName, nextOfKinPhone));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                lines.add(csv(username, customerId, firstName, lastName, phone, address, nextOfKinName, nextOfKinPhone));
            }
            Files.write(customersFile, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save customer profile", e);
        }
    }

    private String csv(String... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(fields[i] == null ? "" : fields[i]);
        }
        return sb.toString();
    }

    public String findCustomerIdByUsername(String username) {
        try {
            List<String> lines = Files.readAllLines(customersFile);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty() || line.startsWith("username")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 2 && parts[0].trim().equals(username)) {
                    return parts[1].trim();
                }
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read customer profile", e);
        }
    }
}
