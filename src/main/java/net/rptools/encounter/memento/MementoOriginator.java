package net.rptools.encounter.memento;

public interface MementoOriginator<T> {
    void setObjectState(T memento);

    T getObjectState();
}
