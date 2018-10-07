package net.rptools.encounter.model.encounter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.rptools.encounter.memento.MementoOriginator;
import net.rptools.encounter.model.team.Team;

import java.util.*;

public class Encounter implements MementoOriginator<EncounterMemento> {

    /** The unique ID of the encounter. */
    private UUID id = UUID.randomUUID();

    /** The name of the <code>Encounter</code>. */
    private final StringProperty name = new SimpleStringProperty(this, "name", "");

    /** The <code>Team</code>s in the encounter. */
    private final List<Team> teams = new ArrayList<>();


    /** The description for the encounter. */
    private String description = "";


    /**
     * The rating or difficulty of the encounter.
     */
    private SimpleStringProperty rating = new SimpleStringProperty(this, "rating", "");

    /**
     * Creates a new <code>Encounter</code>.
     *
     * @param memento The memento which contains the state.
     */
    public Encounter(EncounterMemento memento) {
        setObjectState(memento);
    }

    /**
     * Creates a new <code>Encounter</code>
     * @param encounterName the name of the new encounter.
     */
    public Encounter(String encounterName) {
        name.setValue(encounterName);
    }

    /**
     * Returns the name of the encounter.
     *
     * @return the name of the encounter.
     */
    public String getName() {
        return name.getValue();
    }

    /**
     * Sets the name of the encounter.
     *
     * @param encounterName name to set.
     */
    public void setName(String encounterName) {
        name.setValue(encounterName);
    }

    /**
     * Gets the {@link StringProperty} that holds the name for the <code>Encounter</code>.
     *
     * @return the property which holds the name.
     */
    public StringProperty nameProperty() {
        return name;
    }


    /**
     * Returns the description of the encounter.
     *
     * @return the description of the encounter.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for the encounter.
     *
     * @param description the description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the rating for the encounter.
     *
     * @return the rating for the encounter.
     */
    public String getRating() {
        return rating.get();
    }

    /**
     * Sets the rating for the encounter.
     *
     * @param rating the rating for the encounter.
     */
    public void setRating(String rating) {
        this.rating.set(rating);
    }

    /**
     * Returns a list of the teams in the encounter.
     *
     * @return the teams in the encounter.
     */
    public List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }


    /**
     * Returns the ID of the encounter.
     *
     * @return the ID of the encounter.
     */
    public UUID getId() {
        return id;
    }

    @Override
    public void setObjectState(EncounterMemento memento) {
        id = memento.getId();
        name.setValue(memento.getName());
        teams.clear();
        teams.addAll(memento.getTeams());
        description = memento.getDescription();
        rating.set(memento.getRating());
    }

    @Override
    public EncounterMemento getObjectState() {
        return new EncounterMemento(id, name.getValue(), teams, description, rating.get());
    }
}
