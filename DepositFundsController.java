import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.math.BigDecimal;
import java.util.List;
import java.io.File;

public class DepositFundsController implements CustomerBoundary, TransactionBoundary {

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> accountSelector;

    private final BankService bankService = new BankService(new FileAccountDAO(), new FileTransactionDAO());

    @FXML
    public void initialize() {
        String customerId = CurrentSession.getCustomerId();
        if (customerId == null || customerId.isEmpty()) customerId = "C1001"; // fallback for demo
        List<String> accounts = bankService.getAccountNumbersByCustomer(customerId);
        accountSelector.setItems(FXCollections.observableArrayList(accounts));
        if (!accounts.isEmpty()) accountSelector.getSelectionModel().select(0);
    }

    @FXML
    public void handleDeposit() {
        try {
            BigDecimal amount = getDepositAmount();
            String accountNumber = accountSelector.getSelectionModel().getSelectedItem();
            if (accountNumber == null) {
                displayTransactionResult("Error: Please select an account.");
                return;
            }
            double newBal = bankService.deposit(accountNumber, amount.doubleValue());
            displayTransactionResult("Deposit of P" + amount + " successful. New Balance: " + String.format("%.2f", newBal));
            amountField.clear();
        } catch (NumberFormatException e) {
            displayTransactionResult("Error: Invalid amount entered. Please use a number.");
        } catch (Exception e) {
            displayTransactionResult("Transaction failed: " + e.getMessage());
        }
    }

    @Override
    public BigDecimal getDepositAmount() throws NumberFormatException {
        return new BigDecimal(amountField.getText());
    }

    @Override
    public BigDecimal getWithdrawalAmount() {
        return null;
    }

    @Override
    public void displayTransactionResult(String message) {
        Alert alert = new Alert(message.startsWith("Error") || message.startsWith("Transaction failed") ?
                                Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle("Deposit Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void goToMainMenu() {
        SceneNavigator.goTo("Mainmenu.fxml");
    }
}