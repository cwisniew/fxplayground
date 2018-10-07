package net.rptools.encounter.model.team;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.rptools.encounter.memento.MementoCaretaker;
import net.rptools.encounter.model.participant.Participant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class TeamCaretaker implements MementoCaretaker<TeamMemento> {

    /** The memento which holds the state to be saved. */
    private TeamMemento memento;

    @Override
    public TeamMemento createMemento() {
        // TODO
        return null;
    }

    @Override
    public void setMemento(TeamMemento memento) {
        this.memento = memento;
    }

    @Override
    public void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("name", memento.getName());
        generator.writeArrayFieldStart("members");
        for (Participant p : memento.getMembers()) {
            // TODO save the participants.
        }
        generator.writeEndArray();
    }

    @Override
    public Optional<TeamMemento> loadMemento(Path saveDirectory, JsonNode node) throws IOException {
        return Optional.empty();
    }
}
