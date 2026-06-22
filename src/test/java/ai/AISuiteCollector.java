package ai;

import java.util.ArrayList;
import java.util.List;

public class AISuiteCollector {
	private static List<FailureContext> failures = new ArrayList<>();

	public static void addFailure(FailureContext ctx) {
		failures.add(ctx);
	}
	public static List<FailureContext> getFailures() {
		return failures;
	}
}