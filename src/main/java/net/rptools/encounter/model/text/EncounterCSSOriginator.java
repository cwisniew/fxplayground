package net.rptools.encounter.model.text;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.rptools.encounter.memento.MementoCaretaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class EncounterCSSOriginator implements MementoCaretaker<EncounterCSSMemento> {



    private EncounterCSSMemento memento;


    @Override
    public EncounterCSSMemento createMemento() {
        // TODO
        return null;
    }

    @Override
    public void setMemento(EncounterCSSMemento memento) {
        this.memento = memento;
    }

    @Override
    public void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("id", memento.getId().toString());
        generator.writeStringField("name", memento.getName());



        Path cssFile = saveDirectory.resolve(memento.getId().toString());
        Files.write(cssFile, memento.getCss().getBytes());

        generator.writeEndObject();
        generator.flush();
    }

    @Override
    public Optional<EncounterCSSMemento> loadMemento(Path saveDirectory, JsonNode node) throws IOException {
        // TODO make this more robust
        UUID id = UUID.fromString(node.get("id").asText());
        String name = node.get("name").asText();

        Path cssFile = saveDirectory.resolve(id.toString());
        String css = new String(Files.readAllBytes(cssFile));

        EncounterCSSMemento memento = new EncounterCSSMemento(id, name, css);
        return Optional.of(memento);
    }
}
