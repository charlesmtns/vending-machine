package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.entity.User;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.CountryService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.CountyService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class SignUpController {

    private static final List<String> GENDERS = List.of("Male", "Female");

    private static final String COUNTRY_DEFAULT = "Ireland";

    private static final String ALERT_TITLE = "Sign up result";

    private static final String ALERT_HEADER_TEXT_ERROR = "Error";

    private static final String ALERT_HEADER_TEXT_SUCCESS = "Success";

    private final StageManager stageManager;

    private final CountryService countryService;

    private final CountyService countyService;

    private final UserService userService;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField username;

    @FXML
    private PasswordField pin;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private ComboBox<String> country;

    @FXML
    private ComboBox<String> county;

    @FXML
    private Label lCounty;

    @FXML
    private CheckBox terms;

    public SignUpController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        FileService fileService = new FileService();
        this.countryService = new CountryService(fileService);
        this.countyService = new CountyService(fileService);
        this.userService = new UserService(fileService);
    }

    @FXML
    public void initialize() {
        this.gender.getItems().addAll(GENDERS);
        this.country.getItems().addAll(this.countryService.getAllCountries());
        this.county.getItems().addAll(this.countyService.getAllCounties());
    }


    @FXML
    private void onSelectCountry() {
        if (this.country.getSelectionModel().getSelectedItem().equals(COUNTRY_DEFAULT)) {
            this.county.setVisible(true);
            this.lCounty.setVisible(true);
        } else {
            this.county.setVisible(false);
            this.lCounty.setVisible(false);
        }
    }

    @FXML
    private void onExitBotton() {
        this.stageManager.switchToNextScene(FxmlViewPath.HOME);
    }

    @FXML
    private void onSignUpButton() {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String username = this.username.getText();
        String pin = this.pin.getText();
        LocalDate birthday = this.birthday.getValue();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ALERT_TITLE);
        alert.setHeaderText(ALERT_HEADER_TEXT_ERROR);

        if (firstName.isBlank() || lastName.isBlank() || username.isBlank() || pin.isBlank() || birthday == null) {
            alert.setContentText("Fields marked with * are required");
            alert.showAndWait();
            return;
        }

        if (birthday.isAfter(LocalDate.now().minusYears(18))) {
            alert.setContentText("Users must be over 18 years old.");
            alert.showAndWait();
            return;
        }

        if (!this.terms.isSelected()) {
            alert.setContentText("You must accept the terms");
            alert.showAndWait();
            return;
        }

        this.userService.save(new User(firstName, username, pin, BigDecimal.ZERO));

        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText(ALERT_HEADER_TEXT_SUCCESS);
        alert.setContentText("The user was created successfully.");
        alert.showAndWait();

        this.stageManager.switchToNextScene(FxmlViewPath.HOME);
    }
}
