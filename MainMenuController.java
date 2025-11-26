import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class MainMenuController {

    @FXML private FlowPane accountsPane;
    @FXML private Label thankLabel;

    private final FileAccountDAO accountDAO = new FileAccountDAO();

    @FXML
    public void initialize() {
        // Fade-in animation for thank-you label
        if (thankLabel != null) {
            thankLabel.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(800), thankLabel);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        }

        String customerId = CurrentSession.getCustomerId();
        if (customerId == null || customerId.isEmpty()) customerId = "C1001"; // fallback
        List<String> accounts = accountDAO.listAccountNumbersByCustomer(customerId);
        accountsPane.getChildren().clear();
        if (accounts.isEmpty()) {
            accountsPane.getChildren().add(buildCreateCard());
        } else {
            for (String acc : accounts) {
                FileAccountDAO.AccountRow row = accountDAO.getAccount(acc);
                accountsPane.getChildren().add(buildAccountCard(row));
            }
        }
    }

    private VBox buildAccountCard(FileAccountDAO.AccountRow row) {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.getStyleClass().add("card");
        Label title = new Label(row.accountNumber + " (" + row.type + ")");
        title.getStyleClass().add("card-title");
        title.setFont(new Font(16));
        Label branch = new Label("Branch: " + row.branch);
        Label bal = new Label("Balance: P" + String.format("%.2f", row.balance));
        bal.getStyleClass().add("card-balance");
        Button open = new Button("Open");
        open.setOnAction(e -> {
            // default: go to View Balance or Deposit for selected account could be implemented
            SceneNavigator.goTo("ViewBalance.fxml");
        });
        card.getChildren().addAll(title, branch, bal, open);
        return card;
    }

    private VBox buildCreateCard() {
        VBox card = new VBox(6);
        card.setPadding(new Insets(12));
        card.getStyleClass().add("card");
        Label title = new Label("No accounts yet");
        title.getStyleClass().add("card-title");
        title.setFont(new Font(16));
        Label hint = new Label("Create your first account");
        Button create = new Button("Create Account");
        create.setOnAction(e -> SceneNavigator.goTo("OpenAccount.fxml"));
        card.getChildren().addAll(title, hint, create);
        return card;
    }

    @FXML
    public void goDeposit() {
        SceneNavigator.goTo("DepositFunds.fxml");
    }

    @FXML
    public void goWithdraw() {
        SceneNavigator.goTo("WithdrawFunds.fxml");
    }

    @FXML
    public void goViewBalance() {
        SceneNavigator.goTo("ViewBalance.fxml");
    }

    @FXML
    public void goViewHistory() {
        SceneNavigator.goTo("ViewTransactionHistory.fxml");
    }

    @FXML
    public void goOpenAccount() {
        SceneNavigator.goTo("OpenAccount.fxml");
    }

    @FXML
    public void logout() {
        CurrentSession.setCustomerId(null);
        SceneNavigator.goTo("Login.fxml");
    }
}
