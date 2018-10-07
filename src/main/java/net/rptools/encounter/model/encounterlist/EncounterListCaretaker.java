package net.rptools.encounter.model.encounterlist;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.rptools.encounter.memento.MementoCaretaker;
import net.rptools.encounter.model.encounter.Encounter;
import net.rptools.encounter.model.encounter.EncounterMemento;
import net.rptools.encounter.model.encounter.EncounterCaretaker;
import net.rptools.encounter.model.text.EncounterCSSList;
import net.rptools.encounter.model.text.EncounterCSSListOriginator;
import net.rptools.encounter.model.text.EncounterCSStListMemento;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class EncounterListCaretaker implements MementoCaretaker<EncounterListMemento> {

    /** The name of the file where the information is stored. */
    private static final String ENCOUNTER_FILE_NAME = "encounters.json";

    /** The memento which holds the state to be saved. */
    private EncounterListMemento memento;

    @Override
    public EncounterListMemento createMemento() {
        // TODO
        return null;
    }

    @Override
    public void setMemento(EncounterListMemento memento) {
        this.memento = memento;
    }

    @Override
    public void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException {
        generator.writeStartObject();
        generator.writeNumberField("version", 1);
        generator.writeArrayFieldStart("encounters");

        for (Encounter e : memento.getEncounters()) {
            EncounterCaretaker encounterOriginator = new EncounterCaretaker();
            encounterOriginator.setMemento(e.getObjectState());
            encounterOriginator.saveMemento(saveDirectory, generator);
        }
        generator.writeEndArray();

        EncounterCSSListOriginator eclo = new EncounterCSSListOriginator();
        eclo.setMemento(memento.getStylesheets().getObjectState());
        eclo.saveMemento(saveDirectory, generator);
        generator.writeEndObject();
        generator.flush();

    }

    @Override
    public Optional<EncounterListMemento> loadMemento(Path saveDirectory, JsonNode node) throws IOException {
        EncounterCaretaker o = new EncounterCaretaker();
        List<Encounter> encounterList = new LinkedList<>();

        for (JsonNode encounterNode : node.path("encounters")) {
            Optional<EncounterMemento> m = o.loadMemento(saveDirectory, encounterNode);
            m.ifPresent(encounterMemento -> encounterList.add(new Encounter(encounterMemento)));
        }

        EncounterCSSListOriginator eclo = new EncounterCSSListOriginator();
        Optional<EncounterCSStListMemento> eclm = eclo.loadMemento(saveDirectory, node);
        EncounterCSSList stylesheets = new EncounterCSSList();
        if (eclm.isPresent()) {
            stylesheets.setObjectState(eclm.get());
        }
        EncounterListMemento elm = new EncounterListMemento(encounterList, saveDirectory, stylesheets);
        return Optional.of(elm);
    }

    /**
     * Loads an {@link EncounterListMemento} from a zip file.
     * @param saveDirectory The directory path to load from.
     *
     * @return the memento containing the information.
     *
     * @throws IOException when there is a problem reading the file.
     */
    public Optional<EncounterListMemento> loadMemento(Path saveDirectory) throws IOException {
        Path encounterFile = saveDirectory.resolve(ENCOUNTER_FILE_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(encounterFile.toFile());
        return loadMemento(saveDirectory, node);
    }


    /**
     * Save the state to the file in the specified {@link Path}
     * @param saveDirectory The directory to save the information to
     *
     * @throws IOException if a problem occurs.
     */
    public void saveMemento(Path saveDirectory) throws IOException {
       StringWriter encounterWriter = new StringWriter();
       JsonGenerator generator = new JsonFactory().createGenerator(encounterWriter);
       generator.setPrettyPrinter(new DefaultPrettyPrinter());
       saveMemento(saveDirectory, generator);
        Path encounterListFile = saveDirectory.resolve(ENCOUNTER_FILE_NAME);
        Files.write(encounterListFile, encounterWriter.toString().getBytes());
    }
}
