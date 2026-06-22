package ai.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

import pw.base.PWBaseTest;

public class ElementExtractor {

	private static final String CSV_PATH = "data\\element_repository.csv";

	public ElementExtractor(Page page) {
	}

	public static boolean extract(Page page, String path) {
		String navUrl = PWBaseTest.mapAllVariables.get("url") + path;
		page.navigate(navUrl, new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
		String url = page.url();
		String title = page.title();

		List<ElementHandle> elements = new ArrayList<>();

		// Target elements
		elements.addAll(page.querySelectorAll("input"));
		elements.addAll(page.querySelectorAll("button"));
		elements.addAll(page.querySelectorAll("select"));
		elements.addAll(page.querySelectorAll("textarea"));
		elements.addAll(page.querySelectorAll("a"));

		try {
			File file = new File(CSV_PATH);
			boolean isNewFile = file.createNewFile(); // true if file created now

			try (FileWriter writer = new FileWriter(file, true)) {

				// ✅ Write header ONLY if new file
				if (isNewFile) {
					writer.append(String.join(",", "page_url", "page_title", "element_type", "tag_name", "visible_text",
							"id", "name", "class", "placeholder", "generated_name", "locator_strategy", "locator_value",
							"source_of_name"));
					writer.append("\n");
				}

				for (ElementHandle el : elements) {

					if (!isVisible(el))
						continue;

					String tag = el.evaluate("e => e.tagName.toLowerCase()").toString();

					String text = safe(el.innerText());
					String id = safe(el.getAttribute("id"));
					String name = safe(el.getAttribute("name"));
					String clazz = safe(el.getAttribute("class"));
					String placeholder = safe(el.getAttribute("placeholder"));
					String aria = safe(el.getAttribute("aria-label"));

					// 🚫 IGNORE useless elements
					if (shouldIgnore(tag, text, placeholder, aria, id, name))
						continue;

					// Label extraction
					String label = getAssociatedLabel(page, el, id);

					// ✅ Smart name generation
					NameResult nameResult = generateSmartName(tag, label, placeholder, text, aria, name, id);

					// 🚫 Skip still bad names
					if (nameResult.generatedName.length() < 3)
						continue;

					// Locator
					LocatorResult locator = generateLocator(id, name, clazz, tag);

					String elementType = mapElementType(tag);

					writer.append(String.join(",", escape(url), escape(title), elementType, tag, escape(text),
							escape(id), escape(name), escape(clazz), escape(placeholder), nameResult.generatedName,
							locator.strategy, escape(locator.value), nameResult.source));
					writer.append("\n");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ---------- Helper Methods ----------

	private static boolean isVisible(ElementHandle el) {
		try {
			return (Boolean) el.evaluate("e => !!(e.offsetWidth || e.offsetHeight || e.getClientRects().length)");
		} catch (Exception e) {
			return false;
		}
	}

	private static String safe(String val) {
		return val == null ? "" : val.trim();
	}

	private static String escape(String val) {
		if (val == null)
			return "";
		return val.replace(",", " ").replace("\n", " ").replace("\r", " ");
	}

	private static String mapElementType(String tag) {
		switch (tag) {
		case "input":
			return "input";
		case "button":
			return "button";
		case "select":
			return "dropdown";
		case "textarea":
			return "textarea";
		case "a":
			return "link";
		default:
			return tag;
		}
	}

	private static String getAssociatedLabel(Page page, ElementHandle el, String id) {
		try {
			if (!id.isEmpty()) {
				ElementHandle label = page.querySelector("label[for='" + id + "']");
				if (label != null)
					return safe(label.innerText());
			}

			ElementHandle parent = el.querySelector("xpath=ancestor::label");
			if (parent != null)
				return safe(parent.innerText());

		} catch (Exception ignored) {
		}

		return "";
	}

	static class NameResult {
		String generatedName;
		String source;
	}

	private static NameResult generateName(String label, String placeholder, String text, String aria, String name,
			String id) {

		NameResult result = new NameResult();

		if (!label.isEmpty()) {
			result.generatedName = normalize(label);
			result.source = "label";
		} else if (!placeholder.isEmpty()) {
			result.generatedName = normalize(placeholder);
			result.source = "placeholder";
		} else if (!text.isEmpty()) {
			result.generatedName = normalize(text);
			result.source = "visible_text";
		} else if (!aria.isEmpty()) {
			result.generatedName = normalize(aria);
			result.source = "aria-label";
		} else if (!name.isEmpty()) {
			result.generatedName = normalize(name);
			result.source = "name";
		} else {
			result.generatedName = normalize(id);
			result.source = "id";
		}

		return result;
	}

	private static String normalize(String input) {
		input = input.toLowerCase();
		input = input.replaceAll("[^a-z0-9 ]", " ");
		input = input.trim().replaceAll("\\s+", " ");

		String[] parts = input.split(" ");
		StringBuilder camel = new StringBuilder();

		for (int i = 0; i < parts.length; i++) {
			if (i == 0) {
				camel.append(parts[i]);
			} else {
				camel.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
			}
		}
		return camel.toString();
	}

	static class LocatorResult {
		String strategy;
		String value;
	}

	private static LocatorResult generateLocator(String id, String name, String clazz, String tag) {
		LocatorResult result = new LocatorResult();

		if (!id.isEmpty()) {
			result.strategy = "id";
			result.value = "#" + id;
		} else if (!name.isEmpty()) {
			result.strategy = "name";
			result.value = "[name='" + name + "']";
		} else if (!clazz.isEmpty()) {
			String firstClass = clazz.split(" ")[0];
			result.strategy = "css";
			result.value = tag + "." + firstClass;
		} else {
			result.strategy = "xpath";
			result.value = "//" + tag;
		}

		return result;
	}

	private static boolean shouldIgnore(String tag, String text, String placeholder, String aria, String id,
			String name) {

		// Ignore empty useless elements
		if (text.isEmpty() && placeholder.isEmpty() && aria.isEmpty() && id.isEmpty() && name.isEmpty())
			return true;

		// Ignore very long text (dropdown bulk text issue)
		if (text.length() > 40)
			return true;

		// Ignore generic buttons
		String lower = text.toLowerCase();
		if (lower.equals("ok") || lower.equals("cancel") || lower.equals("close"))
			return true;

		return false;
	}

	private static NameResult generateSmartName(String tag, String label, String placeholder, String text, String aria,
			String name, String id) {

		NameResult result = new NameResult();
		String base = "";

		if (!label.isEmpty())
			base = label;
		else if (!placeholder.isEmpty())
			base = placeholder;
		else if (!aria.isEmpty())
			base = aria;
		else if (!text.isEmpty())
			base = text;
		else if (!name.isEmpty())
			base = name;
		else
			base = id;

		base = cleanText(base);

// 🔥 Special handling for dropdown
		if (tag.equals("select")) {
			base = base + " dropdown";
		}

// 🔥 Limit words (avoid long names)
		String[] words = base.split(" ");
		if (words.length > 3) {
			base = words[0] + " " + words[1];
		}

		result.generatedName = normalize(base);
		result.source = "smart";

		return result;
	}

	private static String cleanText(String input) {
		if (input == null)
			return "";

		input = input.replaceAll("[^a-zA-Z0-9 ]", " ");
		input = input.replaceAll("\\s+", " ").trim();

		// Remove repeated words
		Set<String> seen = new LinkedHashSet<>(Arrays.asList(input.split(" ")));
		return String.join(" ", seen);
	}
}// EOF