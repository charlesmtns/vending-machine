package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.Util;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.ShoppingCart;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.ShoppingCartItem;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.math.BigDecimal;

public class CashPaymentController {

    private static final BigDecimal UNIT_AMOUNT = BigDecimal.TWO;

    private BigDecimal currentAmountToBePaid;

    private final StageManager stageManager;

    private final ShoppingCart shoppingCart;

    private final ProductService productService;

    @FXML
    public Text amount;

    @FXML
    public Button decrease;

    @FXML
    public Text vendingTotal;

    @FXML
    public Button payButton;

    public CashPaymentController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        this.shoppingCart = (ShoppingCart) this.stageManager.getPrimaryStage().getUserData();
        this.currentAmountToBePaid = UNIT_AMOUNT;
        FileService fileService = new FileService();
        this.productService = new ProductService(fileService);
    }

    @FXML
    private void initialize() {
        this.vendingTotal.setText("Vending total amount: " + Util.formatToEuro(this.shoppingCart.totalQuotedPrice()));

        if (UNIT_AMOUNT.compareTo(this.shoppingCart.totalQuotedPrice()) > 0) {
            this.payButton.setDisable(false);
        }
    }

    @FXML
    private void onExitButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.VENDING, null);
    }

    @FXML
    private void onDecrease() {
        if (this.currentAmountToBePaid.compareTo(UNIT_AMOUNT) > 0) {
            this.currentAmountToBePaid = this.currentAmountToBePaid.subtract(UNIT_AMOUNT);
            this.amount.setText(this.currentAmountToBePaid.toPlainString());
            if (this.currentAmountToBePaid.compareTo(UNIT_AMOUNT) == 0) {
                this.decrease.setDisable(true);
            }
        }

        if (this.currentAmountToBePaid.compareTo(this.shoppingCart.totalQuotedPrice()) < 0) {
            this.payButton.setDisable(true);
        }
    }

    @FXML
    private void onIncrease() {
        this.currentAmountToBePaid = this.currentAmountToBePaid.add(UNIT_AMOUNT);
        if (this.currentAmountToBePaid.compareTo(this.shoppingCart.totalQuotedPrice()) >= 0) {
            this.payButton.setDisable(false);
        }
        this.decrease.setDisable(false);
        this.amount.setText(this.currentAmountToBePaid.toPlainString());
    }

    @FXML
    private void onPay() {
        for (ShoppingCartItem shoppingCartItem : this.shoppingCart.getShoppingCartItems()) {
            this.productService.updateUnitOnHand(shoppingCartItem.getProduct().name(), shoppingCartItem.getNumberItems());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment result");
        alert.setHeaderText("Payment successfully");
        if (this.currentAmountToBePaid.compareTo(this.shoppingCart.totalQuotedPrice()) > 0) {
            alert.setContentText("That machine does not give change");
        }
        alert.showAndWait();
        this.stageManager.switchToNextScene(FxmlViewPath.HOME, null);
    }
}
