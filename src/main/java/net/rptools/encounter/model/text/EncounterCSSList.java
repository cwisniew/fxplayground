package net.rptools.encounter.model.text;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.rptools.encounter.memento.MementoOriginator;


public class EncounterCSSList implements MementoOriginator<EncounterCSStListMemento> {
    private final ObservableList<EncounterCSS> cssList = FXCollections.observableArrayList();


    public ObservableList<EncounterCSS> getCssList() {
        return FXCollections.unmodifiableObservableList(cssList);
    }

    public void addTextCSS(EncounterCSS css) {
        cssList.remove(css);
        cssList.add(css);
    }

    public void removeTextCSS(EncounterCSS css) {
        cssList.remove(css);
    }

    @Override
    public void setObjectState(EncounterCSStListMemento memento) {
        cssList.clear();
        cssList.addAll(memento.getCssList());
    }

    @Override
    public EncounterCSStListMemento getObjectState() {
        return new EncounterCSStListMemento(cssList);
    }
}
