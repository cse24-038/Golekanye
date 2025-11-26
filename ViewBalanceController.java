import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.math.BigDecimal;

public class ViewBalanceController implements CustomerBoundary {

    @FXML
    private Label balanceLabel;

    @FXML
    public void initialize() {
        loadBalance();
    }

    private void loadBalance() {
        BigDecimal currentBalance = new BigDecimal("15250.75");
        balanceLabel.setText("P " + currentBalance.toString());
    }

    @FXML
    public void refreshBalance() {
        loadBalance();
    }

    @Override
    public void goToMainMenu() {
        SceneNavigator.goTo("Mainmenu.fxml");
    }
}