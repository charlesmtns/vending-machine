package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignInController {

    private final StageManager stageManager;

    public SignInController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
    }

    private void initialize() {

    }

    @FXML
    private void onSignInButton(ActionEvent event) {

    }

    @FXML
    private void onSignUpLink() {
        this.stageManager.switchToNextScene(FxmlViewPath.SIGNUP);
    }

    @FXML
    public void onExitButton() {
        this.stageManager.switchToNextScene(FxmlViewPath.HOME);
    }
}
