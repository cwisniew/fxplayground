package net.rptools.encounter.model.encounterlist;

import net.rptools.encounter.model.encounter.Encounter;
import net.rptools.encounter.model.text.EncounterCSSList;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class EncounterListMemento {

    /** The encounters that make up the list. */
    private final List<Encounter> encounters = new LinkedList<>();

    /** The path of save file. */
    private Path saveFile;

    /** The stylesheets used for encounter text. */
    private EncounterCSSList stylesheets;

    /**
     * Creates an <code>EncounterListMemento</code> with minimal state.
     */
    public EncounterListMemento() {
    }

    /**
     * Creates an <code>EncounterListMemento</code>.
     *
     * @param encounters The encounters in the list.
     * @param saveFile The file the information is saved to.
     * @param stylesheets the stylesheets to use for the encounter text.
     */
    public EncounterListMemento(Collection<Encounter> encounters, Path saveFile, EncounterCSSList stylesheets) {
        this.encounters.addAll(encounters);
        this.saveFile = saveFile;
        this.stylesheets = stylesheets;
    }

    /**
     * Returns the list of {@link Encounter}s being managed.
     *
     * @return the {@link Encounter}s.
     */
    public Collection<Encounter> getEncounters() {
        return Collections.unmodifiableList(encounters);
    }

    /**
     * Returns the {@link Path} of the directory that everything is saved in.
     *
     * @return the {@link Path} of the save directory.
     */
    public Path getSaveFile() {
        return saveFile;
    }

    /**
     * Returns the stylesheets that are used for encounter text.
     * @return the stylesheets for encounter text.
     */
    public EncounterCSSList getStylesheets() {
        return stylesheets;
    }
}
