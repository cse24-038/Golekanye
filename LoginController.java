import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final FileAuthDAO authDAO = new FileAuthDAO();
    private final FileCustomerDAO customerDAO = new FileCustomerDAO();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String pwd = passwordField.getText();
        if (username == null || username.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login Error");
            a.setHeaderText(null);
            a.setContentText("Please enter Username");
            a.showAndWait();
            return;
        }
        if (pwd == null || pwd.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login Error");
            a.setHeaderText(null);
            a.setContentText("Please enter Password");
            a.showAndWait();
            return;
        }
        boolean ok = authDAO.validate(username.trim(), pwd);
        if (!ok) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Login Error");
            a.setHeaderText(null);
            a.setContentText("Invalid credentials");
            a.showAndWait();
            return;
        }
        // Map username to customerId if present; else fallback to username
        String mapped = customerDAO.findCustomerIdByUsername(username.trim());
        CurrentSession.setCustomerId(mapped != null && !mapped.isEmpty() ? mapped : username.trim());
        SceneNavigator.goTo("Mainmenu.fxml");
    }

    @FXML
    public void goToRegister() {
        SceneNavigator.goTo("Register.fxml");
    }
}
