package pw.pages.minop;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import ai.PWActions;
import pw.base.PWBaseTest;

public class MinopCMSPage {
	public MinopCMSPage(Page page) {
	}

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

	public boolean navigate() {
		try {
			PWActions.navigate("http://172.18.105.102:8082/cms/canteens", "redirect to canteen page");

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

	public boolean ClickOnCanteen() {
		try {
			PWActions.click("//select[@id='OrderCanteenId']", "CLick on canteen ");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	// TC_01---------------------------------------------------------------------------------------------------------------------------------
	public boolean CheckIsDisplayPolicy() {
		try {
			PWActions.click("//a[@class='btn margin-left-15 trans_btnnew']", "CLick on View Policy ");
			PWActions.isVisible("(//h4[@class='modal-title'])[3]", "check policy is visible");
			PWActions.isVisible("(//button[@class='close'])[3]", " Click on close tab");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	// TC_02---------------------------------------------------------------------------------------------------------------------------------

	public boolean AddToCart() {
		try {

			PWActions.select("// select[@id='OrderCanteenId']", "Shelter canteen", "canteen is selected");
			PWActions.waitFor("//select[@id='OrderSlotId']", "wait forslot name", 5);
			PWActions.select("//select[@id='OrderSlotId']", "Lunch (Lunch)", "Slot is selected");
			PWActions.click("//button[@class='btn btn-sm save_btn menu-card-add-btn']", "Click on  Add meal one time ");
			PWActions.isVisible("//div[contains(text(),'added to order')]", " Click on close tab");
			PWActions.click("//button[@id='btnOpenCart']", "Click on Cart");
			PWActions.isVisible("//div[@class='ct-item-left']", "selcted order is display");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	// TC_03---------------------------------------------------------------------------------------------------------------------------------

	public boolean PlaceOrder() {
		try {

			PWActions.isVisible("//input[@id='OrderRemarks']", "Click on instructions");
			PWActions.fill("//input[@id='OrderRemarks']", "Please be on Time", "entered instructions");
			PWActions.click("//button[@id='btnPlaceOrder']", "Clicked on place Order");
			PWActions.isVisible("//h2[@id='swal2-title']", "Confirm order pop up display");
			PWActions.click("//button[@class='swal2-confirm swal-confirm-button-class swal2-styled']",
					"Click on  place order");
			PWActions.isVisible("//h2[@id='swal2-title']", "Order placed pop up mesage is display");
			PWActions.click("//button[@class='swal2-confirm swal-confirm-button-class swal2-styled']",
					"Click on Ok tab");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	// TC_04---------------------------------------------------------------------------------------------------------------------------------

	public boolean viewOrder() {
		try {

			PWActions.click("//li[@class='nav-item']", "Clicked on  My Orders");
			PWActions.click(
					"//table[@id='tblMyOrders']//tbody//tr[1]//td[7]//a[@class='btn btn-xs seagreen_btnnew tooltips']",
					"Clicked on view");

			PWActions.isVisible("//h4[text()='Order Detail']", "Order is dislay");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	// TC_05---------------------------------------------------------------------------------------------------------------------------------

	public boolean CancelOrder() {
		try {

			PWActions.click("//li[@class='nav-item']", "Clicked on  My Orders");
			PWActions.click(
					"//table[@id='tblMyOrders']//tbody//tr[1]//td[7]//a[@class='btn btn-xs red_btnnew tooltips']",
					"Clicked on Cancel");
			PWActions.isVisible("//h2[@id='swal2-title']", "cancel order pop up display");
			PWActions.click("//button[@class='swal2-confirm swal-confirm-button-class swal2-styled']",
					"Clicked on cancel order");
			PWActions.isVisible("//h2[@id='swal2-title']", " cancelled pop up display");
			PWActions.click("//button[@class='swal2-confirm swal-confirm-button-class swal2-styled']",
					"Clicked on OK tab");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean CheckStatus() {
		try {

			PWActions.getText("//table[@id='tblMyOrders']//tbody//tr[1]//td[6]//span[@class='purple_font']",
					"Cancelled", "status change as cancelled");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	// TC_06---------------------------------------------------------------------------------------------------------------------------------

	public boolean vali_msg_fr_maxcount() {
		try {
			// Add to cart
			PWActions.select("// select[@id='OrderCanteenId']", "Shelter canteen", "canteen is selected");
			PWActions.waitFor("//select[@id='OrderSlotId']", "wait forslot name", 5);
			PWActions.select("//select[@id='OrderSlotId']", "Lunch (Lunch)", "Slot is selected");
			PWActions.click("//button[@class='btn btn-sm save_btn menu-card-add-btn']", "Click on  Add meal one time ");
			PWActions.isVisible("//div[contains(text(),'added to order')]", " Click on close tab");
			PWActions.click("//button[@id='btnOpenCart']", "Click on Cart");
			PWActions.isVisible("//div[@class='ct-item-left']", "selcted order is display");

			// doubleclick
			PWActions.doubleClick("(//button[@class='ct-qty-btn'])[2]", "Clicked For increase item");
			PWActions.doubleClick("(//button[@class='ct-qty-btn'])[2]", "Clicked For increase item");

			// placeorder
			PWActions.isVisible("//input[@id='OrderRemarks']", "Click on instructions");
			PWActions.fill("//input[@id='OrderRemarks']", "Please be on Time", "entered instructions");
			PWActions.click("//button[@id='btnPlaceOrder']", "Clicked on place Order");
			PWActions.isVisible("//div[text()='Only 2 items available for 'Poha']", "Validation message is display");

		} catch (Exception e) {
			PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
			return false;
		}
		return true;
	}
	// TC_07--------------------------------------------------------------------------------------------------------------------------------

	public boolean verifyOrderCutoffMessage(String locator) {

		PWActions.select("// select[@id='OrderCanteenId']", "Shelter canteen", "canteen is selected");
		PWActions.waitFor("//select[@id='OrderSlotId']", "wait forslot name", 5);
		PWActions.select("//select[@id='OrderSlotId']", "Lunch (Lunch)", "Slot is selected");
		java.time.LocalTime now = java.time.LocalTime.now();
		java.time.LocalTime cutoffTime = java.time.LocalTime.of(14, 0);

		Page page = PWBaseTest.getPage();

		String expectedMessage = "Order cutoff time has passed for this slot. You cannot place orders.";

		boolean isMessagePresent = false;

		if (page.locator(locator).count() > 0) {
			String message = page.locator(locator).innerText().trim();
			isMessagePresent = message.contains("Order cutoff time has passed");
		}

		if (now.isAfter(cutoffTime) || now.equals(cutoffTime)) {
			return isMessagePresent;
		} else {
			return !isMessagePresent;
		}
	}

	//TC_08---------------------------------------------------------------------------------------------------------------------
	public boolean CheckBalancedAmount() {
	    try {
	        Page page = PWBaseTest.getPage();

	        // ✅ Step 1: Get initial total amount
	        String totalText = page.locator("//div[@class='col-sm-4']").innerText().replaceAll("[^0-9]", "");
	        int totalAmount = Integer.parseInt(totalText);
	        System.out.println("total amount" + totalAmount);

	        // ✅ Step 2: Perform actions
	        PWActions.select("//select[@id='OrderCanteenId']", "Shelter canteen", "canteen is selected");
	        PWActions.waitFor("//select[@id='OrderSlotId']", "wait for slot name", 10);
	        PWActions.select("//select[@id='OrderSlotId']", "Dinner (Dinner)", "Slot is selected");
	        PWActions.click("//button[@class='btn btn-sm save_btn menu-card-add-btn']", "Click on Add meal");
	        PWActions.isVisible("//div[contains(text(),'added to order')]", "Item added");
	        PWActions.click("//button[@id='btnOpenCart']", "Click on Cart");
	        PWActions.isVisible("//div[@class='ct-item-left']", "Selected order is displayed");

	        // Place order
	        PWActions.fill("//input[@id='OrderRemarks']", "Please be on Time", "entered instructions");
	        PWActions.click("//button[@id='btnPlaceOrder']", "Clicked on place Order");
	        PWActions.click("//button[contains(@class,'swal2-confirm')]", "Confirm order");
	        PWActions.click("//button[contains(@class,'swal2-confirm')]", "Click OK");

	        // ✅ Step 3: Get used amount (₹100.00)
	        String usedText = page.locator("//b[contains(.,'You Pay')]").innerText().replaceAll("[^0-9]", "");
	        int usedAmount = Integer.parseInt(usedText);
	        System.out.println("used amount" + usedAmount);

	        // ✅ Step 4: Calculate expected remaining
	        int expectedRemainingAmount = totalAmount - usedAmount;
	        System.out.println("remaining amount" + expectedRemainingAmount);

	        // ✅ Step 5: Get updated remaining amount from UI
	        String remainingText = page.locator("//div[@class='col-sm-4']").innerText().replaceAll("[^0-9]", "");
	        int actualRemainingAmount = Integer.parseInt(remainingText);

	        // ✅ Step 6: Compare
	        if (expectedRemainingAmount != actualRemainingAmount) {
	            PWBaseTest.getFailureContext().setErrorMessage(
	                "Amount mismatch. Expected: " + expectedRemainingAmount + 
	                " but found: " + actualRemainingAmount
	            );
	            return false;
	        }

	    } catch (Exception e) {
	        PWBaseTest.getFailureContext().setErrorMessage(e.getMessage());
	        return false;
	    }
	    return true;
	}
	
	
	
	
	
	
	
	
	 

}