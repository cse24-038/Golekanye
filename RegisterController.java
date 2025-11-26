import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField customerIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextField nextOfKinNameField;
    @FXML private TextField nextOfKinPhoneField;

    private final FileAuthDAO authDAO = new FileAuthDAO();
    private final FileCustomerDAO customerDAO = new FileCustomerDAO();

    @FXML
    public void handleRegister() {
        String username = text(usernameField);
        String pwd = text(passwordField);
        String customerId = text(customerIdField);
        String firstName = text(firstNameField);
        String lastName = text(lastNameField);
        String phone = text(phoneField);
        String address = text(addressField);
        String kinName = text(nextOfKinNameField);
        String kinPhone = text(nextOfKinPhoneField);

        if (isEmpty(username)) { alert("Please enter a username"); return; }
        if (isEmpty(pwd)) { alert("Please enter a password"); return; }
        if (isEmpty(customerId)) { alert("Please enter a customer ID"); return; }
        if (isEmpty(firstName)) { alert("Please enter first name"); return; }
        if (isEmpty(lastName)) { alert("Please enter last name"); return; }
        if (isEmpty(phone)) { alert("Please enter phone number"); return; }
        if (isEmpty(address)) { alert("Please enter address"); return; }
        if (isEmpty(kinName)) { alert("Please enter next of kin name"); return; }
        if (isEmpty(kinPhone)) { alert("Please enter next of kin phone"); return; }

        try {
            authDAO.register(username, pwd);
            customerDAO.upsertProfile(username, customerId, firstName, lastName, phone, address, kinName, kinPhone);
        } catch (IllegalArgumentException ex) {
            alert(ex.getMessage());
            return;
        }

        CurrentSession.setCustomerId(customerId);
        SceneNavigator.goTo("Mainmenu.fxml");
    }

    private String text(TextField f) { return f == null ? null : (f.getText() == null ? null : f.getText().trim()); }
    private boolean isEmpty(String s) { return s == null || s.isEmpty(); }

    @FXML
    public void backToLogin() {
        SceneNavigator.goTo("Login.fxml");
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Registration Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
