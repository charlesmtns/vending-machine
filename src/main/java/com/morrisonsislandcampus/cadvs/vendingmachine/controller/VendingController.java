package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.Util;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.Drink;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.DrinkEnum;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.ShoppingCart;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.DrinkService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class VendingController {

    private static final int MIN_ON_HAND = 0;

    private final StageManager stageManager;

    private final DrinkService drinkService;

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

    public VendingController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        this.username = (String) this.stageManager.getPrimaryStage().getUserData();
        FileService fileService = new FileService();
        this.drinkService = new DrinkService(fileService);
        this.userService = new UserService(fileService);
        this.shoppingCart = new ShoppingCart();
    }


    @FXML
    private void initialize() {

        Optional<Drink> oCoke = this.drinkService.find(DrinkEnum.COKE.getName());
        if (oCoke.isPresent()) {
            Drink coke = oCoke.get();
            this.iCoke.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(coke.imagePath()))));
            this.tCoke.setText(Util.formatToEuro(coke.price()));
            this.sCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, coke.unitOnHand()));
            this.sCoke.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, coke));
        }

        Optional<Drink> oDietCoke = this.drinkService.find(DrinkEnum.DIET_COKE.getName());
        if (oDietCoke.isPresent()) {
            Drink dietCoke = oDietCoke.get();
            this.iDietCoke.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(dietCoke.imagePath()))));
            this.tDietCoke.setText(Util.formatToEuro(dietCoke.price()));
            this.sDietCoke.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, dietCoke.unitOnHand()));
            this.sDietCoke.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, dietCoke));
        }

        Optional<Drink> oFanta = this.drinkService.find(DrinkEnum.FANTA.getName());
        if (oFanta.isPresent()) {
            Drink fanta = oFanta.get();
            this.iFanta.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(fanta.imagePath()))));
            this.tFanta.setText(Util.formatToEuro(fanta.price()));
            this.sFanta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, fanta.unitOnHand()));
            this.sFanta.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, fanta));
        }

        Optional<Drink> oSprite = this.drinkService.find(DrinkEnum.SPRITE.getName());
        if (oSprite.isPresent()) {
            Drink sprite = oSprite.get();
            this.iSprite.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(sprite.imagePath()))));
            this.tSprite.setText(Util.formatToEuro(sprite.price()));
            this.sSprite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_ON_HAND, sprite.unitOnHand()));
            this.sSprite.valueProperty().addListener((o, oldValue, newValue) -> this.drinkOperationObservable(oldValue, newValue, sprite));
        }

        Optional<User> optionalUser = this.userService.findByUsername(this.username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            this.tWelcome.setText("Welcome " + user.name());
            this.tBalance.setText("Balance: " + Util.formatToEuro(user.balance()));
            this.tBalance.setVisible(true);
            this.topUpButton.setVisible(true);
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

    }

    private void drinkOperationObservable(Integer oldValue, Integer newValue, Drink drink) {
        int numberItems = this.shoppingCart.getNumberItems();
        BigDecimal quotedPrice = this.shoppingCart.getQuotedPrice();
        if (newValue > oldValue) {
            numberItems++;
            quotedPrice = quotedPrice.add(drink.price());
        } else {
            numberItems--;
            quotedPrice = quotedPrice.subtract(drink.price());
        }

        if (numberItems > 0) {
            if (this.username == null) {
                this.cashButton.setVisible(true);
            } else {
                this.payButton.setVisible(true);
            }
        } else {
            this.cashButton.setVisible(false);
            this.payButton.setVisible(false);
        }
        this.shoppingCart.setNumberItems(numberItems);
        this.shoppingCart.setQuotedPrice(quotedPrice);
    }
}
