package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.jkube.logging.Log.onException;

public class MarkdownDecomposeTest {

    public static final String SOURCE = "src/test/resources/markdown/test.md";
    public static final String TARGET = "target/workdir/decompose";

    public static final String EXPECTED = "src/test/resources/markdown/test-decomposed";

    @Test
    public void decomposeTest() {
        MarkdownDecomposer mdc = new MarkdownDecomposer();
        mdc.decompose(Path.of(SOURCE), Path.of(TARGET));
        FileUtil.expectEqualTrees(Path.of(EXPECTED), Path.of(TARGET));
    }

}
