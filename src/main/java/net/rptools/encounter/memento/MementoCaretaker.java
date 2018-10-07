package net.rptools.encounter.memento;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface MementoCaretaker<T> {
    T createMemento();

    void setMemento(T memento);

    void saveMemento(Path saveDirectory, JsonGenerator generator) throws IOException;

    Optional<T> loadMemento(Path saveDirectory, JsonNode node) throws IOException;

}
