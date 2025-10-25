package com.bank.controller;

import com.bank.boundary.CustomerBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.math.BigDecimal;

 
public class ViewBalanceController extends CustomerBoundary {

    @FXML
    private Label balanceLabel;

    @FXML
    public void initialize() {
    
        loadBalance();
    }

    private void loadBalance() {
    
        BigDecimal currentBalance = new BigDecimal("15250.75");
        balanceLabel.setText("₹ " + currentBalance.toString());
    }

    @FXML
    public void refreshBalance() {
        
        loadBalance();
        System.out.println("Balance refreshed.");
    }

    @Override
    public void goToMainMenu() {
        System.out.println("Navigating back to the Customer Menu.");
        
    }
}