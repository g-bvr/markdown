package org.jkube.gitbeaver;

import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.interfaces.Plugin;

import java.util.List;

public class MarkdownPlugin implements Plugin {
    @Override
    public void init() {
    }

    @Override
    public List<Command> getCommands() {
        return List.of(new MarkdownDecomposeCommand());
    }

    @Override
    public void shutdown() {
    }
}
