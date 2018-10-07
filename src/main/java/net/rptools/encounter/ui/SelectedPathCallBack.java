package net.rptools.encounter.ui;

import java.nio.file.Path;

@FunctionalInterface
public interface SelectedPathCallBack {
    void selectedPath(Path path);
}
