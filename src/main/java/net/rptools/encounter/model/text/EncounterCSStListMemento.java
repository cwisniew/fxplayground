package net.rptools.encounter.model.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EncounterCSStListMemento {

    private final List<EncounterCSS> cssList;

    public EncounterCSStListMemento(Collection<EncounterCSS> css) {
        cssList = Collections.unmodifiableList(new ArrayList<>(css));
    }


    public Collection<EncounterCSS> getCssList() {
        return Collections.unmodifiableCollection(cssList);
    }

}
