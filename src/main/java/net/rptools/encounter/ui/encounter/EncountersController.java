package net.rptools.encounter.ui.encounter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTabPane;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.rptools.encounter.model.encounter.Encounter;
import net.rptools.encounter.model.encounterlist.EncounterList;
import net.rptools.encounter.model.text.EncounterCSS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class EncountersController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTabPane encounterDetailsTabpane;

    @FXML
    private Label encounterNameLabel;

    @FXML
    private Label challengeRatingLabel;

    @FXML
    private Label mapLocationLabel;


    @FXML
    private TableView<Encounter> encounterTableView;

    @FXML
    private StackPane encountersTopLevel;


    @FXML
    private Pane encountersPane;

    private final WebView descriptionWebView = new WebView();

    private final WebView notesWebView = new WebView();

    private StackPane topLeveStackPane;


    private Encounter encounter;
    private Pane encounterEditPane;


    private EncounterEditController encounterEditController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        // TODO Obviously :)
        Tab descriptionTab = new Tab(resources.getString("encounter.card.tab.description"), descriptionWebView);
        Tab notesTab = new Tab(resources.getString("encounter.card.tab.notes"), notesWebView);
        Tab participantTab = new Tab(resources.getString("encounter.card.tab.participants"));

        descriptionWebView.getEngine().loadContent("<b>This is description test</b>");
        notesWebView.getEngine().loadContent("<b>This is notes test</b>");
        encounterDetailsTabpane.getTabs().addAll(descriptionTab, notesTab, participantTab);

        TableColumn<Encounter, String> encounterNameCol = new TableColumn<>(resources.getString("encounter.table.heading.name"));
        encounterNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        encounterNameCol.setPrefWidth(180);

        TableColumn<Encounter, String> encounterRatingCol = new TableColumn<>(resources.getString("encounter.table.heading.rating"));
        encounterRatingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        encounterRatingCol.setPrefWidth(60);

        TableColumn<Encounter, String> encounterMapCol = new TableColumn<>(resources.getString("encounter.table.heading.map"));
        encounterMapCol.setPrefWidth(100);

        encounterTableView.getColumns().clear();
        encounterTableView.getColumns().add(encounterNameCol);
        encounterTableView.getColumns().add(encounterRatingCol);
        encounterTableView.getColumns().add(encounterMapCol);

        encounterTableView.setItems(EncounterList.getInstance().getEncounters());
        encounterTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setEncounter(newSelection);
            }
        });
        encounterTableView.setPlaceholder(new Label(resources.getString("encounter.table.empty")));

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/net/rptools/encounter/ui/EncounterEdit.fxml"),
                    resources
            );

            encounterEditPane = loader.load();
            encountersTopLevel.getChildren().add(encounterEditPane);
            encounterEditPane.setVisible(false);
            encounterEditController = loader.<EncounterEditController>getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void onEncounterAdd(ActionEvent event) {
        encounterEditController.editNewEncounter();
    }

    private void setEncounter(Encounter enc) {
        encounter = enc;
        encounterNameLabel.setText(encounter.getName());
        challengeRatingLabel.setText(encounter.getRating());
        WebEngine descriptionWebEngine = descriptionWebView.getEngine();
        descriptionWebEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                for (EncounterCSS css : EncounterList.getInstance().getStylesheets().getCssList()) {
                    Document doc = descriptionWebEngine.getDocument();
                    Element styleNode = doc.createElement("style");
                    Text styleContent = doc.createTextNode(css.getCss());
                    styleNode.appendChild(styleContent);
                    doc.getDocumentElement().getElementsByTagName("head").item(0).appendChild(styleNode);

                }
                System.out.println(descriptionWebEngine.executeScript("document.documentElement.innerHTML"));
            }
        });
        descriptionWebEngine.loadContent(encounter.getDescription());
    }

    public void setTopLevel(StackPane topLevel) {
        topLeveStackPane = topLevel;
    }
}
