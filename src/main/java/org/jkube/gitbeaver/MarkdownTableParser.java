package org.jkube.gitbeaver;


import org.jkube.util.Expect;

import java.util.*;
import java.util.regex.Pattern;

public class MarkdownTableParser {

	private static final Pattern TABLE_SEP_MATCHER = Pattern.compile(":?-+:?");

	private final List<String> columnTitles;
	private final List<Map<String, String>> rows;

	public MarkdownTableParser(List<String> lines) {
		Expect.atLeast(lines.size(), 2).elseFail("Expected at least 2 lines for table, got "+lines.size());
		columnTitles = parseLine(lines.get(0));
		rows = new ArrayList<>();
		checkConsistency(parseLine(lines.get(1)));
		for (int i = 2; i < lines.size(); i++) {
			rows.add(parseRow(lines.get(i)));
		}
	}

	private void checkConsistency(List<String> separators) {
		Expect.size(separators, columnTitles.size()).elseFail("Row has "+separators.size()+" cells, table has "+columnTitles.size()+" columns");
		separators.forEach(sep -> Expect.isTrue(TABLE_SEP_MATCHER.matcher(sep).matches()).elseFail("expected markdown table separator, got: "+sep));
	}

	public List<String> getColumnTitles() {
		return columnTitles;
	}

	public List<Map<String, String>> getRows() {
		return rows;
	}

	private Map<String, String> parseRow(String line) {
		List<String> cells = parseLine(line);
		Expect.size(cells, columnTitles.size()).elseFail("Row has "+cells.size()+" cells, table has "+columnTitles.size()+" columns");
		Map<String, String> result = new LinkedHashMap<>();
		for (int i = 0; i < cells.size(); i++) {
			result.put(columnTitles.get(i), cells.get(i));
		}
		return result;
	}

	private List<String> parseLine(String rawline) {
		String line = rawline.trim();
		if (line.startsWith("|")) {
			line = line.substring(1);
		}
		if (line.endsWith("|")) {
			line = line.substring(0, line.length()-1);
		}
		List<String> res = new ArrayList<>();
		for (String s : line.split("\\|")) {
			res.add(s.trim());
		}
		return res;
	}

}
