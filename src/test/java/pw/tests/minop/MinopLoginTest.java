package pw.tests.minop;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pw.base.PWBaseTest;
import pw.base.TestMeta;
import pw.base.UserType;
import pw.utils.PWLog;
import pw.pages.minop.MinopLoginPage;

public class MinopLoginTest extends PWBaseTest {
	String sheetName = "login";

	@TestMeta(user = UserType.ADMIN, navPath = "Dashboard/AdminDashboard")
	@Test(dataProvider = "loginData", enabled = true, priority = 1, groups = { "Smoke" })
	public void M_689_Minop_Login_01(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will verify Admin login automatically");
	 
		if (minopLoginPage.verifyAdminDash()) {
			PWLog.Pass(className, "Admin Dashboard Displayed successfully.");
		} else {
			PWLog.Fail(className, "Error while navigating to Admin Dashboard: " + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	
	
 
	
	@TestMeta(user = UserType.ADMIN, navPath = "MasterData/CompanyMaster")
	@Test(dataProvider = "loginData", enabled = true, priority = 2, groups = { "Smoke" })
	public void M_691_Minop_Login_03(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will test the valid Login by referring to saveed admin state..");
		String methodName = method.getName();
		if (minopLoginPage.verifyAdminDash()) {
			PWLog.Pass(className, "Admin Dashboard Displayed successfully.");
		} else {
			PWLog.Fail(className, "Error while navigating to Admin Dashboard: " + PWBaseTest.getFailureContext().getErrorMessage());
		}
//	
//			
	}
	@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
	@Test(dataProvider = "loginData", enabled = true, priority = 3, groups = { "Smoke" })
	public void M_695_Minop_Login_07(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will test the valid Employee Login functionality.");
		if (minopLoginPage.verifyEmployeeDash()) {
			PWLog.Pass(className, "Employee Dashboard Displayed successfully.");
		} else {
			PWLog.Fail(className, "Error while navigating to Employee Dashboard: " + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
	@Test(dataProvider = "loginData", enabled = true, priority = 3, groups = { "Smoke" })
	public void M_697_Minop_Login_09(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will test the valid Employee Login using saved state.");
		if (minopLoginPage.verifyEmployeeDash()) {
			PWLog.Pass(className, "Employee Dashboard Displayed successfully.");
		} else {
			PWLog.Fail(className, "Error while navigating to Employee Dashboard: " + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	@DataProvider(name = "loginData")
	public Object[][] loginData(Method method) {
		Map<String, String> rowData = GetExcelRow(method.getName(), "", sheetName);
		return new Object[][] { { rowData } };
	}
}// EOF