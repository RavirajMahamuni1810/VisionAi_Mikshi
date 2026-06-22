package pw.pages.minop;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import ai.PWActions;
import pw.base.PWBaseTest;

public class MinopLoginPage 
{
	public MinopLoginPage(Page page) {
	}
	//
	public boolean enterAdminUser(String user) {
		try {
			PWActions.click("//span[contains(text(),'Admin')]", "Enter Admin user");
			PWActions.fill("id=UserEmail", user, "Enter user");
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	public boolean enterAdminPassword(String pass) {
		try {
			PWActions.fill("id=Password", pass, "Enter password");
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	public boolean clickLoginBtn() {
		try {
			PWActions.clickByRole(AriaRole.BUTTON, "Login", true, "Click Login Button");
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	public boolean verifyAdminDash() {
		try {
			PWActions.waitFor("text=Employee Attrition", "Wait for Employee Attrition text", 5000);
			return true;
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}
	public boolean verifyEmployeeDash() {
		try {
			PWActions.waitFor("text=Employee Guidelines", "Wait for Employee Attrition text", 5000);
			return true;
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}
} // EOF