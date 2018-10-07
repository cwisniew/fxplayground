package net.rptools.encounter.model.text;

import net.rptools.encounter.memento.MementoOriginator;

import java.util.UUID;

public class EncounterCSS implements MementoOriginator<EncounterCSSMemento> {

    private UUID id;
    private String name;
    private String css;

    public EncounterCSS(String name, String css) {
        id = UUID.randomUUID();
        this.name = name;
        this.css = css;
    }

    public EncounterCSS(String name) {
        this(name, "");
    }

    public EncounterCSS(EncounterCSSMemento memento) {
        setObjectState(memento);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCss() {
        return css;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCss(String css) {
        this.css = css;
    }

    @Override
    public void setObjectState(EncounterCSSMemento memento) {
        id = memento.getId();
        name = memento.getName();
        css = memento.getCss();
    }

    @Override
    public EncounterCSSMemento getObjectState() {
        return new EncounterCSSMemento(id, name, css);
    }

}
