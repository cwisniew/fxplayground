package net.rptools.encounter.model.encounterlist;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import net.rptools.encounter.memento.MementoOriginator;
import net.rptools.encounter.model.encounter.Encounter;
import net.rptools.encounter.model.ruleset.Ruleset;
import net.rptools.encounter.model.text.EncounterCSSList;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class EncounterList implements MementoOriginator<EncounterListMemento> {

    /** Singleton instance of <code>EncounterList</code>. */
    private static final EncounterList instance = new EncounterList();


    /** The default rule set for new encounters. */
    private Ruleset defaultRuleset = new Ruleset("Generic", Arrays.asList(new String[] {"Players", "Opponents"}));


    /** The stylesheets used. */
    private EncounterCSSList stylesheets = new EncounterCSSList();

    /** The list of <code>Encounter</code>s to manage. */
    private final ObservableList<Encounter> encounters = FXCollections.observableArrayList(
            new Callback<Encounter, Observable[]>() {
                @Override
                public Observable[] call(Encounter encounter) {
                    return new Observable[]{
                            encounter.nameProperty()
                    };
                }
            }
    );

    /**
     * Returns the instance of <code>EncounterList</code>.
     *
     * @return the instance of <code>EncounterList</code>.
     */
    public static EncounterList getInstance() {
        return instance;
    }


    /**
     * The file that the encounter list is saved to.
     */
    private Path saveFile;

    /**
     * Private constructor to stop instantiation.
     */
    private EncounterList() {
    }

    /**
     * Creates a new {@link Encounter} and adds it to the encounter list.
     *
     * @param name The name of the newly created {@link Encounter}.
     *
     * @return the <code>Encounter</code> that was created.
     */
    public Encounter createEncounter(String name) {
        Encounter encounter = new Encounter(name);
        encounters.add(encounter);
        return encounter;
    }
    /***
     * Adds an <code>Encounter</code> to the list of managed encounters.
     *
     * @param encounter The <code>{@link Encounter}</code> to add.
     */
    public void addEncounter(Encounter encounter) {
        encounters.add(encounter);
    }

    /**
     * Removes an <code>Encounter</code> from the list of managed encounters.
     * @param encounter
     */
    public void removeEncounter(Encounter encounter) {
        encounters.remove(encounter);
    }


    /**
     * Returns the list of {@link Encounter}s being managed.
     *
     * @return the {@link Encounter}s.
     */
    public ObservableList<Encounter> getEncounters() {
        return encounters;
    }

    @Override
    public void setObjectState(EncounterListMemento memento) {
        encounters.clear();
        encounters.addAll(memento.getEncounters());
        saveFile = memento.getSaveFile();
        stylesheets = memento.getStylesheets();
    }

    @Override
    public EncounterListMemento getObjectState() {
        return new EncounterListMemento(encounters, saveFile, stylesheets);
    }

    /**
     * Returns the {@link Path} of the file that everything is saved in.
     *
     * @return the {@link Path} of the save file.
     */
    public Optional<Path> getSaveFile() {
        return Optional.ofNullable(saveFile);
    }

    /**
     * Sets the {@link Path} of the directory that everything is saved in.
     *
     * @param directory the {@link Path} of the save directory.
     */
    public void setSaveFile(Path directory) {
        saveFile = directory;
    }

    /**
     * Returns the stylesheets used for text descriptions in encounters.
     * @return the stylesheets used.
     */
    public EncounterCSSList getStylesheets() {
        return stylesheets;
    }
}
