package net.rptools.encounter.ui.encounter;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import net.rptools.encounter.model.encounter.Encounter;
import net.rptools.encounter.model.encounterlist.EncounterList;
import net.rptools.encounter.ui.editor.HTMLCodeEditor;

import java.net.URL;
import java.util.ResourceBundle;

public class EncounterEditController implements Initializable{


    @FXML
    private Pane encounterEditorPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField encounterNameTextField;

    @FXML
    private JFXTextField challengeRatingTextField;

    @FXML
    private JFXTextField mapNameTextField;

    @FXML
    private JFXTextField mapKeyTextField;


    @FXML
    private JFXTabPane encounterDetailsTabpane;


    private HTMLCodeEditor descriptionEditor = new HTMLCodeEditor("");


    private Encounter encounter;

    private boolean newEncounterMode = false;

    @FXML
    void onCancel(ActionEvent event) {
        encounterEditorPane.toBack();
        encounterEditorPane.setVisible(false);
    }

    @FXML
    void onOk(ActionEvent event) {
        encounterEditorPane.toBack();
        encounterEditorPane.setVisible(false);
        // TODO

        encounter.setName(encounterNameTextField.getText());
        encounter.setRating(challengeRatingTextField.getText());
        encounter.setDescription(descriptionEditor.getText());

        EncounterList el = EncounterList.getInstance();
        if (!el.getEncounters().contains(encounter)) {
            el.addEncounter(encounter);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        // TODO Obviously :)
        Tab descriptionTab = new Tab(resources.getString("encounter.card.tab.description"), descriptionEditor);
        Tab notesTab = new Tab(resources.getString("encounter.card.tab.notes"));//), notesWebView);
        Tab participantTab = new Tab(resources.getString("encounter.card.tab.participants"));
        encounterDetailsTabpane.getTabs().addAll(descriptionTab, notesTab, participantTab);


    }

    public void editNewEncounter() {
        newEncounterMode = true;
        StringBuilder name = new StringBuilder();
        name.append(resources.getString("new.encounter.name.prefix"));
        name.append("#").append(EncounterList.getInstance().getEncounters().size() + 1);
        editEncounter(new Encounter(name.toString()));

    }

    public void editEncounter(Encounter enc) {
        encounter = enc;
        encounterEditorPane.toFront();
        encounterEditorPane.setVisible(true);
        encounterNameTextField.setText(enc.getName());
    }
}
