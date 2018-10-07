package net.rptools.encounter.model.team;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.rptools.encounter.memento.MementoOriginator;
import net.rptools.encounter.model.participant.Participant;

import java.util.*;

public class Team implements MementoOriginator<TeamMemento> {
    /** The name of the team. */
    private String name;



    /** The members of the <code>Team</code>.*/
    private ObservableList<Participant> members = FXCollections.observableList(new LinkedList<>());


    /**
     * Creates a new <code>Team</code>
     *
     * @param teamName The name of the <code>Team</code>.
     */
    public Team(String teamName) {
        name = teamName;
    }


    /**
     * Adds a member to the <code>Team</code>.
     *
     * @param participant The team member to add.
     */
    public void addMember(Participant participant) {
        if (!members.contains(participant)) {
            members.add(participant);
        }
    }

    /**
     * Add multiple members to the <code>Team</code>.
     *
     * @param participants the members to add.
     */
    public void addMembers(Participant... participants) {
        addMembers(Arrays.asList(participants));
    }

    /**
     * Add multiple members to the <code>Team</code>.
     *
     * @param participants the members to add.
     */
    public void addMembers(Collection<Participant> participants) {
        List<Participant> newMembers = new LinkedList<>();
        participants.forEach(p-> {
            if (!members.contains(p)) {
                newMembers.add(p);
            }
        });
        FXCollections.copy(members, newMembers);
    }

    /**
     * Removes a member from the <code>Team</code>.
     * @param participant
     */
    public void removeMember(Participant participant) {
        members.remove(participant);
    }

    /**
     * Removes several members from a <code>Team</code>.
     *
     * @param participants the members to remove.
     */
    public void removeMembers(Participant... participants) {
        members.removeAll(participants);
    }

    /**
     * Removes several members from a <code>Team</code>.
     *
     * @param participants the members to remove.
     */
    public void removeMembers(Collection<Participant> participants) {
        members.removeAll(participants);
    }

    /**
     * Returns the list of members on the <code>Team</code>.
     *
     * @return the list of members on the <code>Team</code>.
     */
    public ObservableList<Participant> getMembers() {
        return FXCollections.unmodifiableObservableList(members);
    }


    /**
     * Sets the name of the <code>Team</code>.
     *
     * @param teamName The name for the team.
     */
    public void setName(String teamName) {
        name = teamName;
    }

    /**
     * Returns the name of the <code>Team</code>.
     *
     * @return the name of the team.
     */
    public String getName() {
        return name;
    }

    @Override
    public void setObjectState(TeamMemento memento) {

    }

    @Override
    public TeamMemento getObjectState() {
        return new TeamMemento(name, Collections.unmodifiableList(members));
    }
}
