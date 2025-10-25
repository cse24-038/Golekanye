import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class WithdrawFundsController {
    @FXML
    private TextField amountField;

    @FXML
    private void handleWithdraw(ActionEvent event) {
        // Add withdrawal logic here
        double amount = Double.parseDouble(amountField.getText());
        // Implement withdrawal functionality
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        // Add navigation logic to return to main menu
    }
}