package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.markdown.ElementType;
import org.jkube.markdown.MarkdownElement;
import org.jkube.markdown.MarkdownFile;
import org.jkube.markdown.MarkdownSection;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.onException;

public class MarkdownDecomposer  {

    private static final String INFO = "meta";
    private static final String SECTION = "section";
    private static final String TITLE = "title";
    private static final String LEVEL = "level";
    private static final String ORDER = "order";
    private static final String ROW = "row";
    private static final String SEP = " ";
    private static final String COLUMNS = "columns";

    public void decompose(Path sourcePath, Path targetPath) {
        MarkdownFile input = new MarkdownFile(sourcePath);
        writeRecursively(targetPath, input.getMainSection(), 1);
    }

    private void writeRecursively(Path path, MarkdownSection section, int order) {
        FileUtil.createIfNotExists(path);
        writeInfo(path.resolve(INFO), section.getTitle(), section.getLevel(), order);
        Map<ElementType, Integer> num = new HashMap<>();
        section.getElements().forEach(element -> num.put(element.getType(), num.getOrDefault(element.getType(), 0)+1));
        Map<ElementType, Integer> counts = new HashMap<>();
        section.getElements().forEach(element -> writeElement(counts, num, path, element));
        int numsections = section.getSubSections().size();
        int i = 1;
        for (MarkdownSection subsection : section.getSubSections()) {
            writeRecursively(path.resolve(SECTION+getNumberSuffix(i, numsections)), subsection, i);
            i++;
        }
    }

    private void writeInfo(Path file, String title, int level, int order) {
        List<String> info = List.of(TITLE+SEP+title, LEVEL+SEP+level, ORDER+SEP+order);
        onException(() -> Files.write(file, info))
                .fail("Could not write info to "+file);
    }

    private void writeElement(Map<ElementType, Integer> counts, Map<ElementType, Integer> num, Path path, MarkdownElement element) {
        ElementType type = element.getType();
        int count = counts.getOrDefault(type, 0)+1;
        counts.put(type, count);
        String name = type.name().toLowerCase()+getNumberSuffix(count, num.get(type));
        Path target = path.resolve(name);
        if (type == ElementType.TABLE) {
            writeTable(target, element.getLines());
        } else {
            writeLines(target, element.getLines());
        }
     }

    private void writeTable(Path target, List<String> lines) {
        MarkdownTableParser table = new MarkdownTableParser(lines);
        FileUtil.createIfNotExists(target);
        writeLines(target.resolve(COLUMNS),table.getColumnTitles());
        int i = 0;
        int num = table.getRows().size();
        for (Map<String, String> row : table.getRows()) {
            writeRow(target.resolve(ROW+getNumberSuffix(++i, num)), row);
        }
    }

    private void writeRow(Path file, Map<String, String> row) {
        List<String> lines = new ArrayList<>();
        row.forEach((k,v) -> lines.add(k+SEP+v));
        writeLines(file, lines);
    }

    private void writeLines(Path target, List<String> lines) {
        onException(() -> Files.write(target, lines))
                .fail("Could not write output lines to "+target);
    }

    private String getNumberSuffix(int value, int num) {
        String res = Integer.toString(value);
        int len = 0;
        while (num > 0) {
            num /= 10;
            len++;
        }
        if (res.length() < len) {
            res = "0".repeat(len - res.length()) + res;
        }
        return "-"+res;
    }
}
