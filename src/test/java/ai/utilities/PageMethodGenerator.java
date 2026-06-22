package ai.utilities;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class PageMethodGenerator {

	private static final String CSV_PATH = "data\\element_repository.csv";
	private static final String OUTPUT_DIR = "src\\test\\java\\generated_pages";

	public static boolean generate() {
		try {
			List<Map<String, String>> rows = readCSV();

			// Group by page title
			Map<String, List<Map<String, String>>> grouped = rows.stream()
					.collect(Collectors.groupingBy(r -> r.get("page_title")));

//			Files.createDirectories(Paths.get(OUTPUT_DIR));

			for (String pageTitle : grouped.keySet()) {

				String className = normalizeClassName(pageTitle) + "Page";
				String filePath = OUTPUT_DIR + "\\" + className + ".java";

				try (FileWriter writer = new FileWriter(filePath, false)) {
					writer.write("package generated_pages;\n");
					writer.write("import com.microsoft.playwright.Page;\n");
					writer.write("import ai.PWActions;\n");
					writer.write("import pw.base.PWBaseTest;\n\n");

					writer.write("public class " + className + " {\n\n");

					// Constructor
					writer.write("    Page page;\n\n");
					writer.write("    public " + className + "(Page page) {\n");
					writer.write("        this.page = page;\n");
					writer.write("    }\n\n");

					Set<String> uniqueLocators = new HashSet<>();

					for (Map<String, String> row : grouped.get(pageTitle)) {

						if (!isValid(row, uniqueLocators))
							continue;

						String elementType = row.get("element_type");
						String name = row.get("generated_name");
						String locator = buildLocator(row);

						if (locator == null || name.isEmpty())
							continue;

						// Generate methods
						writer.write(generateMethod(elementType, name, locator));
						writer.write("\n");
					}
					writer.write(generateDefaultVerifyMethod());
					writer.write("}\n");
				}
			}

			System.out.println("✅ Method generation completed.");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ---------- CSV Reader ----------

	private static List<Map<String, String>> readCSV() throws IOException {
		List<Map<String, String>> data = new ArrayList<>();

		List<String> lines = Files.readAllLines(Paths.get(CSV_PATH));
		String[] headers = lines.get(0).split(",");

		for (int i = 1; i < lines.size(); i++) {
			String[] values = lines.get(i).split(",", -1);
			Map<String, String> row = new HashMap<>();

			for (int j = 0; j < headers.length; j++) {
				row.put(headers[j], j < values.length ? values[j] : "");
			}

			data.add(row);
		}

		return data;
	}

	// ---------- Filtering ----------

	private static boolean isValid(Map<String, String> row, Set<String> uniqueLocators) {

		String text = row.get("visible_text");
		String placeholder = row.get("placeholder");
		String name = row.get("generated_name");
		String strategy = row.get("locator_strategy");
		String value = row.get("locator_value");

		if ((text.isEmpty() && placeholder.isEmpty()) || name.length() < 3)
			return false;

		String key = strategy + value;
		if (uniqueLocators.contains(key))
			return false;

		uniqueLocators.add(key);

		if (name.equalsIgnoreCase("btn") || name.equalsIgnoreCase("link"))
			return false;

		return true;
	}

	// ---------- Locator Builder ----------

	private static String buildLocator(Map<String, String> row) {

		String strategy = row.get("locator_strategy");
		String value = row.get("locator_value");
		String text = row.get("visible_text");

		if ("id".equals(strategy))
			return "\"" + value + "\"";

		if ("name".equals(strategy))
			return "\"" + value + "\"";

		if (!text.isEmpty())
			return "\"text=" + text + "\"";

		return null;
	}

	// ---------- Method Generator ----------

	private static String generateMethod(String type, String name, String locator) {

	    StringBuilder sb = new StringBuilder();
	    String camelName = capitalize(name);

	    switch (type) {

	        case "input":
	        case "textarea":
	            sb.append("    public boolean ").append(name).append("EnterText(String value) {\n");
	            sb.append("        try {\n");
	            sb.append("            PWActions.fill(").append(locator).append(", value, \"Enter ").append(camelName).append("\");\n");
	            sb.append("            return true;\n");
	            sb.append("        } catch (Exception e) {\n");
	            sb.append("            PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());\n");
	            sb.append("            return false;\n");
	            sb.append("        }\n");
	            sb.append("    }\n\n");
	            break;

	        case "button":
	        case "link":
	            sb.append("    public boolean click").append(camelName).append("() {\n");
	            sb.append("        try {\n");
	            sb.append("            PWActions.click(").append(locator).append(", \"Click ").append(camelName).append("\");\n");
	            sb.append("            return true;\n");
	            sb.append("        } catch (Exception e) {\n");
	            sb.append("            PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());\n");
	            sb.append("            return false;\n");
	            sb.append("        }\n");
	            sb.append("    }\n\n");
	            break;

	        case "dropdown":
	            sb.append("    public boolean select").append(camelName).append("(String value) {\n");
	            sb.append("        try {\n");
	            sb.append("            PWActions.select(").append(locator).append(", value, \"Select ").append(camelName).append("\");\n");
	            sb.append("            return true;\n");
	            sb.append("        } catch (Exception e) {\n");
	            sb.append("            PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());\n");
	            sb.append("            return false;\n");
	            sb.append("        }\n");
	            sb.append("    }\n\n");
	            break;

	        // ✅ ONLY generate verify for meaningful UI elements
	        case "label":
	        case "text":
	        case "header":
	        case "message":
	            sb.append("    public boolean verify").append(camelName).append("() {\n");
	            sb.append("        try {\n");
	            sb.append("            PWActions.waitFor(").append(locator).append(", \"Verify ").append(camelName).append("\", 5000);\n");
	            sb.append("            return true;\n");
	            sb.append("        } catch (Exception e) {\n");
	            sb.append("            PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());\n");
	            sb.append("            return false;\n");
	            sb.append("        }\n");
	            sb.append("    }\n\n");
	            break;
	    }

	    return sb.toString();
	}

	// ---------- Utils ----------

	private static String normalizeClassName(String input) {
		input = input.replaceAll("[^a-zA-Z0-9]", " ");
		String[] parts = input.trim().split("\\s+");

		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			sb.append(capitalize(part));
		}
		return sb.toString();
	}

	private static String capitalize(String str) {
		if (str == null || str.isEmpty())
			return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private static String generateDefaultVerifyMethod() {
		StringBuilder sb = new StringBuilder();

		sb.append("    public boolean verifyAdmin() {\n");
		sb.append("        try {\n");
		sb.append("            PWActions.waitFor(\"body\", \"Verify page loaded\", 5000);\n");
		sb.append("            return true;\n");
		sb.append("        } catch (Exception e) {\n");
		sb.append("            PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());\n");
		sb.append("            return false;\n");
		sb.append("        }\n");
		sb.append("    }\n");

		return sb.toString();
	}
} // EOF