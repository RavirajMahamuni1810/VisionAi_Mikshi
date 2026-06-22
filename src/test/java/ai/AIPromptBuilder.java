package ai;

import java.util.List;

import pw.utils.PWLog;

public class AIPromptBuilder {
	public static String buildPrompt(FailureContext ctx) {
		StringBuilder prompt = new StringBuilder();
		prompt.append("You are an expert QA automation engineer.\n");
		prompt.append("Analyze the following Playwright test failure and provide root cause and suggested fix.\n\n");
		// Step info
		prompt.append("Test Step:\n").append(ctx.getStepName()).append("\n\n");
		prompt.append("Action:\n").append(ctx.getAction()).append("\n\n");
		prompt.append("Attempted Locator:\n").append(ctx.getAttemptedLocator()).append("\n\n");
		// Error
		prompt.append("Error Message:\n").append(ctx.getErrorMessage()).append("\n\n");
		// Execution steps
		prompt.append("Execution Steps:\n");
		List<String> steps = PWLog.getStepHistory();
		if (steps != null) {
			for (String step : steps) {
				prompt.append(step).append("\n");
			}
		}
		prompt.append("\n");
		// Console logs
		prompt.append("Console Errors:\n");
		if (ctx.getConsoleErrors() != null) {
			for (String log : ctx.getConsoleErrors()) {
				prompt.append(log).append("\n");
			}
		}
		prompt.append("\n");
		// Network failures
		prompt.append("Network Failures:\n");
		if (ctx.getNetworkFailures() != null) {
			for (String net : ctx.getNetworkFailures()) {
				prompt.append(net).append("\n");
			}
		}
		prompt.append("\n");
		prompt.append("Please provide:\n");
		prompt.append("1. Root cause\n");
		prompt.append("2. Whether it is Automation issue or Application issue\n");
		prompt.append("3. Suggested fix\n");
		return prompt.toString();
	}
}