import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.event.ActionEvent;
import java.util.Date;

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
    public void initialize() {
        // Initialize the table columns
    }

    @FXML
    private void goToMainMenu(ActionEvent event) {
        // Add navigation logic to return to main menu
    }
}