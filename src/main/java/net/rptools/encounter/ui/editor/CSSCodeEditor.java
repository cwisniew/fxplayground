package net.rptools.encounter.ui.editor;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CSSCodeEditor extends StackPane {
    /** The {@link WebView} for displaying the CodeMirror editor. */
    private final WebView webView = new WebView();

    /** The HTML template for the code editor. */
    private static String editingHTMLTemplate;

    private static String emptyHTML;

    private static final URL BASE_HREF = CSSCodeEditor.class.getResource("/net/rptools/encounter/ui/editor/");


    static {
        try (Scanner scanner = new Scanner(
                CSSCodeEditor.class.getResourceAsStream("/net/rptools/encounter/ui/editor/HTMLCodeEditor.html"),
                StandardCharsets.UTF_8.name()
        )) {
            editingHTMLTemplate = scanner.useDelimiter("\\A").next();
        }
        try (Scanner scanner = new Scanner(
                CSSCodeEditor.class.getResourceAsStream("/net/rptools/encounter/ui/editor/EmptyHTMLFile.html"),
                StandardCharsets.UTF_8.name()
        )) {
            emptyHTML = scanner.useDelimiter("\\A").next();
        }
    }

    public CSSCodeEditor(String code) {

        if (code == null || code.length()==0) {
            setCode(emptyHTML);
        } else {
            setCode(code);
        }
       this.getChildren().add(webView);
    }

    private String applyEditingHTMLTemplate(String code) {
        return editingHTMLTemplate.replace("${code}", code).replace("${base_href}", BASE_HREF.toExternalForm());
    }

    public String getText() {
        return (String) webView.getEngine().executeScript("editor.getValue()");
    }

    public void setCode(String newCode) {
        webView.getEngine().loadContent(applyEditingHTMLTemplate(newCode));
        Platform.runLater(()->{
            JSObject jsWindow = (JSObject) webView.getEngine().executeScript("window");
            System.out.println(":::: -> " + jsWindow.getMember("editor"));
            //jsWindow.call("setValue","This is a TEST!");
        });
    }


}
