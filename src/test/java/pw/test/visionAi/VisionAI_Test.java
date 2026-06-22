
package pw.test.visionAi;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pw.base.PWBaseTest;
import pw.base.TestMeta;
import pw.base.UserType;
import pw.pages.visionAi.VisionAI_LoginPage;
import pw.utils.PWLog;

public class VisionAI_Test extends PWBaseTest {

	String sheetName = "loginVisionAI";

	@TestMeta(user = UserType.ADMIN, navPath = "")
	@Test(dataProvider = "loginData", enabled = true, priority = 1, groups = { "Smoke" })
	public void M_689_VisionAi_Login_01(Method method, Map<String, String> testData) 
	{

		VisionAI_LoginPage visionLoginPage = new VisionAI_LoginPage(getPage());
		String className = this.getClass().getSimpleName();

	 

		if (visionLoginPage.Isdisplaydashboard()) {
			PWLog.Pass(className, "Dashboard displayed successfully");
		} else {
			PWLog.Fail(className, "Dashboard not displayed: " + PWBaseTest.getFailureContext().getErrorMessage());
		}

		if (visionLoginPage.ClickONTeams()) {
			PWLog.Pass(className, "Clicked upload option");
		} else {
			PWLog.Fail(className, "Unable to click upload option");
		}

	 
		if (visionLoginPage.ClickOnCapture()) {
			PWLog.Pass(className, "Click on Capture ");
		} else {
			PWLog.Fail(className, "Not Click on Capture" + PWBaseTest.getFailureContext().getErrorMessage());
		}
		if (visionLoginPage.ClickProccesedVideo()) {
			PWLog.Pass(className, " Processed and send question to video");
		} else {
			PWLog.Fail(className, "Not Processed and send question to video " + PWBaseTest.getFailureContext().getErrorMessage());
		}
 
	}
 
	@DataProvider(name = "loginData")
	public Object[][] loginData(Method method) {
		Map<String, String> rowData = GetExcelRow(method.getName(), "", sheetName);
		return new Object[][] { { rowData } };
	}
}// EOF
