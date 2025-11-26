import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
 
public class ViewTransactionhistory {

    @FXML
    private TableView<Transaction> historyTable;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> typeColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;
    @FXML
    private TableColumn<Transaction, String> descriptionColumn;

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTimestamp().toLocalDate().toString()));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        historyTable.setItems(getSampleTransactions());
    }

    private ObservableList<Transaction> getSampleTransactions() {
        return FXCollections.observableArrayList(
                new Transaction("Deposit", 5000.00, 5000.00, "Initial Deposit"),
                new Transaction("Withdraw", 500.00, 4500.00, "ATM Withdrawal"),
                new Transaction("Transfer", 250.50, 4249.50, "Online Bill Payment"),
                new Transaction("Deposit", 1500.00, 5749.50, "Salary Credit")
        );
    }

    public void goToMainMenu() {
        System.out.println("Returning to the Customer Menu.");
    }
}