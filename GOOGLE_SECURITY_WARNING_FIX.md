# Google Security Warning Fix - "This browser or app may not be secure"

## Problem Description
When running Playwright automation to login to Google, you may encounter the error:
```
This browser or app may not be secure. 
Learn more. Try using a different browser. 
If you're already using a supported browser, you can try again to sign in.
```

## Why This Happens
Google has sophisticated bot detection that identifies:
- Automated browsers (like Playwright)
- Missing or fake browser identifiers
- Suspicious browser context (no user data, etc.)
- Automation framework headers
- Rapid/unnatural user behavior

## Solutions Implemented

### 1. ✅ Stealth Mode Browser Arguments (FIXED)
Added anti-detection browser arguments in `launchBrowser()`:
```java
"--disable-blink-features=AutomationControlled"  // Hide chrome.webdriver flag
"--disable-extensions"
"--disable-plugins"
"--no-first-run"
"--no-default-browser-check"
"--disable-popup-blocking"
"--disable-features=IsolateOrigins,site-per-process"
```

### 2. ✅ Proper User-Agent (FIXED)
Set a realistic Chrome user-agent:
```java
.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36...")
```

### 3. ✅ Explicit Viewport Size (FIXED)
Changed from `null` to explicit dimensions:
```java
.setViewportSize(1920, 1080)  // Instead of null
```

### 4. ✅ Persistent Context (ALREADY GOOD)
Using `launchPersistentContext()` helps because:
- Preserves browser cache and cookies
- Reuses login sessions across test runs
- Maintains consistent browser history
- Avoids repeated Google security checks

### 5. ✅ Increased Wait Times (FIXED)
Added longer delays for Google's verification screens:
- Initial page load: **3 seconds** (was 2s)
- After email submission: **4 seconds** (was 3s)  
- Final redirect: **5 seconds** (was 4s)

### 6. ✅ Security Warning Detection (NEW)
Added code to detect and report security warnings:
```java
if (page.locator("text=/This browser or app may not be secure/").isVisible()) {
    System.err.println("❌ GOOGLE SECURITY WARNING DETECTED!");
    throw new RuntimeException("Google Security Warning: Browser detected as insecure");
}
```

## Troubleshooting Steps

### Step 1: Clear Persistent Context Data
If you still encounter the warning, clear the cached browser data:

```java
// In your test before running:
PWBaseTest.deletePersistentContextData();
```

Or manually delete: `playwright-user-data/` folder

### Step 2: Verify Browser Arguments
Ensure all stealth arguments are being applied:
```
✅ --disable-blink-features=AutomationControlled
✅ --disable-extensions
✅ --disable-plugins
✅ User-Agent: Mozilla/5.0...
✅ Viewport: 1920x1080
```

### Step 3: Check for Google 2FA
If you have 2FA enabled on your Google account, the test will fail at:
```
"Verify it's you" screen
```
Solution: Temporarily disable 2FA for test account or use app-specific passwords.

### Step 4: Try Headed Mode (No Headless)
Change `hideBrowser` to `N` in your test data to see the browser:
```
hideBrowser: N
```
This helps debug what Google is seeing.

### Step 5: Use Different Google Account
Try with a different Gmail account that has:
- No 2FA enabled
- No suspicious login history
- Fewer security restrictions

### Step 6: Contact Google Support
If none of the above work, Google may have flagged your IP/account. You'll need to:
1. Verify ownership of the Google account through their flow
2. Temporarily allow the test IP address
3. Or use a service account for automation

## Implementation Code Changes

### File: PWBaseTest.java

#### Change 1: launchBrowser() method (Line ~287)
```java
// BEFORE (OLD):
.setViewportSize(null)
.setArgs(Arrays.asList("--start-maximized", "--disable-dev-shm-usage",
        "--no-sandbox", "--disable-gpu"));

// AFTER (NEW):
.setViewportSize(1920, 1080)
.setArgs(args)  // Includes stealth arguments
.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)...")
```

#### Change 2: performLogin() method (Line ~490)
Added security warning detection:
```java
// NEW: Check for Google security warning after navigation
if (page.locator("text=/This browser or app may not be secure/").isVisible()) {
    System.err.println("❌ GOOGLE SECURITY WARNING DETECTED!");
    throw new RuntimeException("Google Security Warning: Browser detected as insecure");
}
```

## Best Practices

### ✅ DO:
- Run in headed mode first to debug
- Use persistent contexts (already doing this)
- Clear user data between test suites
- Keep browser updated to latest version
- Use realistic user-agents
- Add appropriate wait times for network latency

### ❌ DON'T:
- Run multiple login attempts in rapid succession
- Use obviously fake user-agents
- Skip wait times (causes Google detection)
- Mix automation frameworks in the same test
- Hard-code credentials (use test data files)

## Monitoring & Logging

### Logs to Watch For:
```
✅ Email entered                           // Good - email was filled
✅ Password field detected                 // Good - Google page loaded
✅ Successfully redirected from Google     // Good - login passed
❌ GOOGLE SECURITY WARNING DETECTED!       // Bad - needs troubleshooting
⚠️  Still on Google login page             // Warning - may need extra wait
```

## Additional Resources

- [Playwright Anti-Detection Guide](https://playwright.dev/docs/api/class-browser)
- [Google Account Security](https://support.google.com/accounts/answer/32040)
- [Common Automation Issues](https://blog.apify.com/how-to-bypass-web-scraping-protection/)

## Quick Fixes Summary

| Issue | Solution |
|-------|----------|
| "Not secure" warning | Clear persistent data & retry |
| Still on Google page | Increase wait times (done: 4-5s) |
| 2FA blocking login | Disable 2FA or use app password |
| Browser detected as bot | Stealth args added ✅ |
| Wrong user-agent | User-agent now set ✅ |
| Session not cached | Persistent context enabled ✅ |

## Testing the Fix

Run your test with:
```xml
<parameter name="hideBrowser">N</parameter>  <!-- Show browser to debug -->
<parameter name="recordVideo">Y</parameter>   <!-- Record video of login -->
<parameter name="debugMode">Y</parameter>    <!-- Add slow-motion (8s delays) -->
```

Then monitor the console output for any warnings or errors.

---

**Last Updated:** May 27, 2026  
**Status:** ✅ All fixes implemented in PWBaseTest.java
