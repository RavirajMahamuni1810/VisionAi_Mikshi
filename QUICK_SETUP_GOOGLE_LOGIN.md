## 🚀 Quick Setup Guide - Google Login Automation

### Step 1: Log In Manually (One-time setup)
1. Open: https://dev.vision.mikshi.ai/app
2. Click "Continue with Google"
3. Enter your Google credentials
4. Complete the login process
5. **Stay on the dashboard** (verify you see "Dashboard" heading)

✅ Your Google session is now saved to `playwright-user-data/` directory

### Step 2: Run Your Test
```java
@TestMeta(user = UserType.ADMIN, navPath = "")
@Test(dataProvider = "loginData", enabled = true, priority = 1, groups = { "Smoke" })
public void M_689_VisionAi_Login_01(Method method, Map<String, String> testData) {
    VisionAI_LoginPage visionLoginPage = new VisionAI_LoginPage(getPage());
    
    // This should NOW work without asking for email!
    if (visionLoginPage.Isdisplaydashboard()) {
        PWLog.Pass(className, "✅ Dashboard displayed successfully");
    } else {
        PWLog.Fail(className, "Dashboard not displayed");
    }
}
```

### Step 3: What Happens Now
✅ First Test Run:
  - Detects persistent context has Google session
  - Automatically loads saved session
  - Skips login flow
  - Dashboard is ready immediately
  - Tests run 60+ seconds faster!

✅ Subsequent Test Runs:
  - Same as above
  - Uses saved session every time
  - No manual login needed again

---

## ⚙️ If You Need to Change Google Account

### Option A: Clear and Re-login
```java
// In your test code or main method, run this ONCE:
PWBaseTest.deletePersistentContextData();

// Then manually log in again
// Next test runs will use the new account
```

### Option B: Delete Folder Manually
1. Stop Eclipse/IDE
2. Delete folder: `C:\Users\MN001752\git\repository\Miksh\playwright-user-data\`
3. Manually log in to the web app again
4. Start tests

---

## 📋 Expected Output

### ✅ Correct (Already Logged In):
```
✅ Already logged in via persistent context. Skipping login.
✅ Dashboard displayed successfully
```

### ❌ Wrong (Asking for Email):
```
❌ Login Error: Email input not found
```

If you see this, run: `PWBaseTest.deletePersistentContextData();`

---

## 🔧 Verify Your Setup

Run this test to verify everything works:

```java
@Test
public void verifyAutoLogin() {
    Page page = getPage();
    
    // Should be on dashboard without any login prompts
    String url = page.url();
    System.out.println("Current URL: " + url);
    
    boolean isDashboardVisible = page.locator("//h1[text()='Dashboard']").isVisible();
    System.out.println("Dashboard Visible: " + isDashboardVisible);
    
    assertTrue(isDashboardVisible, "Dashboard should be visible after auto-login");
}
```

---

## 📁 What Gets Saved

When you log in manually with Google, this directory stores:
- `playwright-user-data/`
  - Cookies (including Google session)
  - Local storage
  - Authentication tokens
  - Browser state

This is completely local and not uploaded anywhere.

---

## ❌ If You Still Have Issues

1. **Clear everything:**
   ```java
   PWBaseTest.deletePersistentContextData();
   PWBaseTest.deleteStorageFiles();
   ```

2. **Manually log in again** to https://dev.vision.mikshi.ai/app

3. **Verify manual login works** (dashboard shows)

4. **Run your test** - should now auto-detect session

5. **Check logs** for any error messages about XPath not found

---

**That's it! Your automation should now work with Google login without repeatedly asking for email.** ✨

Enjoy faster, more reliable tests! 🚀
