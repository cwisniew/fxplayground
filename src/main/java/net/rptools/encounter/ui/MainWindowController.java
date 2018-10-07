package net.rptools.encounter.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import net.rptools.encounter.model.encounterlist.EncounterList;
import net.rptools.encounter.persistence.EncounterPersistence;
import net.rptools.encounter.ui.encounter.EncountersController;

public class MainWindowController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane topLevelStackPane;

    @FXML
    private StackPane contentPane;


    private Pane encountersPane;

    private Pane styleSheetsPane;

    private EncountersController encountersController;

    private StylesheetsController stylesheetsController;



    @FXML
    void closeMenu(ActionEvent event) {

    }

    @FXML
    void editRuleSets(ActionEvent event) {

    }

    @FXML
    void newEncounter(ActionEvent event) {

    }

    @FXML
    void newEncounterList(ActionEvent event) {

    }

    @FXML
    void openMenu(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(resources.getString("save.file.title"));

        File save = directoryChooser.showDialog(topLevelStackPane.getScene().getWindow());
        if (save != null) {
            EncounterPersistence encounterPersistence = new EncounterPersistence();
            Optional<String> error = encounterPersistence.checkValidSaveFile(save.toPath());
            if (error.isPresent()) {
                JFXDialog dialog = new JFXDialog();
                JFXDialogLayout content = new JFXDialogLayout();

                content.setBody(new Label(error.get()));
                JFXButton okButton = new JFXButton(resources.getString("button.ok"));
                okButton.setOnAction(a -> dialog.close());
                content.setActions(okButton);
                dialog.setContent(content);
                dialog.show(topLevelStackPane);
            } else {
                try {
                    encounterPersistence.load(save.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            EncounterList.getInstance().getEncounters().forEach(encounter -> System.out.println(encounter.getName()));
        }


    }

    @FXML
    void saveMenu(ActionEvent event) {
        SaveDirectoryDialog saveDirectoryDialog = new SaveDirectoryDialog(topLevelStackPane, resources);
        saveDirectoryDialog.showSaveDialog();
    }

    @FXML
    void initialize() {
        assert topLevelStackPane != null : "fx:id=\"topLevelStackPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert contentPane != null : "fx:id=\"contentPane\" was not injected: check your FXML file 'MainWindow.fxml'.";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        try {
            FXMLLoader encountersPaneLoader = new FXMLLoader(
                    getClass().getResource("/net/rptools/encounter/ui/Encounters.fxml"),
                    resources
            );
            encountersPane = encountersPaneLoader.load();

            encountersController = encountersPaneLoader.getController();
            encountersController.setTopLevel(topLevelStackPane);

            FXMLLoader styleSheetsEditorLoader = new FXMLLoader(
                    getClass().getResource("/net/rptools/encounter/ui/Stylesheets.fxml"),
                    resources
            );
            styleSheetsPane = styleSheetsEditorLoader.load();
            stylesheetsController = styleSheetsEditorLoader.getController();


            contentPane.getChildren().addAll(encountersPane, styleSheetsPane);
            styleSheetsPane.toBack();
            styleSheetsPane.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editStylesheets(ActionEvent event) {
        styleSheetsPane.setVisible(true);
        styleSheetsPane.toFront();
        System.out.println("Debug: here!");
    }
}
