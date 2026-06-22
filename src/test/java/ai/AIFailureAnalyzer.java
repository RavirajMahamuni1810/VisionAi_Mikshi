package ai;

import io.qameta.allure.Allure;

public class AIFailureAnalyzer {
	private static final AIClient aiClient = new GeminiClient();

	public static String analyze(FailureContext ctx) {
		try {
			// Build AI prompt
			String prompt = AIPromptBuilder.buildPrompt(ctx);
			// Always attach prompt first
			Allure.addAttachment("AI Prompt", prompt);
			// Send prompt to AI
//			long start = System.currentTimeMillis();
			String aiResponse = aiClient.ask(prompt);
//			long end = System.currentTimeMillis();
//			Allure.addAttachment("AI Response Time", (end - start) + " ms");
			// Attach AI response
//			Allure.addAttachment("AI Failure Analysis", aiResponse);
			return aiResponse;
		} catch (Exception e) {
			String error = "AI processing failed: " + e.toString();
//			Allure.addAttachment("AI Failure Analysis Error", error);
			e.printStackTrace();
			return error;
		}
	}
}
// EOF
