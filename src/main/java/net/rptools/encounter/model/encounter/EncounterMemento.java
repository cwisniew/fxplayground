package net.rptools.encounter.model.encounter;

import net.rptools.encounter.model.team.Team;

import java.util.*;

public class EncounterMemento {

    /** The unique ID of the encounter. */
    private final UUID id;

    /** The name of the <code>Encounter</code>. */
    private final String name;

    /** The <code>Team</code>s in the encounter. */
    private final List<Team> teams = new ArrayList<>();


    /** The description for the encounter. */
    private final String description;


    /** The rating or difficulty of the encounter. */
    private final String rating;

    /**
     * Creates a new <code>EncounterMemento</code>.
     *
     * @param id The id of the encounter.
     * @param name The name of the encounter.
     * @param teams The teams in the encounter.
     * @param description The description of the encounter.
     * @param rating The rating of the encounter.
     */
    public EncounterMemento(UUID id, String name, Collection<Team> teams, String description, String rating) {
        this.id = id;
        this.name = name;
        this.teams.addAll(teams);
        this.description = description;
        this.rating = rating;
    }

    /**
     * Returns the ID of the encounter.
     *
     * @return the ID of the encounter.
     */
    public UUID getId() {
        return id;
    }


    /**
     * Returns the name of the encounter.
     *
     * @return the name of the encounter.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a list of the teams in the encounter.
     *
     * @return the teams in the encounter.
     */
    public Collection<Team> getTeams() {
        return Collections.unmodifiableList(teams);
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
     * Returns the rating for the encounter.
     *
     * @return the rating for the encounter.
     */
    public String getRating() {
        return rating;
    }
}
