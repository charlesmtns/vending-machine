package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.SignInService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Optional;

public class SignInController {

    private final StageManager stageManager;

    private final SignInService signInService;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField pin;

    public SignInController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        FileService fileService = new FileService();
        UserService userService = new UserService(fileService);
        this.signInService = new SignInService(userService);
    }

    @FXML
    private void onSignInButton() {
        String userName = this.userName.getText();
        String pin = this.pin.getText();

        if (userName != null && pin != null) {
            Optional<User> user = this.signInService.signIn(userName, pin);
            if (user.isPresent()) {
                this.stageManager.switchToNextScene(FxmlViewPath.VENDING, user.get().username());
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Authentication Error");
        alert.showAndWait();
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
