import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import java.util.List;

public class WithdrawFundsController {
    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> accountSelector;

    private final BankService bankService = new BankService(new FileAccountDAO(), new FileTransactionDAO());

    @FXML
    public void initialize() {
        String customerId = CurrentSession.getCustomerId();
        if (customerId == null || customerId.trim().isEmpty()) {
            showMessage("Error: No logged-in customer.", true);
            return;
        }
        List<String> accounts = bankService.getAccountNumbersByCustomer(customerId);
        accountSelector.setItems(FXCollections.observableArrayList(accounts));
        if (!accounts.isEmpty()) accountSelector.getSelectionModel().select(0);
    }

    @FXML
    private void handleWithdraw(ActionEvent event) {
        try {
            String accountNumber = accountSelector.getSelectionModel().getSelectedItem();
            if (accountNumber == null) {
                showMessage("Error: Please select an account.", true);
                return;
            }
            double amount = Double.parseDouble(amountField.getText());
            double newBal = bankService.withdraw(accountNumber, amount);
            showMessage("Withdrawal of P" + String.format("%.2f", amount) + " processed. New Balance: " + String.format("%.2f", newBal), false);
            amountField.clear();
        } catch (NumberFormatException ex) {
            showMessage("Error: Invalid amount entered.", true);
        } catch (Exception ex) {
            showMessage("Transaction failed: " + ex.getMessage(), true);
        }
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        SceneNavigator.goTo("Mainmenu.fxml");
    }

    private void showMessage(String msg, boolean error) {
        Alert a = new Alert(error ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        a.setTitle("Withdraw Status");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}