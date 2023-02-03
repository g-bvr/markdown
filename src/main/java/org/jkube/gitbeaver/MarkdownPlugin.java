package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.SimplePlugin;

public class MarkdownPlugin extends SimplePlugin {

    public MarkdownPlugin() {
        super("Parses and deconstructs markdown files", MarkdownDecomposeCommand.class);
    }

}
