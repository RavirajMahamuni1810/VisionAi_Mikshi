package ai.utilities;

import java.util.ArrayList;
import java.util.List;

import ai.GeminiClient;

public class GeminiMapper {

    // =========================================
    // ✅ OLD METHOD (kept for compatibility)
    // =========================================
    public static String mapToVerifyMethod(String expected, List<String> availableMethods) {

        List<String> methods = mapStepToMethods(expected, availableMethods);

        if (!methods.isEmpty()) {
            return methods.get(methods.size() - 1); // last = most likely verify
        }

        return "verifyAdmin";
    }

    // =========================================
    // 🚀 NEW CORE METHOD (MULTI ACTION)
    // =========================================
    public static List<String> mapStepToMethods(String step, List<String> availableMethods) {

        List<String> result = new ArrayList<>();

        try {
            StringBuilder prompt = new StringBuilder();

            prompt.append("Map the step to relevant Java methods.\n");
            prompt.append("Return ONLY method names from the list.\n");
            prompt.append("Return comma-separated values.\n");
            prompt.append("Do NOT create new names.\n\n");

            prompt.append("Step:\n").append(step).append("\n\n");
            prompt.append("Available Methods:\n");

            for (String m : availableMethods) {
                prompt.append(m).append("\n");
            }

            String aiResponse = callGemini(prompt.toString());

            if (aiResponse == null || aiResponse.isEmpty()) {
                return fallback(step, availableMethods);
            }

            // 🔥 Split multiple methods
            String[] tokens = aiResponse.split(",");

            for (String token : tokens) {

                String cleaned = clean(token);

                String matched = matchWithAvailable(cleaned, availableMethods);

                if (matched != null && !result.contains(matched)) {
                    result.add(matched);
                }
            }

            // 🔥 If nothing matched → fallback
            if (result.isEmpty()) {
                return fallback(step, availableMethods);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // =========================================
    // 🔥 MATCHING ENGINE (VERY IMPORTANT)
    // =========================================
    private static String matchWithAvailable(String cleaned, List<String> methods) {

        // 1. Exact
        if (methods.contains(cleaned)) return cleaned;

        // 2. Ignore case
        for (String m : methods) {
            if (m.equalsIgnoreCase(cleaned)) return m;
        }

        // 3. Contains match
        for (String m : methods) {
            if (m.toLowerCase().contains(cleaned.toLowerCase()) ||
                cleaned.toLowerCase().contains(m.toLowerCase())) {
                return m;
            }
        }

        // 4. Keyword match
        String keyword = cleaned
                .replace("verify", "")
                .replace("click", "")
                .replace("enter", "")
                .replace("select", "")
                .toLowerCase();

        for (String m : methods) {
            if (!keyword.isEmpty() && m.toLowerCase().contains(keyword)) {
                return m;
            }
        }

        return null;
    }

    // =========================================
    // 🔥 FALLBACK LOGIC (SMART)
    // =========================================
    private static List<String> fallback(String step, List<String> methods) {

        List<String> result = new ArrayList<>();

        String lower = step.toLowerCase();

        for (String m : methods) {

            if (lower.contains("click") && m.toLowerCase().startsWith("click")) {
                result.add(m);
            }

            if ((lower.contains("enter") || lower.contains("type")) &&
                    m.toLowerCase().contains("enter")) {
                result.add(m);
            }

            if (lower.contains("select") && m.toLowerCase().contains("select")) {
                result.add(m);
            }

            if (lower.contains("verify") && m.toLowerCase().startsWith("verify")) {
                result.add(m);
            }
        }

        if (result.isEmpty()) {
            result.add("verifyAdmin");
        }

        return result;
    }

    // =========================================
    // 🔥 CLEAN METHOD NAME
    // =========================================
    private static String clean(String input) {

        input = input.trim();
        input = input.replace("()", "");
        input = input.replaceAll("[^a-zA-Z0-9]", "");

        return input;
    }

    // =========================================
    // 🔥 GEMINI CALL
    // =========================================
    static String callGemini(String prompt) {
        GeminiClient geminiClient = new GeminiClient();

        try {
            String response = geminiClient.ask(prompt);

            if (response == null || response.isEmpty()) {
                return null;
            }

            return response.trim();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}