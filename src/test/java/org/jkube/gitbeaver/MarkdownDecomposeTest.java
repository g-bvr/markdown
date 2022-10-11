package org.jkube.gitbeaver;

import org.jkube.util.Expect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.jkube.logging.Log.onException;

public class MarkdownDecomposeTest {

    public static final String SOURCE = "src/test/resources/markdown/test.md";
    public static final String TARGET = "target/workdir/decompose";

    public static final String EXPECTED = "src/test/resources/markdown/test-decomposed";

    @Test
    public void decomposeTest() {
        MarkdownDecomposer mdc = new MarkdownDecomposer();
        mdc.decompose(Path.of(SOURCE), Path.of(TARGET));
        compare(Path.of(EXPECTED), Path.of(TARGET));
    }

    private void compare(Path path1, Path path2) {
        File file1 = path1.toFile();
        File file2 = path2.toFile();
        Expect.equal(file1.isDirectory(), file2.isDirectory())
                .elseFail("Mismatching file type "+file1+" vs. "+file2);
        if (file1.isDirectory()) {
            String[] files1 = file1.list();
            if (files1 == null) {
                files1 = new String[0];
            }
            String[] files2 = file2.list();
            if (files2 == null) {
                files2 = new String[0];
            }
            Assertions.assertEquals(files1.length, files2.length);
            Arrays.sort(files1);
            Arrays.sort(files2);
            for (int i = 0; i < files1.length; i++) {
                Expect.equal(files1[i], files2[i]).elseFail("Mismatching files " + files1[i] + " vs. " + files2[i]);
                compare(path1.resolve(files1[i]), path2.resolve(files2[i]));
            }
        } else {
            List<String> lines1 = onException(()-> Files.readAllLines(path1)).fail("Could not load file "+path1);
            List<String> lines2 = onException(()-> Files.readAllLines(path2)).fail("Could not load file "+path2);
            Expect.equal(lines1.size(), lines2.size()).elseFail("Mismatching number of lines: "+path1+" ("+lines1.size()+") vs. "+path2+" ("+lines2.size()+")");
            for (int i = 0; i < lines1.size(); i++) {
                Expect.equal(lines1.get(i), lines2.get(i)).elseFail("Mismatching line #" + i +
                        " in file "+path1+ " vs. " + path2+": \n"+lines1.get(i)+"\n"+lines2.get(i));
            }
        }
    }

}
