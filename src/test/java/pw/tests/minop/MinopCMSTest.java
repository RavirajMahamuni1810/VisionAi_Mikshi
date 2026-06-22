package pw.tests.minop;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pw.base.PWBaseTest;
import pw.base.TestMeta;
import pw.base.UserType;
import pw.pages.minop.MinopLoginPage;
import pw.pages.minop.MinopCMSPage;
import pw.utils.PWLog;

public class MinopCMSTest extends PWBaseTest {
	String sheetName = "cms";

	// TC_01------------------------------------------------------------------------------------------------------------------
	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 1, groups = { "Smoke" })
	public void M_689_Minop_canteen_01(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "Verify After click on view policy link policy page is open");

		if (MinopCMSPage.CheckIsDisplayPolicy()) {
			PWLog.Pass(className, "After click on view policy policy is display");
		} else {
			PWLog.Fail(className,
					"Policy is display after click on view policy" + PWBaseTest.getFailureContext().getErrorMessage());
			System.out.println(" hello ");
		}

	}
	// TC_02------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 2, groups = { "Smoke" })
	public void M_689_Minop_canteen_02(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "Verify employee is able Add to cart meal whne meal is available");

		if (MinopCMSPage.AddToCart()) {
			PWLog.Pass(className, "Employee has selected canteen and slot");
		} else {
			PWLog.Fail(className,
					"Employee has not selected canteen and slot" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}

	// TC_03------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 3, groups = { "Smoke" })
	public void M_689_Minop_canteen_03(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, " Verify user is able Add instruction and place order");

		if (MinopCMSPage.AddToCart()) {
			PWLog.Pass(className, "Employee has selected canteen and slot");
		} else {
			PWLog.Fail(className,
					"Employee has not selected canteen and slot" + PWBaseTest.getFailureContext().getErrorMessage());
		}

		if (MinopCMSPage.PlaceOrder()) {
			PWLog.Pass(className, "Employee has selected canteen and slot");
		} else {
			PWLog.Fail(className,
					"Employee has not selected canteen and slot" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	// TC_04------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 4, groups = { "Smoke" })
	public void M_689_Minop_canteen_04(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, " Verify user is able  to view order");

		if (MinopCMSPage.viewOrder()) {
			PWLog.Pass(className, "Employee is able to view order");
		} else {
			PWLog.Fail(className,
					"Employee is not  able to view order" + PWBaseTest.getFailureContext().getErrorMessage());
		}

	}
	// TC_05------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 5, groups = { "Smoke" })
	public void M_689_Minop_canteen_05(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, " Verify user is able Add instruction and place order");

		if (MinopCMSPage.CancelOrder()) {
			PWLog.Pass(className, "Employee is able to cancel order");
		} else {
			PWLog.Fail(className,
					"Employee is not  able to cancel order" + PWBaseTest.getFailureContext().getErrorMessage());
		}

	}
	// TC_06------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 6, groups = { "Smoke" })
	public void M_689_Minop_canteen_06(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, " Verify user is able Add more item than available");

		if (MinopCMSPage.vali_msg_fr_maxcount()) {
			PWLog.Pass(className, "when employee Add more than availableitem validation message is display");
		} else {
			PWLog.Fail(className,
					"when employee Add more than availableitem validation message is not display" + PWBaseTest.getFailureContext().getErrorMessage());
		}

	}
	
	// TC_07------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = false, priority = 7, groups = { "Smoke" })
	public void M_689_Minop_canteen_07(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "Verify cut off time validation message is display when cutoof time is passed");

		if (MinopCMSPage.verifyOrderCutoffMessage("//div[contains(@class,'alert-danger')]")) {
		    PWLog.Pass(className, "Cutoff time message validation passed");
		} else {
		    PWLog.Fail(className, "Cutoff time validation failed");
		}
 
		}
	// TC_08------------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "cms/orders")
	@Test(dataProvider = "loginData", enabled = true, priority = 8, groups = { "Smoke" })
	public void M_689_Minop_canteen_08(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		MinopCMSPage MinopCMSPage = new MinopCMSPage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "Verify cut off time validation message is display when cutoof time is passed");

		if (MinopCMSPage.CheckBalancedAmount( )) {
		    PWLog.Pass(className, "Check Amount");
		} else {
		    PWLog.Fail(className, " Not check amount");
		}
 
		}
	 
	
	@DataProvider(name = "loginData")
	public Object[][] loginData(Method method) {
		Map<String, String> rowData = GetExcelRow(method.getName(), "", sheetName);
		return new Object[][] { { rowData } };
	}
}// EOF
