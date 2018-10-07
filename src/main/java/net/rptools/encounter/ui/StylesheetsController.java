package net.rptools.encounter.ui;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import net.rptools.encounter.ui.editor.CSSCodeEditor;

public class StylesheetsController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXListView<?> stylesheetListView;

    @FXML
    private AnchorPane editorPane;

    private CSSCodeEditor codeEditor = new CSSCodeEditor("");

    @FXML
    void initialize() {
        assert stylesheetListView != null : "fx:id=\"stylesheetListView\" was not injected: check your FXML file 'Stylesheets.fxml'.";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        editorPane.getChildren().add(codeEditor);



        // TODO Obviously :)
        //Tab descriptionTab = new Tab(resources.getString("encounter.card.tab.description"), descriptionEditor);

    }
}
