package pw.tests.minop;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pw.base.PWBaseTest;
import pw.base.TestMeta;
import pw.base.UserType;
import pw.pages.minop.MinopLoginPage;
import pw.pages.minop.NotificationRulePage;
import pw.utils.PWLog;

public class NotificationRuleTest extends PWBaseTest {
	String sheetName = "Notificationrule";

	@TestMeta(user = UserType.ADMIN, navPath = "NotificationAlert/Notificationruleconfig")
	@Test(dataProvider = "loginData", enabled = false, priority = 1, groups = { "Smoke" })
	public void M_3726_Minop_Noti_Rule_01(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will verify Admin login automatically");

		if (NotificationRulePage.CheckValidation()) {
			PWLog.Pass(className,
					"All mandatory field have validation and it shows when click on save without filling them");
		} else {
			PWLog.Fail(className, " Novalidation in All field" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	// TC_02--------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.ADMIN, navPath = "NotificationAlert/Notificationruleconfig")
	@Test(dataProvider = "loginData", enabled = false, priority = 2, groups = { "Smoke" })
	public void M_3726_Minop_Noti_Rule_02(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "verify user is able to edit notification rule configuration");

		if (NotificationRulePage.EditNotificcationrule()) {
			PWLog.Pass(className, " successfully edit notification rule configuration");
		} else {
			PWLog.Fail(className, " successfully not edit notification rule configuration "
					+ PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	// TC_03--------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.ADMIN, navPath = "NotificationAlert/Notificationruleconfig")
	@Test(dataProvider = "loginData", enabled = false, priority = 3, groups = { "Smoke" })
	public void M_3726_Minop_Noti_Rule_03(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "verify user is able to see details");

		if (NotificationRulePage.Checkview()) {
			PWLog.Pass(className, " successfully view details");
		} else {
			PWLog.Fail(className, " successfully not view details" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}

	// TC_04--------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.ADMIN, navPath = "NotificationAlert/Notificationruleconfig")
	@Test(dataProvider = "loginData", enabled = false, priority = 4, groups = { "Smoke" })
	public void M_3726_Minop_Noti_Rule_04(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "verify user is able to ON and OFF radio button");

		if (NotificationRulePage.CheckRadiobutton()) {
			PWLog.Pass(className, "Radio button work properly");
		} else {
			PWLog.Fail(className, " Radio button not work properly" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}

	// TC_05 Adding 1st leave----------------------------------------------------------------------------------------------------------------------
	@TestMeta(user = UserType.EMPLOYEE, navPath = "ESS/LeaveRequest")
	@Test(dataProvider = "loginData", enabled = false, priority = 5, groups = { "Smoke" })
	public void M_3726_Minop_Noti_Rule_05(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());
		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "This Method will test the valid Employee Login using saved state.");

		if (NotificationRulePage.Addleave()) {
			PWLog.Pass(className, "Add leave");
		} else {
			PWLog.Fail(className, "Error while  Add leave " + PWBaseTest.getFailureContext().getErrorMessage());
		}
		if (NotificationRulePage.selecttxtFromDate()) {
			PWLog.Pass(className, " clicked from date");
		} else {
			PWLog.Fail(className, "Error while  clicked from date " + PWBaseTest.getFailureContext().getErrorMessage());
		}

		if (NotificationRulePage.selectDateFromToday(1)) {
			PWLog.Pass(className, " select from date");
		} else {
			PWLog.Fail(className, "not select from date " + PWBaseTest.getFailureContext().getErrorMessage());
		}
		if (NotificationRulePage.selecttxtToDate()) {
			PWLog.Pass(className, "clicked to date");
		} else {
			PWLog.Fail(className, "Error while  clicked to date " + PWBaseTest.getFailureContext().getErrorMessage());
		}

		if (NotificationRulePage.selectDateFromToday(1)) {
			PWLog.Pass(className, " select to date");
		} else {
			PWLog.Fail(className, " Not select to date" + PWBaseTest.getFailureContext().getErrorMessage());
		}
		if (NotificationRulePage.EnterresonNdSave()) {
			PWLog.Pass(className, "leave Apply  done");
		} else {
			PWLog.Fail(className, "Leave not Apply" + PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	// TC_06--------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
	@Test(dataProvider = "loginData", enabled = false, priority = 6, groups = { "Smoke" })

	public void M_3726_Minop_Noti_Rule_06(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person");

		if (NotificationRulePage.WebNotificationDisplay()) {
			PWLog.Pass(className, "web notification suits to reporting person ");
		} else {
			PWLog.Fail(className, "web notification not suits to reporting person"
					+ PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	
	// TC_07--------------------------------------------------------------------------------------------------------------

	@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
	@Test(dataProvider = "loginData", enabled = false, priority = 7, groups = { "Smoke" })

	public void M_3726_Minop_Noti_Rule_07(Method method, Map<String, String> testData) {
		MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
		NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

		String className = this.getClass().getSimpleName();
		PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person in his mail ID");

		if (NotificationRulePage.WebNotificationatmail()) {
			PWLog.Pass(className, "web notification suits to reporting person at mail ");
		} else {
			PWLog.Fail(className, "web notification not suits to reporting person at mail"
					+ PWBaseTest.getFailureContext().getErrorMessage());
		}
	}
	// TC_08 Adding 1st leave----------------------------------------------------------------------------------------------------------------------
		@TestMeta(user = UserType.EMPLOYEE, navPath = "ESS/LeaveRequest")
		@Test(dataProvider = "loginData", enabled = true, priority = 8, groups = { "Smoke" })
		public void M_3726_Minop_Noti_Rule_08(Method method, Map<String, String> testData) {
			MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
			NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());
			String className = this.getClass().getSimpleName();
			PWLog.Info(className, " Adding 2 leave");
 
			//2nd leave
			if (NotificationRulePage.Addleave()) {
				PWLog.Pass(className, "Add leave");
			} else {
				PWLog.Fail(className, "Error while  Add leave " + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.selecttxtFromDate()) {
				PWLog.Pass(className, " clicked from date");
			} else {
				PWLog.Fail(className, "Error while  clicked from date " + PWBaseTest.getFailureContext().getErrorMessage());
			}

			if (NotificationRulePage.selectDateFromToday(2)) {
				PWLog.Pass(className, " select from date");
			} else {
				PWLog.Fail(className, "not select from date " + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.selecttxtToDate()) {
				PWLog.Pass(className, "clicked to date");
			} else {
				PWLog.Fail(className, "Error while  clicked to date " + PWBaseTest.getFailureContext().getErrorMessage());
			}

			if (NotificationRulePage.selectDateFromToday(2)) {
				PWLog.Pass(className, " select to date");
			} else {
				PWLog.Fail(className, " Not select to date" + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.EnterresonNdSave()) {
				PWLog.Pass(className, "leave Apply  done");
			} else {
				PWLog.Fail(className, "Leave not Apply" + PWBaseTest.getFailureContext().getErrorMessage());
			}
		}
			
			// 3rd leave------------------------------------------------------------------------------
			// TC_08 Adding 2nd leave----------------------------------------------------------------------------------------------------------------------
			@TestMeta(user = UserType.EMPLOYEE, navPath = "ESS/LeaveRequest")
			@Test(dataProvider = "loginData", enabled = true, priority = 9, groups = { "Smoke" })
			public void M_3726_Minop_Noti_Rule_09(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());
				String className = this.getClass().getSimpleName();
				PWLog.Info(className, " Adding 2 leave");
			if (NotificationRulePage.Addleave()) {
				PWLog.Pass(className, "Add leave");
			} else {
				PWLog.Fail(className, "Error while  Add leave " + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.selecttxtFromDate()) {
				PWLog.Pass(className, " clicked from date");
			} else {
				PWLog.Fail(className, "Error while  clicked from date " + PWBaseTest.getFailureContext().getErrorMessage());
			}

			if (NotificationRulePage.selectDateFromToday(3)) {
				PWLog.Pass(className, " select from date");
			} else {
				PWLog.Fail(className, "not select from date " + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.selecttxtToDate()) {
				PWLog.Pass(className, "clicked to date");
			} else {
				PWLog.Fail(className, "Error while  clicked to date " + PWBaseTest.getFailureContext().getErrorMessage());
			}

			if (NotificationRulePage.selectDateFromToday(3)) {
				PWLog.Pass(className, " select to date");
			} else {
				PWLog.Fail(className, " Not select to date" + PWBaseTest.getFailureContext().getErrorMessage());
			}
			if (NotificationRulePage.EnterresonNdSave()) {
				PWLog.Pass(className, "leave Apply  done");
			} else {
				PWLog.Fail(className, "Leave not Apply" + PWBaseTest.getFailureContext().getErrorMessage());
 			} }
			
			//Approve request by reporting person----------------------------------------------------------------
			 
			@TestMeta(user = UserType.EMPLOYEE, navPath = "ESS/ApproveLeave")
			@Test(dataProvider = "loginData", enabled = true, priority = 10, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_10(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person");

				if (NotificationRulePage.Approveleave()) {
					PWLog.Pass(className, " Approve leave");
				} else {
					PWLog.Fail(className, " Not Approve leave" + PWBaseTest.getFailureContext().getErrorMessage());
	 			}
			}
			// TC_11__webnotification to employee of Approve leave--------------------------------------------------------------------------------------------------------------

			@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
			@Test(dataProvider = "loginData", enabled = true, priority = 11, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_11(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person");

				if (NotificationRulePage.LA_WebNotificationDisplay_emp()) {
					PWLog.Pass(className, "web notification suits to employee ");
				} else {
					PWLog.Fail(className, "web notification not suits to employee"
							+ PWBaseTest.getFailureContext().getErrorMessage());
				}
			}
			// TC_12 notification suits by mail to employee of Approve leave--------------------------------------------------------------------------------------------------------------

			@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
			@Test(dataProvider = "loginData", enabled = true, priority = 12, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_12(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person in his mail ID");

				if (NotificationRulePage.LA_WebNotificationatmail_Emp()) {
					PWLog.Pass(className, "web notification suits to reporting person at mail ");
				} else {
					PWLog.Fail(className, "web notification not suits to reporting person at mail"
							+ PWBaseTest.getFailureContext().getErrorMessage());
				}
			}
			
			//reject request by reporting person----------------------------------------------------------------
			 
			@TestMeta(user = UserType.EMPLOYEE, navPath = "ESS/ApproveLeave")
			@Test(dataProvider = "loginData", enabled = true, priority = 13, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_13(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person");

				if (NotificationRulePage.rejectleave()) {
					PWLog.Pass(className, " reject leave ");
				} else {
					PWLog.Fail(className, " Not reject leave" + PWBaseTest.getFailureContext().getErrorMessage());
	 			}
			}
			
			// TC_11__webnotification to employee of reject leave--------------------------------------------------------------------------------------------------------------

			@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
			@Test(dataProvider = "loginData", enabled = true, priority = 14, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_14(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person");

				if (NotificationRulePage.LR_WebNotificationDisplay_emp()) {
					PWLog.Pass(className, "web notification suits to employee ");
				} else {
					PWLog.Fail(className, "web notification not suits to employee"
							+ PWBaseTest.getFailureContext().getErrorMessage());
				}
			}
			// TC_15 notification suits by mail to employee of reject leave--------------------------------------------------------------------------------------------------------------

			@TestMeta(user = UserType.EMPLOYEE, navPath = "Dashboard/EmployeeDashboard")
			@Test(dataProvider = "loginData", enabled = true, priority = 15, groups = { "Smoke" })

			public void M_3726_Minop_Noti_Rule_15(Method method, Map<String, String> testData) {
				MinopLoginPage minopLoginPage = new MinopLoginPage(getPage());
				NotificationRulePage NotificationRulePage = new NotificationRulePage(getPage());

				String className = this.getClass().getSimpleName();
				PWLog.Info(className, "verify when employee Apply leave request then notification suits to reporting person in his mail ID");

				if (NotificationRulePage.LR_WebNotificationatmail_Emp()) {
					PWLog.Pass(className, "web notification suits to reporting person at mail ");
				} else {
					PWLog.Fail(className, "web notification not suits to reporting person at mail"
							+ PWBaseTest.getFailureContext().getErrorMessage());
				}
			}
	
 
	@DataProvider(name = "loginData")
	public Object[][] loginData(Method method) {
		Map<String, String> rowData = GetExcelRow(method.getName(), "", sheetName);
		return new Object[][] { { rowData } };
	}
}// EOF
