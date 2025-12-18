package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class TopUpController {

    private static final BigDecimal UNIT_AMOUNT = BigDecimal.TEN;

    private BigDecimal currentAmountToBeCredited;

    private final StageManager stageManager;

    private final UserService userService;

    private final String username;

    @FXML
    private Text amount;

    @FXML
    public TextField cardHolderName;

    @FXML
    public TextField cardNumber;

    @FXML
    public TextField cardExpirationDate;

    @FXML
    public TextField cardCvv;

    @FXML
    public Button decrease;

    @FXML
    public Button payButton;

    public TopUpController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        this.username = (String) this.stageManager.getPrimaryStage().getUserData();
        FileService fileService = new FileService();
        this.userService = new UserService(fileService);
        this.currentAmountToBeCredited = UNIT_AMOUNT;
    }

    @FXML
    private void initialize() {

    }

    @FXML
    public void onDecrease() {
        if (this.currentAmountToBeCredited.compareTo(UNIT_AMOUNT) > 0) {
            this.currentAmountToBeCredited = this.currentAmountToBeCredited.subtract(UNIT_AMOUNT);
            this.amount.setText(this.currentAmountToBeCredited.toPlainString());
            if (this.currentAmountToBeCredited.compareTo(UNIT_AMOUNT) == 0) {
                this.decrease.setDisable(true);
            }
        }
    }

    @FXML
    public void onIncrease() {
        this.currentAmountToBeCredited = this.currentAmountToBeCredited.add(UNIT_AMOUNT);
        this.decrease.setDisable(false);
        this.amount.setText(this.currentAmountToBeCredited.toPlainString());
    }

    @FXML
    public void onExitButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.VENDING);
    }

    @FXML
    public void onPay() {
        String cardHolderName = this.cardHolderName.getText();
        String cardNumber = this.cardNumber.getText();
        String cardExpirationDate = this.cardExpirationDate.getText();
        String cardCvv = this.cardCvv.getText();

        if (!cardHolderName.isBlank() && !cardNumber.isBlank() && !cardExpirationDate.isBlank() && !cardCvv.isBlank()) {
            this.userService.topUp(this.username, this.currentAmountToBeCredited);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Top up result");
            alert.setHeaderText("Top up successful");
            alert.showAndWait();
            this.stageManager.switchToNextScene(FxmlViewPath.VENDING);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Top up result");
            alert.setHeaderText("All fields are required.");
            alert.showAndWait();
        }
    }
}
