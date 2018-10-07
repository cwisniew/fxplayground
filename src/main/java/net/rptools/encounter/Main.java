package net.rptools.encounter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Encounter Tool");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("net.rptools.encounter.i18n.i18n");
        Pane fxmlPane = FXMLLoader.load(getClass().getResource("/net/rptools/encounter/ui/MainWindow.fxml"), resourceBundle);

        Scene fxmlScene = new Scene(fxmlPane);
        primaryStage.setScene(fxmlScene);
        primaryStage.show();
    }
}
