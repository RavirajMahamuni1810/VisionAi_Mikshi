# Quick Troubleshooting Checklist for Google Login Issues

## 🔴 If You See: "This browser or app may not be secure"

### Immediate Actions (In Order)

```
☐ Step 1: CLEAR BROWSER DATA
  Run in your test base class setup:
  PWBaseTest.deletePersistentContextData();
  
  OR manually delete folder:
  playwright-user-data/
  
  Then restart your test.

☐ Step 2: RUN IN HEADED MODE
  In your test data (Excel or config):
  hideBrowser = N
  
  This shows you what the browser looks like to Google.

☐ Step 3: ENABLE SLOW MOTION
  In your test data:
  debugMode = Y
  recordVideo = Y
  
  This adds 8-second delays between actions so
  Google has time to process each step.

☐ Step 4: CHECK 2FA STATUS
  Log into your Gmail account manually:
  - Go to myaccount.google.com
  - Check "Security" tab
  - Is 2FA enabled? If YES, disable it for testing
  - Or create an App Password
  
☐ Step 5: VERIFY GOOGLE ACCOUNT
  Google may be asking for account verification:
  - Go to accounts.google.com
  - Complete any pending security verification
  - Try test login again

☐ Step 6: CHECK IP/LOCATION
  Google may block IP if:
  - First time accessing from this IP
  - IP is in different country than account
  - IP is from VPN/Proxy
  
  Solution:
  - Whitelist IP in Google Security settings
  - Or temporarily disable VPN

☐ Step 7: WAIT LONGER BETWEEN RETRIES
  Don't retry immediately after failure.
  Wait at least 5 minutes between attempts.
  Google rate-limits failed login attempts.

☐ Step 8: USE DIFFERENT ACCOUNT
  Try with a different Gmail account that:
  - Has never failed logins
  - Has 2FA disabled
  - Is accessed from stable IP
  - Has no suspicious activity
```

---

## 📋 Verify Browser Arguments Are Applied

### Code Location
File: `PWBaseTest.java` → `launchBrowser()` method (around line 287-305)

### What Should Be There ✅
```java
List<String> args = Arrays.asList(
    "--start-maximized",
    "--disable-dev-shm-usage",
    "--no-sandbox",
    "--disable-gpu",
    "--disable-blink-features=AutomationControlled",  // ← KEY LINE
    "--disable-extensions",
    "--disable-plugins",
    "--no-first-run",
    "--no-default-browser-check",
    "--disable-popup-blocking",
    "--disable-features=IsolateOrigins,site-per-process"
);

.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)...")  // ← SET USER AGENT
.setViewportSize(1920, 1080)  // ← NOT NULL
```

If you don't see these, apply the code changes from GOOGLE_SECURITY_WARNING_FIX.md

---

## 🧪 Testing Checklist

| Task | Command / Action | Expected Result |
|------|-----------------|-----------------|
| Clear cache | `deletePersistentContextData()` | Folder deleted ✅ |
| Show browser | Set `hideBrowser=N` | Browser visible |
| Set slow mode | Set `debugMode=Y` | 8s delays between actions |
| Record video | Set `recordVideo=Y` | Video saved in target/videos/ |
| Check email | `visionaivisionai2@gmail.com` | Email should be correct |
| Check log | Console output | Should show "Email entered ✅" |
| Final redirect | After password | URL should NOT contain "google" |

---

## 🔍 Console Log Indicators

### ✅ GOOD Logs (Login Will Succeed)
```
✅ Already logged in via persistent context. Skipping login.
🔵 Found login button, clicking Google login...
✅ Email entered
✅ Password field detected
✅ Password entered
✅ Successfully redirected from Google - login completed
Current URL: https://yourapplication.com/dashboard
```

### ❌ BAD Logs (Login Will Fail)
```
❌ GOOGLE SECURITY WARNING DETECTED!
❌ Login Error: ...
⚠️  Still on Google login page - may need additional verification
❌ Google 2FA/Verification Required - Cannot proceed
```

---

## 📞 Error Messages & Solutions

| Error Message | Cause | Solution |
|---------------|-------|----------|
| "This browser or app may not be secure" | Bot detection | Clear cache, run headed, increase waits |
| "Verify it's you" | 2FA enabled | Disable 2FA or use app password |
| "Try using a different browser" | Blocked browser type | Use Chrome channel ✅ (already set) |
| Password field not found | Page didn't load | Increase wait time from 4s to 6s |
| Still on accounts.google.com | Redirect failed | Wait longer after password entry |

---

## 🛠️ Code Changes Made

### File: PWBaseTest.java

**What Changed:**
1. Added 10+ stealth mode browser arguments
2. Set explicit user-agent string
3. Changed viewport from null to 1920x1080
4. Added security warning detection
5. Increased wait times (3s → 4-5s)
6. Added 2FA detection

**Result:** Browser appears more "real" to Google's detection

---

## 🚀 If Still Not Working

### Nuclear Option: Force Re-Login
```java
// Add this to your test @BeforeMethod:
PWBaseTest.deletePersistentContextData();
mapAllVariables.put("forceRelogin", "Y");
```

### Manual Testing
1. Run test with `hideBrowser=N` and `debugMode=Y`
2. Watch the browser as it logs in
3. If Google shows warning, Google itself is blocking
4. You may need to:
   - Use a different IP
   - Use a different account
   - Complete Google's verification manually

### Contact Google Support
If systematic blocking: https://support.google.com/accounts

---

## 📝 Log Your Troubleshooting

When reporting the issue, include:
1. Console output (full error message)
2. Screenshot/video of browser (if headed mode)
3. OAuth account email
4. IP address (if different from usual)
5. Last successful login date
6. Current time zone vs account time zone

---

## ✅ Fixed Issues

- [x] Browser stealth mode enabled
- [x] User-agent configured
- [x] Viewport size set
- [x] Wait times increased
- [x] Security warning detection added
- [x] Persistent context enabled
- [x] Documentation created

**Last Updated:** May 27, 2026
