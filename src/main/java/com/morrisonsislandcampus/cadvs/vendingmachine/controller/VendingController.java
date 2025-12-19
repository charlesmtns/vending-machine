package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.Util;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.*;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.ProductService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class VendingController {

    private static final int MIN_ON_HAND = 0;

    private static final BigDecimal MIN_BALANCE = BigDecimal.TWO;

    private final StageManager stageManager;

    private final ProductService productService;

    private final UserService userService;

    private final ShoppingCart shoppingCart;

    private final String username;

    @FXML
    private ImageView iCoke;

    @FXML
    private Text tCoke;

    @FXML
    private Spinner<Integer> sCoke;

    @FXML
    private ImageView iDietCoke;

    @FXML
    private Text tDietCoke;

    @FXML
    private Spinner<Integer> sDietCoke;

    @FXML
    private ImageView iFanta;

    @FXML
    private Text tFanta;

    @FXML
    private Spinner<Integer> sFanta;

    @FXML
    private ImageView iSprite;

    @FXML
    private Text tSprite;

    @FXML
    private Spinner<Integer> sSprite;

    @FXML
    private Button topUpButton;

    @FXML
    private Button payButton;

    @FXML
    private Button cashButton;

    @FXML
    private Text tWelcome;

    @FXML
    private Text tBalance;

    @FXML
    private HBox hCoke;

    @FXML
    public Text tCokeOutOfStock;

    @FXML
    public HBox hCokeDiet;

    @FXML
    public Text tDietCokeOutOfStock;

    @FXML
    public HBox hFanta;

    @FXML
    public Text tFantaOutOfStock;

    @FXML
    public HBox hSprite;

    @FXML
    public Text tSpriteOutOfStock;

    public VendingController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        this.username = (String) this.stageManager.getPrimaryStage().getUserData();
        FileService fileService = new FileService();
        this.productService = new ProductService(fileService);
        this.userService = new UserService(fileService);
        this.shoppingCart = new ShoppingCart();
    }


    @FXML
    private void initialize() {

        Optional<Product> oCoke = this.productService.find(DrinkEnum.COKE.getName());
        if (oCoke.isPresent()) {
            Product coke = oCoke.get();
            this.iCoke.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(coke.imagePath()))));
            this.tCoke.setText(Util.formatToEuro(coke.price()));
            if (coke.unitOnHand() > 0) {
                this.sCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, coke.unitOnHand()));
                this.sCoke.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, coke));
            } else {
                this.hCoke.setVisible(false);
                this.tCokeOutOfStock.setVisible(true);
            }
        }

        Optional<Product> oDietCoke = this.productService.find(DrinkEnum.DIET_COKE.getName());
        if (oDietCoke.isPresent()) {
            Product dietCoke = oDietCoke.get();
            this.iDietCoke.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(dietCoke.imagePath()))));
            this.tDietCoke.setText(Util.formatToEuro(dietCoke.price()));
            if (dietCoke.unitOnHand() > 0) {
                this.sDietCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, dietCoke.unitOnHand()));
                this.sDietCoke.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, dietCoke));
            } else {
                this.hCokeDiet.setVisible(false);
                this.tDietCokeOutOfStock.setVisible(true);
            }
        }

        Optional<Product> oFanta = this.productService.find(DrinkEnum.FANTA.getName());
        if (oFanta.isPresent()) {
            Product fanta = oFanta.get();
            this.iFanta.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(fanta.imagePath()))));
            this.tFanta.setText(Util.formatToEuro(fanta.price()));
            if (fanta.unitOnHand() > 0) {
                this.sFanta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, fanta.unitOnHand()));
                this.sFanta.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, fanta));
            } else {
                this.hFanta.setVisible(false);
                this.tFantaOutOfStock.setVisible(true);
            }
        }

        Optional<Product> oSprite = this.productService.find(DrinkEnum.SPRITE.getName());
        if (oSprite.isPresent()) {
            Product sprite = oSprite.get();
            this.iSprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(sprite.imagePath()))));
            this.tSprite.setText(Util.formatToEuro(sprite.price()));
            if (sprite.unitOnHand() > 0) {
                this.sSprite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, sprite.unitOnHand()));
                this.sSprite.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, sprite));
            } else {
                this.hSprite.setVisible(false);
                this.tSpriteOutOfStock.setVisible(true);
            }
        }

        Optional<User> optionalUser = this.userService.findByUsername(this.username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            this.tWelcome.setText("Welcome " + user.name());
            this.tBalance.setText("Balance: " + Util.formatToEuro(user.balance()));
            this.tBalance.setVisible(true);
            this.topUpButton.setVisible(true);
            if (user.balance().compareTo(MIN_BALANCE) < 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Balance");
                alert.setContentText("Your balance is under 2€, please top up now!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onExitButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.HOME, null);
    }

    @FXML
    private void onTopUpButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.TOPUP, this.username);
    }

    @FXML
    private void onCashButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.CASH, this.shoppingCart);
    }

    @FXML
    private void onPayButton() {

        this.userService.payDown(this.username, this.shoppingCart.totalQuotedPrice());

        for (ShoppingCartItem shoppingCartItem : this.shoppingCart.getShoppingCartItems()) {
            this.productService.updateUnitOnHand(shoppingCartItem.getProduct().name(), shoppingCartItem.getNumberItems());
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment result");
        alert.setHeaderText("Payment successfully");
        alert.showAndWait();

        this.stageManager.switchToNextScene(FxmlViewPath.VENDING, this.username);
    }

    private void drinkOperationObservable(Integer oldValue, Integer newValue, Product product) {
        Optional<User> optionalUser = this.userService.findByUsername(this.username);
        if (this.shoppingCart.getShoppingCartItems().isEmpty()) {
            this.shoppingCart.setShoppingCartItems(new ArrayList<>(Arrays.asList(new ShoppingCartItem(product, newValue, product.price()))));
        } else {
            Optional<ShoppingCartItem> shoppingCartItemOptional = this.shoppingCart.getShoppingCartItems().stream().filter(shoppingCartItem -> shoppingCartItem.getProduct().name().equals(product.name())).findFirst();
            if (shoppingCartItemOptional.isPresent()) {
                ShoppingCartItem shoppingCartItem = shoppingCartItemOptional.get();
                int numberItems = shoppingCartItem.getNumberItems();
                BigDecimal quotedPrice = shoppingCartItem.getQuotedPrice();
                if (newValue > oldValue) {
                    numberItems++;
                    quotedPrice = quotedPrice.add(product.price());
                } else {
                    numberItems--;
                    quotedPrice = quotedPrice.subtract(product.price());
                }
                shoppingCartItem.setNumberItems(numberItems);
                shoppingCartItem.setQuotedPrice(quotedPrice);
            } else {
                this.shoppingCart.getShoppingCartItems().add(new ShoppingCartItem(product, newValue, product.price()));
            }
        }

        Optional<ShoppingCartItem> shoppingCartItemOptional = this.shoppingCart.getShoppingCartItems().stream().filter(shoppingCartItem -> shoppingCartItem.getNumberItems() > 0).findFirst();
        if (shoppingCartItemOptional.isPresent()) {
            if (optionalUser.isEmpty()) {
                this.cashButton.setVisible(true);
            } else {
                User user = optionalUser.get();
                if (user.balance().compareTo(this.shoppingCart.totalQuotedPrice()) > 0) {
                    this.payButton.setVisible(true);
                } else {
                    this.payButton.setVisible(false);
                }
            }
        } else {
            this.cashButton.setVisible(false);
            this.payButton.setVisible(false);
        }
    }
}
