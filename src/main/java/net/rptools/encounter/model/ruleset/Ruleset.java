package net.rptools.encounter.model.ruleset;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Ruleset {

    private String name;

    private final List<String> defaultTeamNames = new LinkedList<>();


    public Ruleset(String rulesetName, Collection<String> teamNames) {
        name = rulesetName;
        defaultTeamNames.addAll(teamNames);
    }


    public void setName(String rulesetName){
        name = rulesetName;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getDefaultTeamNames() {
        return Collections.unmodifiableList(defaultTeamNames);
    }

    public void setDefaultTeamNames(Collection<String> teamNames) {
        defaultTeamNames.clear();
        defaultTeamNames.addAll(teamNames);
    }
}
