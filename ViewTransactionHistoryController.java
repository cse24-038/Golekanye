import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ViewTransactionHistoryController {
    @FXML
    private TableView<Transaction> historyTable;
    
    @FXML
    private TableColumn<Transaction, Date> dateColumn;
    
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;

    @FXML
    private ComboBox<String> accountSelector;

    private final FileTransactionDAO txDAO = new FileTransactionDAO();
    private final FileAccountDAO accountDAO = new FileAccountDAO();

    @FXML
    public void initialize() {
        // Initialize the table columns
        // Note: Transaction getters provide appropriate types; for date we can display as String via toString
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        String customerId = CurrentSession.getCustomerId();
        if (customerId == null || customerId.isEmpty()) customerId = "C1001"; // fallback
        List<String> accounts = accountDAO.listAccountNumbersByCustomer(customerId);
        accountSelector.setItems(FXCollections.observableArrayList(accounts));
        if (!accounts.isEmpty()) {
            accountSelector.getSelectionModel().select(0);
            loadHistory(accounts.get(0));
        }

        accountSelector.setOnAction(e -> {
            String acc = accountSelector.getSelectionModel().getSelectedItem();
            if (acc != null) loadHistory(acc);
        });
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        SceneNavigator.goTo("Mainmenu.fxml");
    }

    private void loadHistory(String accountNumber) {
        List<String[]> rows = txDAO.listByAccount(accountNumber);
        List<Transaction> items = rows.stream()
                .map(r -> new Transaction(r[2], Double.parseDouble(r[3]), Double.parseDouble(r[4]), r[5]))
                .collect(Collectors.toList());
        historyTable.setItems(FXCollections.observableArrayList(items));
    }
}