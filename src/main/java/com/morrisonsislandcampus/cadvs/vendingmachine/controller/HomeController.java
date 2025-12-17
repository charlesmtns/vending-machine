package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {

    private final StageManager stageManager;

    public HomeController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
    }

    @FXML
    private void onSignInButton(ActionEvent event) {
        this.stageManager.switchToNextScene(FxmlViewPath.SIGNIN);
    }

    @FXML
    private void onSignUpButton(ActionEvent event) {
        this.stageManager.switchToNextScene(FxmlViewPath.SIGNUP);
    }

    @FXML
    private void onGuestButton(ActionEvent event) {
        this.stageManager.switchToNextScene(FxmlViewPath.VENDING);
    }
}
