package net.rptools.encounter.model.encounter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import net.rptools.encounter.memento.MementoCaretaker;
import net.rptools.encounter.model.team.Team;
import net.rptools.encounter.model.team.TeamCaretaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EncounterCaretaker implements MementoCaretaker<EncounterMemento> {

    /** The sub directory that the description is saved in. */
    private static String DESCRIPTION_DIRECTORY = "descriptions";

    /** The memento that holds the information to be saved. */
    private EncounterMemento memento;


    /**
     * Returns the name of the file where the description is saved.
     *
     * @return the name of the file for the description.
     */
    private String getDescriptionFileName() {
        return memento.getId().toString() + ".html";
    }

    @Override
    public EncounterMemento createMemento() {
        // TODO
        return null;
    }

    @Override
    public void setMemento(EncounterMemento memento) {
        this.memento = memento;
    }

    private void saveFields(JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("id", memento.getId().toString());
        generator.writeStringField("name",  memento.getName());
        generator.writeStringField("rating",  memento.getRating());
        generator.writeStringField("descriptionFile", getDescriptionFileName());
        generator.writeArrayFieldStart("teams");
    }

    @Override
    public void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException {
        saveFields(generator);

        for (Team tm : memento.getTeams()) {
            TeamCaretaker to = new TeamCaretaker();
            to.setMemento(tm.getObjectState());
            to.saveMemento(saveDirectory, generator);
        }
        generator.writeEndArray();
        generator.writeEndObject();
        generator.flush();

        Path descriptionDir = saveDirectory.resolve(DESCRIPTION_DIRECTORY);
        Files.createDirectories(descriptionDir);

        Path descriptionFile = descriptionDir.resolve(getDescriptionFileName());
        Files.write(descriptionFile,memento.getDescription().getBytes());
    }

    @Override
    public Optional<EncounterMemento> loadMemento(Path saveDirectory, JsonNode node) throws IOException {
        // TODO make this more robust
        UUID id = UUID.fromString(node.get("id").asText());
        String name = node.get("name").asText();
        String rating = node.get("rating").asText();
        String descriptionFile = node.get("descriptionFile").asText();
        List<Team> teams = new LinkedList<>();

        String description = loadDescription(saveDirectory, descriptionFile);

        EncounterMemento em = new EncounterMemento(id, name, teams, description, rating);
        return Optional.of(em);
    }

    /**
     * Loads an encounter description from the zip file.
     *
     * @param saveDirectory The zip file to load the description from.
     *
     * @param descriptionFile The name of the file that contains the description.
     *
     * @return the description for the encounter.
     */
    private String loadDescription(Path saveDirectory, String descriptionFile) throws IOException {
        Path descriptionDir = saveDirectory.resolve(DESCRIPTION_DIRECTORY);
        Files.createDirectories(descriptionDir);

        Path filename = descriptionDir.resolve(descriptionFile);
        return new String(Files.readAllBytes(filename));
    }
}
