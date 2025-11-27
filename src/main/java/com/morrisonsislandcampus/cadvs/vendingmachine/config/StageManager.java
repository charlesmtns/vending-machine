package com.morrisonsislandcampus.cadvs.vendingmachine.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public enum StageManager {

    INSTANCE(new Stage());

    private static final String APPLICATION_TITLE = "Vending Machine";

    private Stage primaryStage;

    StageManager(Stage stage) {
        this.primaryStage = stage;
    }

    public StageManager getInstance() {
        return INSTANCE;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void switchScene(String fxmlPath) {
        this.primaryStage.setMinWidth(700);
        this.primaryStage.setMinHeight(850);
        this.primaryStage.setTitle(APPLICATION_TITLE);
        Parent rootNode = loadRootNode(fxmlPath);
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToNextScene(final String fxmlPath) {
        Parent rootNode = loadRootNode(fxmlPath);
        primaryStage.getScene().setRoot(rootNode);
        primaryStage.show();
    }

    private Parent loadRootNode(String fxmlPath) {
        Parent rootNode;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxmlPath));
            rootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rootNode;
    }
}
