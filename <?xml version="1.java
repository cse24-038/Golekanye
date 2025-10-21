<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.layout.*?>
<?import javafx.scene.control.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns:fx="http://javafx.com/fxml" fx:controller="banking.Controller">
    <top>
        <ToolBar>
            <Button fx:id="btnOpenInvestment" text="Open Investment" onAction="#handleOpenInvestment" />
            <Button fx:id="btnOpenCheque" text="Open Cheque" onAction="#handleOpenCheque" />
            <Button fx:id="btnWithdraw" text="Withdraw" onAction="#handleWithdraw" />
            <Button fx:id="btnSummary" text="Summary" onAction="#handleSummary" />
            <Button fx:id="btnExit" text="Exit" onAction="#handleExit" />
        </ToolBar>
    </top>
    <center>
        <TextArea fx:id="txtOutput" editable="false" wrapText="true" />
    </center>
    <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
    </padding>
</BorderPane>
