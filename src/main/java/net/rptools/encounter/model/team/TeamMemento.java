package net.rptools.encounter.model.team;

import net.rptools.encounter.model.participant.Participant;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TeamMemento {
    /** The name of the team. */
    private String name;

    /** The members of the <code>Team</code>.*/
    private List<Participant> members = new LinkedList<>();


    /**
     * Creates a new <code>TeamMemento</code> object.
     *
     * @param teamName The name of the team.
     * @param teamMembers The members of the team.s
     */
    public TeamMemento(String teamName, Collection<Participant> teamMembers) {
        name = teamName;
        members.addAll(teamMembers);
    }

    /**
     * Returns the list of members on the <code>Team</code>.
     *
     * @return the list of members on the <code>Team</code>.
     */
    public Collection<Participant> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Returns the name of the <code>Team</code>.
     *
     * @return the name of the team.
     */
    public String getName() {
        return name;
    }
}
