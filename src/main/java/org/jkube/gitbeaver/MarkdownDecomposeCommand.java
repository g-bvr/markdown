package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.FileUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.log;
import static org.jkube.logging.Log.onException;

/**
 * Usage: resolve source target
 */
public class MarkdownDecomposeCommand extends AbstractCommand {

    private static final String MARKDOWN = "markdown";

    private static final String TARGET = "target";

    public MarkdownDecomposeCommand() {
        super("Decompose a markdown file into a folder tree");
        commandline("DECOMPOSE MARKDOWN "+MARKDOWN+" INTO "+TARGET);
        argument(MARKDOWN, "The path to the markdown file (relative to current workspace)");
        argument(TARGET, "The path of the result folder (relative to current workspace, will be created including ancestors if not present, yet)");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path sourcePath = workSpace.getAbsolutePath(arguments.get(MARKDOWN));
        Path targetPath = workSpace.getAbsolutePath(arguments.get(TARGET));
        log("Resolving markdown file "+sourcePath+" to "+targetPath);
        FileUtil.createIfNotExists(targetPath.getParent());
        onException(() -> new MarkdownDecomposer().decompose(sourcePath, targetPath))
                .fail("Could not write resolved lines to "+targetPath);
    }
}
