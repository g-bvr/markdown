package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.SimplePlugin;

public class MarkdownPlugin extends SimplePlugin {

    public MarkdownPlugin() {
        super(MarkdownDecomposeCommand.class);
    }

}
