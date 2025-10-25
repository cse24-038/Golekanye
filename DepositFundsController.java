package com.bank.controller;

import com.bank.boundary.TransactionBoundary;
import com.bank.boundary.CustomerBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.math.BigDecimal;

public class DepositFundsController implements CustomerBoundary, TransactionBoundary {

    @FXML
    private TextField amountField;

    @FXML
    public void handleDeposit() {
        try {
            BigDecimal amount = getDepositAmount();
        
            displayTransactionResult("Deposit of ₹" + amount + " successful. New Balance: [New Balance]");
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
        System.out.println("Cancelling deposit and returning to the Customer Menu.");
        
    }
}