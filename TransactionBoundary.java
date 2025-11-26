import java.math.BigDecimal;

public interface TransactionBoundary {
    BigDecimal getDepositAmount() throws NumberFormatException;
    BigDecimal getWithdrawalAmount() throws NumberFormatException;
    void displayTransactionResult(String message);
}