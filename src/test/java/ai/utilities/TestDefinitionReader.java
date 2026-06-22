package ai.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.*;

public class TestDefinitionReader {

	public static class TestCase {
		public String methodName;
		public String expected;
		public String sheetName;
		public String tcname;
	}

	public static List<TestCase> read(String filePath) {
		List<TestCase> testCases = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			for (int s = 0; s < workbook.getNumberOfSheets(); s++) {

				Sheet sheet = workbook.getSheetAt(s);
				String sheetName = sheet.getSheetName(); // .toLowerCase();

				Iterator<Row> rows = sheet.iterator();
				if (rows.hasNext())
					rows.next(); // skip header

				String currentMethod = "";
				String currentTCName = "";

				while (rows.hasNext()) {
					Row row = rows.next();

					String methodCell = getCell(row, 0);
					String tcCell = getCell(row, 1);

					// 🔥 Carry forward values
					if (methodCell != null && !methodCell.isEmpty()) {
						currentMethod = methodCell;
					}

					if (tcCell != null && !tcCell.isEmpty()) {
						currentTCName = tcCell;
					}

					String steps = getCell(row, 2);
					String expected = getCell(row, 3);

					TestCase tc = new TestCase();
					tc.methodName = currentMethod;
					tc.tcname = currentTCName;
					tc.sheetName = sheetName;

//					tc.expected = resolveExpected(expected, steps);
					tc.expected = (steps + " " + expected).trim();

					// ✅ Only add if we have a valid method
					if (tc.methodName != null && !tc.methodName.isEmpty()) {
						testCases.add(tc);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return testCases;
	}

	private static String resolveExpected(String expected, String steps) {

		if (expected != null && !expected.trim().isEmpty()) {
			return expected;
		}

		if (steps == null || steps.isEmpty()) {
			return "";
		}

		String[] lines = steps.split("\\n");

		// Traverse from bottom → pick last meaningful step
		for (int i = lines.length - 1; i >= 0; i--) {
			String line = lines[i].toLowerCase();

			if (line.contains("login") || line.contains("password") || line.contains("http") || line.contains("email")
					|| line.contains("type")) {
				continue;
			}

			return lines[i].trim();
		}

		return steps; // fallback
	}

	private static String getCell(Row row, int index) {
		Cell cell = row.getCell(index);
		return cell == null ? "" : cell.toString().trim();
	}
}