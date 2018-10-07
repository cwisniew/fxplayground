package net.rptools.encounter.model.text;

import java.util.UUID;

public class EncounterCSSMemento {
    private final UUID id;
    private final String name;
    private final String css;

    EncounterCSSMemento(UUID id, String name, String css) {
        this.id = id;
        this.name = name;
        this.css = css;
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
}
