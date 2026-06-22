package pw.pages.visionAi;

import com.microsoft.playwright.Page;

import ai.PWActions;
import pw.base.PWBaseTest;
import pw.utils.PWLog;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;
import java.io.File;

public class VisionAI_LoginPage {

	public VisionAI_LoginPage(Page page) {
	}

	// TC_01---------------------------------------------------------------------------------------------------------------------------------------
	public boolean enterAdminUser(String user) {
		try {
			PWActions.click("//input[@placeholder='Username']", "Enter Admin user");
			PWActions.fill("//input[@placeholder='Username']", user, "Enter user");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean enterAdminPassword(String password) {
		try {

			PWActions.click("//input[@placeholder='Password']", "Enter Admin user");
			PWActions.fill("//input[@placeholder='Password']", password, "Enter password");
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean ClickSigninBtn() {
		try {

			PWActions.click("//button[contains(text(),'Sign In')]", "Clicked on sign in");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean Isdisplaydashboard() {
		try {

			boolean status = PWActions.isVisible("//h1[text()='Dashboard']", "Dashboard is displayed");

			if (status) {
				return true;
			} else {
				PWBaseTest.getFailureContext().setErrorMessage("Dashboard not displayed");
				return false;
			}

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}

	// TC_02-----------------------------------------------------------------------------------------------
	public boolean ClickONTeams() {
		try {

			PWActions.click("(//div[@class='flex items-start justify-between gap-4'])[2]", "Clicked on sign in");
			PWActions.click(
					"//button[@class='inline-flex items-center gap-2 rounded-lg bg-violet-600 px-4 py-2 text-sm font-medium text-white shadow hover:bg-violet-700 transition disabled:opacity-50 disabled:cursor-not-allowed']",
					"Clicked on sign in");
			PWActions.click(
					"//div[@class='w-9 h-9 rounded-full bg-violet-100 flex items-center justify-center flex-shrink-0']",
					"Clicked on sign in");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean Uploadfile(String fileName) {
		try {
			PWActions.uploadFile("input[type='file']", fileName, "Uploading video file");
			return true;

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}

	public boolean ClickOnCapture() {
		try {
			PWActions.waitFor("(//button[text()='Capture moments'])[1]", "wait for capture tab", 10000);

			PWActions.click("(//button[text()='Capture moments'])[1]", "Clicked on Capture");

			return true;

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}

	public boolean ClickProccesedVideo() {
		try {
//			PWActions.waitFor("(//div[@class='group relative rounded-3xl border border-slate-200 bg-white shadow-sm transition hover:shadow-md cursor-pointer'])[1]", "click on capture", 10000);
 
//			PWActions.click("(//div[@class='group relative rounded-3xl border border-slate-200 bg-white shadow-sm transition hover:shadow-md cursor-pointer'])[1]", "Clicked on Capture");
 		//	PWActions.waitFor("(//div[@class='group relative rounded-3xl border border-slate-200 bg-white shadow-sm transition hover:shadow-md cursor-pointer'])[1]", "click on capture", 10000);
 	    	PWActions.waitFor("//button[@class='inline-flex items-center gap-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg px-3 py-1.5 text-sm font-medium transition-colors']", "wait for chat tab", 30000);
 			 
			PWActions.click(
					"//button[@class='inline-flex items-center gap-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg px-3 py-1.5 text-sm font-medium transition-colors']",
					"chat about full video");
			PWActions.click("//input[@placeholder='Ask a question about the video...']", "Clicked on Chat plceholder");
			PWActions.fill("//input[@placeholder='Ask a question about the video...']", "what is happening in video",
					"question Asked");
			PWActions.click("//button[text()='Send']", "Clicked on send Tab");
			return true;

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}

	public boolean CompareText() {
		try {
			PWActions.getText("(//div[@class='prose prose-sm max-w-none text-slate-800 break-words'])[2]",
					" get text from web");

			return true;

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}

	public boolean CompareText(String expectedText) {
		try {

			String actualText = PWActions.getText( "(//div[@class='prose prose-sm max-w-none text-slate-800 break-words'])[2]", "Get Actual Text");

			System.out.println("Actual Text: " + actualText);
			System.out.println("Expected Text: " + expectedText);

			if (actualText.equals(expectedText)) {
				PWLog.Pass(PWBaseTest.getCurrentClassName(), "Text matched successfully");
				return true;
			} else {
				PWLog.Fail(PWBaseTest.getCurrentClassName(),
						"Text mismatch. Actual: " + actualText + " | Expected: " + expectedText);
				return false;
			}

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
	}
	
	//=========================== New Version==============================================
	
	
	
	// TC_01---------------------------------------------------------------------------------------------------------------------------------------
//	public boolean ClickOnSign() {
//		try {
//			PWActions.click("(//button[@type='button'])[1]", "Enter Admin user");
//	 
//
//		} catch (Exception e) {
//			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
//			return false;
//		}
//		return true;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
