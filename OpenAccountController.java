import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class OpenAccountController {
    @FXML private ChoiceBox<String> accountType;
    @FXML private TextField branchField;
    @FXML private TextField initialDepositField;

    private final FileAccountDAO accountDAO = new FileAccountDAO();
    private final BankService bankService = new BankService(new FileAccountDAO(), new FileTransactionDAO());

    @FXML
    public void initialize() {
        accountType.getItems().addAll("Savings", "Investment", "Cheque");
        accountType.getSelectionModel().select("Savings");
    }

    @FXML
    public void handleCreate() {
        String type = accountType.getSelectionModel().getSelectedItem();
        String branch = branchField.getText();
        String cust = CurrentSession.getCustomerId();
        if (cust == null || cust.isEmpty()) cust = "C1001"; // fallback
        if (branch == null || branch.trim().isEmpty()) {
            alert("Please enter branch");
            return;
        }
        double initial = 0.0;
        try {
            String txt = initialDepositField.getText();
            initial = (txt == null || txt.isEmpty()) ? 0.0 : Double.parseDouble(txt);
        } catch (NumberFormatException ex) {
            alert("Invalid initial deposit");
            return;
        }
        String accNum = accountDAO.nextAccountNumber(type);
        accountDAO.createAccount(accNum, type, branch.trim(), 0.0, cust);
        try {
            if (initial > 0) {
                bankService.deposit(accNum, initial);
            }
            info("Account created: " + accNum);
            goBack();
        } catch (Exception ex) {
            alert("Failed to create account: " + ex.getMessage());
        }
    }

    @FXML
    public void goBack() {
        SceneNavigator.goTo("Mainmenu.fxml");
    }

    private void alert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Success");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
