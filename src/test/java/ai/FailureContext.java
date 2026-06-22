package ai;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Page;

import pw.base.PWBaseTest;

public class FailureContext {
	List<String> stepsExecuted;
	private String screenshotPath;
	private String errorMessage;
	private String url;
	private List<String> consoleLogs = new ArrayList<>();
	private List<String> failedApis = new ArrayList<>();
	private String html;
	private String attemptedLocator;
	private String stepName;
	private String action;
	List<String> consoleErrors;
	List<String> networkFailures;

	public void addConsoleLog(String log) {
		consoleLogs.add(log);
	}
	public void addFailedApi(String api) {
		failedApis.add(api);
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public List<String> getConsoleLogs() {
		return consoleLogs;
	}
	public List<String> getFailedApis() {
		return failedApis;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getAttemptedLocator() {
		return attemptedLocator;
	}
	public void setAttemptedLocator(String attemptedLocator) {
		this.attemptedLocator = attemptedLocator;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setActionContext(String stepName, String action, String locator) {
		this.stepName = stepName;
		this.action = action;
		this.attemptedLocator = locator;
	}
	public List<String> getConsoleErrors() {
		return consoleErrors;
	}
	public List<String> getNetworkFailures() {
		return networkFailures;
	}
//	public static void click(String locator, String stepName) {
//		Page page = PWBaseTest.getPage();
//		FailureContext ctx = PWBaseTest.getFailureContext();
//		ctx.setActionContext(stepName, "click", locator);
//		page.locator(locator).click();
//	}
//	public static void fill(String locator, String value, String stepName) {
//		Page page = PWBaseTest.getPage();
//		FailureContext ctx = PWBaseTest.getFailureContext();
//		ctx.setActionContext(stepName, "fill", locator);
//		page.locator(locator).fill(value);
//	}
//	public static void select(String locator, String value, String stepName) {
//		Page page = PWBaseTest.getPage();
//		FailureContext ctx = PWBaseTest.getFailureContext();
//		ctx.setActionContext(stepName, "select", locator);
//		page.locator(locator).selectOption(value);
//	}
}// EOF