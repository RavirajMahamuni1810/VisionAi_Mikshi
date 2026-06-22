package pw.pages.minop;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GooglePage {

	private final Page page;

	public GooglePage(Page page) {
		this.page = page;
	}

	private Locator searchBox() {
		return page.locator("textarea[name='q']");
	}

	public void open() {
		page.navigate("https://www.google.com");
	}

	public String getTitle() {
		return page.title();
	}

	public void search(String text) {
		searchBox().fill(text);
		searchBox().press("Enter");
	}
}