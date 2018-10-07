package net.rptools.encounter.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import net.rptools.encounter.model.encounterlist.EncounterList;
import net.rptools.encounter.persistence.EncounterPersistence;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

public class SaveDirectoryDialog {

    private static final String DEFAULT_SAVE_DIR_NAME = "Encounters." + EncounterPersistence.SAVE_FILE_EXTENSION;

    private static final String DEFAULT_SAVE_PARENT_NAME = System.getProperty("user.home");

    private final StackPane parent;
    private final ResourceBundle resources;

    private void createDialog(String title, String okText, SelectedPathCallBack okCallback) {
        JFXTextField directoryNameTextField = new JFXTextField();
        directoryNameTextField.setPromptText(resources.getString("save.directory"));
        directoryNameTextField.getStyleClass().add("save-dir-dialog-textfield");

        JFXTextField parentDirectoryNameTextField = new JFXTextField();
        parentDirectoryNameTextField.setPromptText(resources.getString("save.parent.directory"));
        parentDirectoryNameTextField.getStyleClass().add("save-dir-dialog-textfield");

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog();
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("save-dir-dialog-heading");
        layout.setHeading(titleLabel);

        Glyph editGlyph = new Glyph("FontAwesome", FontAwesome.Glyph.ELLIPSIS_H);
        JFXButton launchDirDialog = new JFXButton("", editGlyph);
        launchDirDialog.getStyleClass().add("save-dir-dialog-button");
        launchDirDialog.setButtonType(JFXButton.ButtonType.FLAT);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(parentDirectoryNameTextField, launchDirDialog);
        HBox.setHgrow(parentDirectoryNameTextField, Priority.ALWAYS);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(directoryNameTextField, hBox);
        layout.setBody(vBox);

        JFXButton cancel = new JFXButton(resources.getString("button.cancel"));
        cancel.getStyleClass().add("save-dir-dialog-button");
        cancel.setButtonType(JFXButton.ButtonType.FLAT);
        cancel.setOnAction(e -> dialog.close());

        JFXButton ok = new JFXButton(okText);
        ok.getStyleClass().add("save-dir-dialog-button");
        ok.setButtonType(JFXButton.ButtonType.FLAT);
        ok.setOnAction(event -> {
            // TODO: Check that the text fields are not empty.
            Path selectedPath = Paths.get(parentDirectoryNameTextField.getText()).resolve(directoryNameTextField.getText());
            dialog.close();
            okCallback.selectedPath(selectedPath);
        });

        layout.setActions(cancel, ok);

        dialog.setContent(layout);
        dialog.getStylesheets().add(getClass().getResource("/net/rptools/encounter/ui/EncounterToolUI.css").toExternalForm());

        Optional<Path> saveDirectory = EncounterList.getInstance().getSaveFile();
        String parentName;
        String directoryName;
        if (saveDirectory.isPresent()) {
            Path dir = saveDirectory.get();
            directoryName = dir.getFileName().toString();
            parentName = dir.getParent().toString();
        } else {
            directoryName = DEFAULT_SAVE_DIR_NAME;
            parentName = DEFAULT_SAVE_PARENT_NAME;
        }

        launchDirDialog.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(resources.getString("save.file.title"));
            directoryChooser.setInitialDirectory(new File(parentDirectoryNameTextField.getText()));
            File save = directoryChooser.showDialog(parent.getScene().getWindow());
            if (save != null) {
                parentDirectoryNameTextField.setText(save.toString());
            }
        });

        directoryNameTextField.setText(directoryName);
        parentDirectoryNameTextField.setText(parentName);
        dialog.show(parent);
    }

    SaveDirectoryDialog(StackPane parent, ResourceBundle resources) {
        this.parent = parent;
        this.resources = resources;
    }

    public void showSaveDialog() {
        createDialog(resources.getString("save.directory.title"), resources.getString("save.button.label"), path -> {
            EncounterPersistence encounterPersistence = new EncounterPersistence();
            encounterPersistence.persist(path);
        });

    }
}
