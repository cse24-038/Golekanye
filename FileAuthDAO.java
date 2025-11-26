import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileAuthDAO {
    private final Path dataDir = Paths.get("data");
    private final Path passFile = dataDir.resolve("pass.csv");

    public FileAuthDAO() {
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            if (!Files.exists(passFile)) {
                Files.write(passFile, List.of("username,password"), StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to initialize auth storage", e);
        }
    }

    public boolean validate(String username, String password) {
        try {
            List<String> lines = Files.readAllLines(passFile);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty() || line.startsWith("username")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 2) {
                    String u = parts[0].trim();
                    String p = parts[1].trim();
                    if (u.equals(username) && p.equals(password)) return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read credentials", e);
        }
    }

    public void register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username required");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password required");
        }
        String u = username.trim();
        try {
            // prevent duplicate usernames
            Set<String> existing = new HashSet<>();
            List<String> lines = Files.readAllLines(passFile);
            for (String line : lines) {
                if (line == null || line.trim().isEmpty() || line.startsWith("username")) continue;
                String[] parts = line.split(",", -1);
                if (parts.length >= 1) existing.add(parts[0].trim());
            }
            if (existing.contains(u)) {
                throw new IllegalArgumentException("Username already exists");
            }
            String row = u + "," + password;
            Files.write(passFile, List.of(row), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }
}
