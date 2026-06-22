package ai.utilities;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap; 
import java.util.List;
import java.util.Map;

public class TestGenerator {

	private static final String EXCEL_PATH = "data\\Alltests.xlsx";
	private static final String PAGE_DIR = "src\\test\\java\\generated_pages\\";
	private static final String OUTPUT_DIR = "src\\test\\java\\generated_tests\\";
	static boolean err = false;

	public static boolean generate() {

	    List<TestDefinitionReader.TestCase> testCases = TestDefinitionReader.read(EXCEL_PATH);

	    Map<String, List<TestDefinitionReader.TestCase>> grouped = groupBySheet(testCases);

	    grouped.forEach((sheet, cases) -> {

	        try {
	        	int priorityCounter = 1;
	            String pageClass = capitalize(sheet) + "Page";
	            String pageFile = PAGE_DIR + pageClass + ".java";

	            List<String> verifyMethods = PageMethodLoader.getVerifyMethods(pageFile);

	            String testClassName = capitalize(sheet) + "Test";
	            FileWriter writer = new FileWriter(OUTPUT_DIR + testClassName + ".java");

	            writeClassHeader(writer, testClassName, sheet, pageClass);

	            // ✅ GROUP BY METHOD NAME (IMPORTANT FIX)
	            Map<String, List<TestDefinitionReader.TestCase>> methodGrouped = new LinkedHashMap<>();

	            for (TestDefinitionReader.TestCase tc : cases) {
	                methodGrouped.computeIfAbsent(tc.methodName, k -> new ArrayList<>()).add(tc);
	            }

	            // ✅ GENERATE ONE TEST METHOD PER METHOD NAME
	            for (String methodName : methodGrouped.keySet()) {

	                List<TestDefinitionReader.TestCase> steps = methodGrouped.get(methodName);

	                writeTestMethod(writer, methodName, steps, pageClass, verifyMethods, priorityCounter++);
	            }

	            writeFooter(writer);
	            writer.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	            err = true;
	        }
	    });

	    if (err)
	        return false;

	    System.out.println("✅ Test generation completed.");
	    return true;
	}

	private static Map<String, List<TestDefinitionReader.TestCase>> groupBySheet(
			List<TestDefinitionReader.TestCase> list) {

		Map<String, List<TestDefinitionReader.TestCase>> map = new HashMap<>();

		for (TestDefinitionReader.TestCase tc : list) {
			map.computeIfAbsent(tc.sheetName, k -> new ArrayList<>()).add(tc);
		}

		return map;
	}

	private static void writeClassHeader(FileWriter writer, String className, String sheet, String pageClass)
			throws Exception {

		writer.write("package generated_tests;\n\n");

		writer.write("import java.lang.reflect.Method;\n");
		writer.write("import java.util.Map;\n\n");

		writer.write("import org.testng.annotations.DataProvider;\n");
		writer.write("import org.testng.annotations.Test;\n\n");

		writer.write("import pw.base.*;\n");
		writer.write("import pw.utils.PWLog;\n");
		writer.write("import generated_pages." + pageClass + ";\n\n");

		writer.write("public class " + className + " extends PWBaseTest {\n");
		writer.write("    String sheetName = \"" + sheet + "\";\n\n");
	}
 
	
	private static void writeTestMethod(FileWriter writer,
	        String methodName,
	        List<TestDefinitionReader.TestCase> steps,
	        String pageClass,
	        List<String> verifyMethods,
	        int priority) throws Exception {

	    writer.write("    @TestMeta(user = UserType.ADMIN, navPath = \"Dashboard/AdminDashboard\")\n");
	    writer.write("    @Test(dataProvider = \"loginData\", enabled = true, priority = " + priority + ", groups = { \"Smoke\" })\n");
	    writer.write("    public void " + methodName + "(Method method, Map<String, String> testData) {\n\n");

	    writer.write("        " + pageClass + " page = new " + pageClass + "(getPage());\n");
	    writer.write("        String className = this.getClass().getSimpleName();\n\n");

	    // ✅ Safe TC name
	    String tcname = (steps != null && !steps.isEmpty()) ? steps.get(0).tcname : methodName;

	    writer.write("        PWLog.Info(className, \"" + escape(tcname) + "\");\n\n");

	    int stepIndex = 1;

	    for (TestDefinitionReader.TestCase step : steps) {

	        String stepText = escape(step.expected);

	        String verifyMethod = GeminiMapper.mapToVerifyMethod(
	                step.expected + " " + step.tcname,
	                verifyMethods
	        );

	        // ✅ HARD VALIDATION (only safety retained)
	        if (!verifyMethods.contains(verifyMethod)) {
	            verifyMethod = "verifyAdmin";
	        }

	        writer.write("        // Step " + stepIndex + ": " + stepText + "\n");

	        writer.write("        if (page." + verifyMethod + "()) {\n");
	        writer.write("            PWLog.Pass(className, \"Step " + stepIndex + " passed: " + stepText + "\");\n");
	        writer.write("        } else {\n");
	        writer.write("            PWLog.Fail(className, \"Step " + stepIndex + " failed: " + stepText + " - \" + PWBaseTest.getFailureContext().getErrorMessage());\n");
	        writer.write("            return;\n");
	        writer.write("        }\n\n");

	        stepIndex++;
	    }

	    writer.write("    }\n\n");
	}
	
	private static void writeFooter(FileWriter writer) throws Exception {

		writer.write("    @DataProvider(name = \"testData\")\n");
		writer.write("    public Object[][] testData(Method method) {\n");
		writer.write("        Map<String, String> rowData = GetExcelRow(method.getName(), \"\", sheetName);\n");
		writer.write("        return new Object[][] { { rowData } };\n");
		writer.write("    }\n");

		writer.write("}\n");
	}

	private static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	private static String escape(String str) {
	    if (str == null) return "";
	    return str.replace("\"", "\\\"");
	}
}// EOF