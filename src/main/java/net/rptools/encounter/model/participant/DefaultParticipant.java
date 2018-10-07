package net.rptools.encounter.model.participant;

public class DefaultParticipant implements Participant {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the <code>Participant</code>.
     *
     * @param n The name.
     */
    public void setName(String n) {
        name = n;
    }
}
