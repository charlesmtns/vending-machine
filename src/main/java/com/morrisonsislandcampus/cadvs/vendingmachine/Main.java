package com.morrisonsislandcampus.cadvs.vendingmachine;

import com.morrisonsislandcampus.cadvs.vendingmachine.config.FxmlViewPath;
import com.morrisonsislandcampus.cadvs.vendingmachine.config.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;
    private StageManager stageManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stageManager = StageManager.INSTANCE.getInstance();
        this.stageManager.setPrimaryStage(stage);
        this.stage = stage;
        showHomeScene();
    }

    @Override
    public void stop() {
        this.stage.close();
    }

    private void showHomeScene() {
        this.stageManager.switchScene(FxmlViewPath.HOME);
    }

}
