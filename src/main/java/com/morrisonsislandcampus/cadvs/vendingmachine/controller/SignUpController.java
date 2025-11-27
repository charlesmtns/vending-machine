package com.morrisonsislandcampus.cadvs.vendingmachine.controller;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.CountryService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.CountyService;
import com.morrisonsislandcampus.cadvs.vendingmachine.service.FileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class SignUpController {

    private static final List<String> GENDERS = List.of("Male", "Female");

    private static final String COUNTRY_DEFAULT = "Ireland";

    private final StageManager stageManager;

    private final CountryService countryService;

    private final CountyService countyService;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField pin;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField email;

    @FXML
    private TextField mobileNumber;

    @FXML
    private TextField address;

    @FXML
    private ComboBox<String> country;

    @FXML
    private ComboBox<String> county;

    @FXML
    private Label lCounty;

    public SignUpController() {
        this.stageManager = StageManager.INSTANCE.getInstance();
        FileService fileService = new FileService();
        this.countryService = new CountryService(fileService);
        this.countyService = new CountyService(fileService);
    }

    @FXML
    public void initialize() {
        this.gender.getItems().addAll(GENDERS);
        this.country.getItems().addAll(this.countryService.getAllCountries());
        this.county.getItems().addAll(this.countyService.getAllCounties());
    }


    @FXML
    private void onSelectCountry(ActionEvent event) {
        if (this.country.getSelectionModel().getSelectedItem().equals(COUNTRY_DEFAULT)) {
            this.county.setVisible(true);
            this.lCounty.setVisible(true);
        } else {
            this.county.setVisible(false);
            this.lCounty.setVisible(false);
        }
    }

    @FXML
    private void onExitBotton(ActionEvent event) throws IOException {
        this.stageManager.switchToNextScene(FxmlViewPath.HOME);
    }

    @FXML
    private void onSignUpButton(ActionEvent event) {

    }
}
