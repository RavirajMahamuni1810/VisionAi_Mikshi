package pw.pages.minop;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter; 
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
 
import ai.PWActions;
import pw.base.PWBaseTest;

public class NotificationRulePage

{

	private Page page;
	public String exceptionDesc;

	public NotificationRulePage(Page page) {
		this.page = page;
	}

	//
	public boolean CheckValidation() {
		try {
			PWActions.click("//a[@id='AddRules']", "Click on Add rule");
			PWActions.isVisible("//h4[text()='Rules Configuration']", "Configuration page is display");
			// ruletitle
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
			PWActions.isVisible("//input[@class='form-control field_error']", " title error meassge is display");
			PWActions.click("id =RuleTitle", "Click on rule title");
			PWActions.fill("id=RuleTitle", "Attendence", " rule title Added");
			// description
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
			PWActions.isVisible("//input[@class='form-control field_error']", " description error meassge is display");
			PWActions.fill("//input[@placeholder='Enter Description']", "Att App", "Enter in Description");
			// event
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
			PWActions.isVisible("//div[text()='Please select Event.']", " event error meassge is display");
			PWActions.select("//select[@id='ddleventmodulewise']", "Attendance Approval", "select Attendance Approval");
			// Operator
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
			PWActions.isVisible("//input[@class='form-control field_error']", "operator error meassge is display");
			PWActions.select("//select[@id='Operator']", "=", "select = operator");
			// Value
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
			PWActions.isVisible("//input[@class='form-control field_error']", " description error meassge is display");
			PWActions.fill("//input[@placeholder='Please Enter']", "1", "Value Added");
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");

//			PWActions.click("//label[contains(., 'In- App Notification')]//input", "Click on checkbox");
//			PWActions.select("//select[@id='InAppReporting']", "Request Person", "select Request Person");
//			
//			PWActions.click("//label[contains(., 'Email')]//input", "Click on checkbox");
//			PWActions.select("//select[@id='select2-EmailReporting-container']", "Request Person", "select Request Person");
//			
//			
//			PWActions.click("//label[contains(., 'Push-Notification (Mobile)')]//input", "Click on checkbox");
//			PWActions.select("//select[@id=select2-PushReporting-container']", "Request Person", "select Request Person");

//			PWActions.click("id =InAppStartTime", "Click on In AppStart Time");
//			PWActions.clear("id =InAppStartTime", "clear In AppStart Time");
//			PWActions.fill("id =InAppStartTime", "09:00", "Value Added at In AppStart Time ");

			PWActions.click("//button[@id='btnRuleDetailsSubmit']", "Click on save");

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

	// TC_02--------------------------------------------------------------------------------------------------------------------
	public boolean EditNotificcationrule() {
		try {

			PWActions.click(
					"//table[@id='tblNotificationruleConfigDetails']//tbody//tr[1]//td[11]//a[@class='editclass btn seagreen_btnnew tooltips']",
					"Click on edit");

			PWActions.fill("//input[@placeholder='Please Enter']", "9", "Value Added");
			PWActions.click("id =btnRuleDetailsSubmit", "Click on save");
		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	// TC_03--------------------------------------------------------------------------------------------------------------------
	public boolean Checkview() {
		try {
			PWActions.click(
					"//table[@id='tblNotificationruleConfigDetails']//tbody//tr[1]//td[11]//a[@class='quickviewclass btn blue_btnnew tooltips']",
					"Click on view");

			PWActions.isVisible("//h5[@id='quickViewModalLabel']", " details display");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	// TC_04--------------------------------------------------------------------------------------------------------------------
	public boolean CheckRadiobutton() {
		try {
			String toggleXpath = "//table[@id='tblNotificationruleConfigDetails']//tbody//tr[1]//input[@type='checkbox']";

			// Click to make INACTIVE
			PWActions.click(toggleXpath, "Click on toggle button");

			// Wait for inactive message (increase timeout)
			String inactiveMsg = "//div[contains(text(),'Inactive Successfully')]";
			PWActions.waitFor(inactiveMsg, "Wait for inactive message", 5000);
			PWActions.isVisible(inactiveMsg, "Inactive message displayed");

			// Click again to make ACTIVE
			PWActions.click(toggleXpath, "Click on toggle button again");

			String activeMsg = "//div[contains(text(),'Active Successfully')]";
			PWActions.waitFor(activeMsg, "Wait for active message", 5000);
			PWActions.isVisible(activeMsg, "Active message displayed");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	// TC_05--------------------------------------------------------------------------------------------------------------------
	public boolean Addleave() {
		try {
			
			PWActions.waitFor( "//a[@id='btnAddLeave']", "Wait for leave type ", 30);
			PWActions.click("//a[@id='btnAddLeave']", "Click on Add leave tab");
			PWActions.waitForHidden("//select[@id='ddlLeaveType']",  "wait for element", 20);
			PWActions.click("//select[@id='ddlLeaveType']", "Click on leave dropdown");
			PWActions.select( "//select[@id='ddlLeaveType']",  "TL", "select TLleave");
			 

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean selectDateFromToday(int daysFromToday) {
		try {

			LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
			int targetDay = targetDate.getDayOfMonth();
			int targetMonth = targetDate.getMonthValue();
			int targetYear = targetDate.getYear();

			Locator monthHeader = page.locator( "//div[contains(@class,'datepicker-days') and contains(@style,'block')]//th[@class='datepicker-switch']");
			monthHeader.waitFor(); // ✅ wait properly

			String displayedMonthYear = monthHeader.textContent().trim();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", java.util.Locale.ENGLISH);
			YearMonth displayedYM = YearMonth.parse(displayedMonthYear, formatter);

			while (displayedYM.getMonthValue() != targetMonth || displayedYM.getYear() != targetYear) {
				if (displayedYM.isBefore(YearMonth.of(targetYear, targetMonth))) {
					page.locator("//th[@class='next']").click();
				} else {
					page.locator("//th[@class='prev']").click();
				}

				monthHeader.waitFor(); // ✅ wait after click
				displayedMonthYear = monthHeader.textContent().trim();
				displayedYM = YearMonth.parse(displayedMonthYear, formatter);
			}

			// ✅ target only visible calendar
			Locator days = page.locator(
					"//div[contains(@class,'datepicker-days') and contains(@style,'block')]//td[contains(@class,'day') and not(contains(@class,'old')) and not(contains(@class,'new'))]");

			int count = days.count();

			for (int i = 0; i < count; i++) {
				String dayText = days.nth(i).innerText().trim(); // ✅ use innerText

				if (dayText.equals(String.valueOf(targetDay))) {
					days.nth(i).click();
					return true;
				}
			}

			exceptionDesc = "Target day not found in date picker";

		} catch (Exception e) {
			exceptionDesc = e.getMessage();
			return false;
		}

		return false;
	}

	public boolean selecttxtFromDate() {
		try {
			PWActions.click("//input[@id='txtFromDate']", "Click on from date");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean selecttxtToDate() {
		try {
			PWActions.click("//input[@id='txtToDate']", "Click on To  date");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean EnterresonNdSave() {
		try {
			
			
			
			PWActions.fill("//textarea[@id='txtReason']", "tarveling leave ", " enter reason");
			PWActions.click("//button[@id='btnSave']", "Click on save tab");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	//TC_07----------------------------------------------------------------------------------
	public boolean WebNotificationDisplay() {
		try {
			PWActions.refresh( "refresh page");
			PWActions.refresh( "refresh page");
			PWActions.waitFor( "//button[@id='btnnoti']", "Wait for  notification", 30);
			PWActions.click( "//button[@id='btnnoti']", "Click on Notification icon");
            PWActions.isVisible("//h6[text()='Leave Request savi']", " web notification is display ");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	//TC_08----------------------------------------------------------------------------------
		public boolean WebNotificationatmail() {
			try {
				PWActions.refresh( "refresh page");
				PWActions.navigate( "https://yopmail.com/en/", "Navigate to yopmail");
				PWActions.fill("//input[@class='ycptinput']", "Akshay@yopmail.com", "Entered reporting person mail");
				PWActions.click( "//button[@class='md']", "Click on  Arrow");
	            PWActions.isVisible("(//div[text()='Leave Request savi'])[2]", " web notification is display at reporting person mail ");
	            PWActions.isVisible("(//div[text()='Leave Request savi'])[3]", " web notification is display at reporting person mail");
			} catch (Exception e) {
				PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
				return false;
			}
			return true;
		} 
	
	 //TC_10----------------------------------------------------------------------------------
		public boolean Approveleave() {
			try {
			 
				PWActions.click( "//table[@id='tblleaveapproval']//tbody//tr[1]//a[contains(@class,'approve')]",  "Approve leave");
	            
			} catch (Exception e) {
				PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
				return false;
			}
			return true;
		}
	 
		//TC_11_webnotification to employee of Approve leave---------
		public boolean LA_WebNotificationDisplay_emp() {
			try {
				PWActions.refresh( "refresh page");
				PWActions.refresh( "refresh page");
				PWActions.waitFor( "//button[@id='btnnoti']", "Wait for  notification", 30);
				PWActions.click( "//button[@id='btnnoti']", "Click on Notification icon");
	            PWActions.isVisible("//h6[text()='Leave Approved savi']", " web notification is display ");

			} catch (Exception e) {
				PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
				return false;
			}
			return true;
		}
		//TC_12----------------------------------------------------------------------------------
		public boolean LA_WebNotificationatmail_Emp() {
			try {
				PWActions.refresh( "refresh page");
				PWActions.navigate( "https://yopmail.com/en/", "Navigate to yopmail");
				PWActions.fill("//input[@class='ycptinput']", "savi@yopmail.com", "Entered reporting person mail");
				PWActions.click( "//button[@class='md']", "Click on  Arrow");
	            PWActions.isVisible("(//div[text()='Leave Approved savi'])[1]", " web notification is display at reporting person mail ");
	            PWActions.isVisible("(//div[text()='Leave Approved savi'])[2]", " web notification is display at reporting person mail");
			} catch (Exception e) {
				PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
				return false;
			}
			return true;
		} 
		//TC_13----------------------------------------------------------------------------------
				public boolean rejectleave() {
					try {
					 
						PWActions.click( "//table[@id='tblleaveapproval']//tbody//tr[1]//a[@class='btn red_btnnew reject tooltips']",  "Reject leave");
						PWActions.click( "//textarea[@id='ApprovalReason']",  " Enter in Reject leave");
						PWActions.fill( "//textarea[@id='ApprovalReason']",  "Not Allowed leave",  "reason entered for leave reject");
						PWActions.click( "//button[@CLASS=' btn save_btn']",  "Click on save");
						 
						 
			             
					} catch (Exception e) {
						PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
						return false;
					}
					return true;
				} 
				//TC_14_webnotification to employee of Approve leave---------
				public boolean LR_WebNotificationDisplay_emp() {
					try {
						PWActions.refresh( "refresh page");
						PWActions.refresh( "refresh page");
						PWActions.waitFor( "//button[@id='btnnoti']", "Wait for  notification", 30);
						PWActions.click( "//button[@id='btnnoti']", "Click on Notification icon");
			            PWActions.isVisible("//h6[text()='Leave Rejected savi']", " web notification is display ");

					} catch (Exception e) {
						PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
						return false;
					}
					return true;
				}
				//TC_15----------------------------------------------------------------------------------
				public boolean LR_WebNotificationatmail_Emp() {
					try {
						PWActions.refresh( "refresh page");
						PWActions.navigate( "https://yopmail.com/en/", "Navigate to yopmail");
						PWActions.fill("//input[@class='ycptinput']", "savi@yopmail.com", "Entered reporting person mail");
						PWActions.click( "//button[@class='md']", "Click on  Arrow");
			            PWActions.isVisible("(//div[text()='Leave Rejected savi'])[1]", " web notification is display at reporting person mail ");
			            PWActions.isVisible("//div[text()='Leave Rejected savi'])[2]", " web notification is display at reporting person mail");
					} catch (Exception e) {
						PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
						return false;
					}
					return true;
				}
	
	
	

}
