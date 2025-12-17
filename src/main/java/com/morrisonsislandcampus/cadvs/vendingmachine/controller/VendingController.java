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

    private final User user;

    private final ShoppingCart shoppingCart;

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
    private Button cashButton;

    @FXML
    private Text tWelcome;

    @FXML
    private Text tBalance;

    @FXML
    private Text vBalance;

    public VendingController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        FileService fileService = new FileService();
        this.drinkService = new DrinkService(fileService);
        this.user = this.stageManager.getUser();
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

        if (this.user != null) {
            this.tWelcome.setText("Welcome " + this.user.name());
            this.vBalance.setText(Util.formatToEuro(this.user.balance()));
            this.vBalance.setVisible(true);
            this.tBalance.setVisible(true);
        }
    }

    @FXML
    private void onExitBotton() {
        this.stageManager.setUser(null);
        this.stageManager.switchToNextScene(FxmlViewPath.HOME);
    }

    @FXML
    private void onTopUpBotton() {

    }

    @FXML
    private void onCashBotton() {
        this.stageManager.switchToNextScene(FxmlViewPath.CASH, this.shoppingCart);
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
            if (this.stageManager.getUser() == null) {
                this.cashButton.setVisible(true);
            } else {
                this.topUpButton.setVisible(true);
            }
        } else {
            this.cashButton.setVisible(false);
            this.topUpButton.setVisible(false);
        }
        this.shoppingCart.setNumberItems(numberItems);
        this.shoppingCart.setQuotedPrice(quotedPrice);
    }
}
