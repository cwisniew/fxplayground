package net.rptools.encounter.model.text;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.rptools.encounter.memento.MementoCaretaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class EncounterCSSListOriginator implements MementoCaretaker<EncounterCSStListMemento> {

    private static final String CSS_DIRECTORY_NAME = "css";

    private EncounterCSStListMemento memento;

    @Override
    public EncounterCSStListMemento createMemento() {
        // TODO
        return null;
    }

    @Override
    public void setMemento(EncounterCSStListMemento memento) {
        this.memento = memento;
    }

    @Override
    public void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException {

        Path cssDir = saveDirectory.resolve(CSS_DIRECTORY_NAME);
        Files.createDirectories(cssDir);

        generator.writeArrayFieldStart("stylesheets");
        for (EncounterCSS css : memento.getCssList()) {
            EncounterCSSOriginator o = new EncounterCSSOriginator();
            o.setMemento(css.getObjectState());
            o.saveMemento(cssDir, generator);
        }
        generator.writeEndArray();
    }

    @Override
    public Optional<EncounterCSStListMemento> loadMemento(Path saveDirectory, JsonNode node) throws IOException {
        EncounterCSSOriginator o = new EncounterCSSOriginator();
        Path cssDir = saveDirectory.resolve(CSS_DIRECTORY_NAME);
        List<EncounterCSS> cssList = new LinkedList<>();

        for (JsonNode cssNode : node.path("stylesheets")) {
            Optional<EncounterCSSMemento> m = o.loadMemento(cssDir, cssNode);
            if (m.isPresent()) {
                cssList.add(new EncounterCSS(m.get()));
            }
        }

        if (cssList.isEmpty()) {
            cssList = defaultCCSList();
        }

        EncounterCSStListMemento tclm = new EncounterCSStListMemento(cssList);

        return Optional.of(tclm);
    }

    private List<EncounterCSS> defaultCCSList() {
        List<EncounterCSS> cssList = new ArrayList<>();

        cssList.add(new EncounterCSS("Default", ".h1 {\n" +
                "    background-color: black;\n" +
                "    color: whitesmoke;\n" +
                "    font-weight: bold;\n" +
                "}"));
        return cssList;
    }
}
