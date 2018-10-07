package net.rptools.encounter.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.rptools.encounter.model.encounterlist.EncounterList;
import net.rptools.encounter.model.encounterlist.EncounterListMemento;
import net.rptools.encounter.model.encounterlist.EncounterListCaretaker;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class EncounterPersistence {

    /** The extension used for the save file name. */
    public static final String SAVE_FILE_EXTENSION = "enct";

    /** The default file name used for new saves. */
    public static final String DEFAULT_FILE_NAME = "encounter." + SAVE_FILE_EXTENSION;

    /** The file name for the save properties. */
    private static final String ENCOUNTER_TOOL_PROPERTY_FILE_NAME = "encountertool_properties.json";

    /** The key name for the save file type. */
    private static final String ENCOUNTER_TOOL_SAVE_NAME_KEY = "name";
    /** The name of the save file type. */
    private static final String ENCOUNTER_TOOL_SAVE_NAME = "Encounter Tool";

    /** The key name for the save version. */
    private static final String ENCOUNTER_TOOL_SAVE_VERSION_KEY = "saveVersion";
    /** The save version. */
    private static final String ENCOUNTER_TOOL_SAVE_VERSION = "1";


    /**
     * Returns the property information read from the save directory.
     *
     * @param saveFile The directory the encounters are saved in.
     * @return the properties in the save directory.
     *
     * @throws IOException when an error occurs.
     */
    private Optional<Map<String, String>> getSaveProperties(Path saveFile) throws IOException {
        Map<String,String> propMap;
        Path propFile = saveFile.resolve(ENCOUNTER_TOOL_PROPERTY_FILE_NAME);
        try (InputStream in = Files.newInputStream(propFile, StandardOpenOption.READ)) {
            ObjectMapper objectMapper = new ObjectMapper();
            propMap = objectMapper.readValue(in, new TypeReference<HashMap<String,String>>() {});

            return Optional.of(propMap);
        }
    }

    /**
     * Creates the property file entry in the save file.
     *
     * @param saveDirectory the directory that the information is saved into.
     */
    private void createSaveProperties(Path saveDirectory) throws IOException {
        Map<String,String> propMap = Map.of(
                ENCOUNTER_TOOL_SAVE_VERSION_KEY, ENCOUNTER_TOOL_SAVE_VERSION,
                ENCOUNTER_TOOL_SAVE_NAME_KEY, ENCOUNTER_TOOL_SAVE_NAME
        );

        Path propFile = saveDirectory.resolve(ENCOUNTER_TOOL_PROPERTY_FILE_NAME);
        Files.write(propFile, new ObjectMapper().writeValueAsString(propMap).getBytes());
    }


    /**
     * Persist the current state to the directory specified.
     *
     * @param directory The directory to save the information to.
     */
    public void persist(Path directory) {
        EncounterList.getInstance().setSaveFile(directory);
        EncounterListCaretaker o = new EncounterListCaretaker();
        o.setMemento(EncounterList.getInstance().getObjectState());
        try {
            o.saveMemento(directory);
            createSaveProperties(directory);
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if a file is a valid save file or not.
     *
     * @param saveFile The file to check.
     * @return the string describing the error if the save file is not valid. Will be empty if the
     * file is valid.
     */
    public Optional<String> checkValidSaveFile(Path saveFile) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("net.rptools.encounter.i18n.i18n");
        if (Files.exists(saveFile)) {
            try {
                Optional<Map<String, String>> propMap = getSaveProperties(saveFile);
                if (!propMap.isPresent()) {
                    return Optional.of(resourceBundle.getString("save.file.notsave"));
                } else {
                    if (!ENCOUNTER_TOOL_SAVE_NAME.equals(propMap.get().get(ENCOUNTER_TOOL_SAVE_NAME_KEY))) {
                        return Optional.of(resourceBundle.getString("save.directory.notempty"));
                    }
                }
            } catch (IOException e) {
                return Optional.of(e.getLocalizedMessage());
            }
        }

        return Optional.empty();
    }

    public void load(Path path) throws IOException {
        EncounterListCaretaker o = new EncounterListCaretaker();
        Optional<EncounterListMemento> m = o.loadMemento(path);
        m.ifPresent(encounterListMemento -> EncounterList.getInstance().setObjectState(encounterListMemento));
    }
}
