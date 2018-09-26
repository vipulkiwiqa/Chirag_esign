package com.automation.init;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.internal.Utils;

import com.automation.indexpage.PackageIndexpage;
import com.automation.indexpage.PackageIndexpage_1;
import com.automation.utility.Common;
import com.automation.utility.TestData;
import com.automation.verification.PackageVerification;
import com.automation.verification.PackageVerification_1;

public class SeleniumInit {

	String testUrl;
	String seleniumHub;
	String seleniumHubPort;
	public String targetBrowser;
	public String suiteName = "";
	public String testName = "";

	public WebDriver driver;

	protected static String test_data_folder_path = null;
	protected static String screenshot_folder_path = null;
	public static String currentTest;

	public static String browserName = "";
	public static String osName = "";
	public static String browserVersion = "";

	public static PackageIndexpage packageIndexpage;
	public static PackageVerification packageVerification;

	public static PackageIndexpage_1 packageIndexpage_1;
	public static PackageVerification_1 packageVerification_1;

	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {

		testUrl = TestData.getURL();

		seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");
		targetBrowser = testContext.getCurrentXmlTest().getParameter("selenium.browser");
		browserName = testContext.getCurrentXmlTest().getParameter("selenium.browser");
		System.out.println(" Before Test ");

	}

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext) throws Exception {
		System.out.println("Set up Start =====");
		String path = "";

		File theDir = new File(path);

		if (!theDir.exists()) {
			System.out.println("creating directory: ");
			boolean result = false;
				
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		URL remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";

		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		currentTest = method.getName(); // get Name of current test.

		DesiredCapabilities capability = null;
		if (targetBrowser == null || targetBrowser.contains("firefox")) {
			
			
			FirefoxProfile profile = new FirefoxProfile();
			   System.setProperty("webdriver.gecko.driver", "Resources/geckodriver_0_22_64BIT.exe");
			   System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"false");
			   System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"log.txt");
			   
			   LoggingPreferences pref = new LoggingPreferences();
			   pref.enable(LogType.BROWSER, Level.OFF);
			   pref.enable(LogType.CLIENT, Level.OFF);
			   pref.enable(LogType.DRIVER, Level.OFF);
			   pref.enable(LogType.PERFORMANCE, Level.OFF);
			   pref.enable(LogType.PROFILER, Level.OFF);
			   pref.enable(LogType.SERVER, Level.OFF);
			   
			   final FirefoxOptions options = new FirefoxOptions();
			  // options.addArguments("-profile", "./firefoxprofile"); // Added for CSRF Issue
			   
			   profile.setPreference("geo.enabled", false);
			   profile.setPreference("geo.provider.use_corelocation", false);
			   profile.setPreference("geo.prompt.testing", false);
			   profile.setPreference("geo.prompt.testing.allow", false);
			  
			      
			  // profile.setPreference("csrf_protection", false);
			    
			   capability = DesiredCapabilities.firefox();
			   capability.setCapability(FirefoxDriver.PROFILE, profile); 
			   capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
			   capability.setCapability("marionette", true);
			   capability.setCapability(FirefoxDriver.PROFILE, profile);
			   capability.setCapability(CapabilityType.LOGGING_PREFS, pref);
			   options.addArguments("no-sandbox");

			   /*options.addArguments("--start-maximized");*/
			   if (TestData.getHeadless().contains("Yes")) {

				System.out.println("Firefox headless.........");
			    File file = new File("Resources/geckodriver_0_22_64BIT.exe");

			    FirefoxBinary binary=new FirefoxBinary();
			    binary.addCommandLineOptions("--headless");
				System.out.println("Firefox headless.........");

			    System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());

			    options.setBinary(binary);
			    options.addArguments("--window-size=1920x1080");

			   }
			   
			   capability.setBrowserName("firefox");
			   capability.setJavascriptEnabled(true);
			   browserName = capability.getVersion();
			   osName = capability.getPlatform().name();
			   browserVersion = capability.getVersion();
			   // driver = new RemoteWebDriver(new
			   // URL("http://172.16.150.12:4444/wd/hub"), capability);
			  // driver = new ChromeDriver(capability);
			   
			   System.out.println("=========" + "firefox Driver " + "==========");
			   if (TestData.getHeadless().contains("Yes")) {
			    options.addArguments("headless");
			    options.addArguments("--window-size=1920x1080");
			   }
			   driver = new FirefoxDriver(capability);
			   
			   /*if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
			    path = "/Users/Jignesh/developer/test-automation";
			   } else {
			    path = "Resources/geckodriver.exe";
			   }*/

			   /*profile.setPreference("dom.max_chrome_script_run_time", "999");
			   profile.setPreference("dom.max_script_run_time", "999");
			   profile.setPreference("browser.download.folderList", 2);
			   profile.setPreference("browser.download.dir", path);
			   profile.setPreference("browser.helperApps.neverAsk.openFile",
			     "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			   profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
			     "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			   profile.setPreference("browser.download.manager.showWhenStarting", false);
			   profile.setPreference("browser.download,manager.focusWhenStarting", false);
			   // profile.setPreference("browser.download.useDownloadDir",true);
			   profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			   profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			   profile.setPreference("browser.download.manager.closeWhenDone", false);
			   profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			   profile.setPreference("browser.download.manager.useWindow", false);
			   profile.setPreference("browser.download.manager.showWhenStarting", false);

			   profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			   profile.setPreference("pdfjs.disabled", true);
			   profile.setAcceptUntrustedCertificates(true);
			   profile.setPreference("security.OCSP.enabled", 0);
			   profile.setPreference("network.http.use-cache", false);
			   profile.setPreference("gfx.direct2d.disabled", true);
			   profile.setPreference("layers.acceleration.disabled", true);*/
			   //capability = DesiredCapabilities.firefox();
			   /*capability.setJavascriptEnabled(true);
			   capability.setCapability(FirefoxDriver.PROFILE, profile);*/
			   //capability.setCapability("marionette", true);
			   /*browserName = capability.getBrowserName();
			   osName = System.getProperty("os.name");
			   browserVersion = capability.getVersion().toString();*/

			   /*System.out.println("=========" + "firefox Driver " + "==========");
			   driver = new FirefoxDriver();*/
			   // driver = new RemoteWebDriver(remote_grid, capability);
			   
			   /*if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
			    path = "/Users/Jignesh/developer/test-automation";
			   } else {
			    path = "Resources/geckodriver.exe";
			   }*/

			   /*profile.setPreference("dom.max_chrome_script_run_time", "999");
			   profile.setPreference("dom.max_script_run_time", "999");
			   profile.setPreference("browser.download.folderList", 2);
			   profile.setPreference("browser.download.dir", path);
			   profile.setPreference("browser.helperApps.neverAsk.openFile",
			     "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			   profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
			     "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			   profile.setPreference("browser.download.manager.showWhenStarting", false);
			   profile.setPreference("browser.download,manager.focusWhenStarting", false);
			   // profile.setPreference("browser.download.useDownloadDir",true);
			   profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			   profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			   profile.setPreference("browser.download.manager.closeWhenDone", false);
			   profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			   profile.setPreference("browser.download.manager.useWindow", false);
			   profile.setPreference("browser.download.manager.showWhenStarting", false);

			   profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			   profile.setPreference("pdfjs.disabled", true);
			   profile.setAcceptUntrustedCertificates(true);
			   profile.setPreference("security.OCSP.enabled", 0);
			   profile.setPreference("network.http.use-cache", false);
			   profile.setPreference("gfx.direct2d.disabled", true);
			   profile.setPreference("layers.acceleration.disabled", true);*/
			   //capability = DesiredCapabilities.firefox();
			   /*capability.setJavascriptEnabled(true);
			   capability.setCapability(FirefoxDriver.PROFILE, profile);*/
			   //capability.setCapability("marionette", true);
			   /*browserName = capability.getBrowserName();
			   osName = System.getProperty("os.name");
			   browserVersion = capability.getVersion().toString();*/

			   /*System.out.println("=========" + "firefox Driver " + "==========");
			   driver = new FirefoxDriver();*/
			   // driver = new RemoteWebDriver(remote_grid, capability);

			/*FirefoxProfile profile = new FirefoxProfile();
			System.setProperty("webdriver.gecko.driver", "Resources/geckodriver.exe");
			
			final FirefoxOptions options = new FirefoxOptions();
		//	options.addArguments("-profile", "./firefoxprofile"); // Added for CSRF Issue
			
			profile.setPreference("geo.enabled", false);
			profile.setPreference("geo.provider.use_corelocation", false);
			profile.setPreference("geo.prompt.testing", false);
			profile.setPreference("geo.prompt.testing.allow", false);
		//	profile.setPreference("csrf_protection", false);
			
			capability = DesiredCapabilities.firefox();
			capability.setCapability(FirefoxDriver.PROFILE, profile);	
			capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
			capability.setCapability("marionette", true);

			options.addArguments("no-sandbox");

			options.addArguments("--start-maximized");
			if (TestData.getHeadless().contains("Yes")) {
				options.addArguments("headless");
				options.addArguments("--window-size=1920x1080");
			}
			
			capability.setBrowserName("firefox");
			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			// driver = new RemoteWebDriver(new
			// URL("http://172.16.150.12:4444/wd/hub"), capability);
		//	driver = new ChromeDriver(capability);
			
			System.out.println("=========" + "firefox Driver " + "==========");
			driver = new FirefoxDriver(capability);*/
			
			/*if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
				path = "/Users/Jignesh/developer/test-automation";
			} else {
				path = "Resources/geckodriver.exe";
			}*/

			/*profile.setPreference("dom.max_chrome_script_run_time", "999");
			profile.setPreference("dom.max_script_run_time", "999");
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.dir", path);
			profile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download,manager.focusWhenStarting", false);
			// profile.setPreference("browser.download.useDownloadDir",true);
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			profile.setPreference("browser.download.manager.closeWhenDone", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			profile.setPreference("browser.download.manager.useWindow", false);
			profile.setPreference("browser.download.manager.showWhenStarting", false);

			profile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
			profile.setPreference("pdfjs.disabled", true);
			profile.setAcceptUntrustedCertificates(true);
			profile.setPreference("security.OCSP.enabled", 0);
			profile.setPreference("network.http.use-cache", false);
			profile.setPreference("gfx.direct2d.disabled", true);
			profile.setPreference("layers.acceleration.disabled", true);*/
			//capability = DesiredCapabilities.firefox();
			/*capability.setJavascriptEnabled(true);
			capability.setCapability(FirefoxDriver.PROFILE, profile);*/
			//capability.setCapability("marionette", true);
			/*browserName = capability.getBrowserName();
			osName = System.getProperty("os.name");
			browserVersion = capability.getVersion().toString();*/

			/*System.out.println("=========" + "firefox Driver " + "==========");
			driver = new FirefoxDriver();*/
			// driver = new RemoteWebDriver(remote_grid, capability);
			   
		} else if (targetBrowser.contains("chrome")) {
			File file = new File("Resources/chromedriver_2_41.exe");

			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

			final ChromeOptions options = new ChromeOptions();
			String downloadFilepath = new File("Downloads").getAbsolutePath();
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilepath);
			System.err.println("File Path ==================" + file.getAbsolutePath());
			options.setExperimentalOption("prefs", chromePrefs);
			capability = DesiredCapabilities.chrome();
			capability.setCapability(ChromeOptions.CAPABILITY, options);
					
			options.addArguments("no-sandbox");

			options.addArguments("--start-maximized");
			if (TestData.getHeadless().contains("Yes")) {
				options.addArguments("headless");
				options.addArguments("--window-size=1920x1080");
			}
			capability.setBrowserName("chrome");
			capability.setJavascriptEnabled(true);
			browserName = capability.getVersion();
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			// driver = new RemoteWebDriver(new
			// URL("http://172.16.150.12:4444/wd/hub"), capability);
			driver = new ChromeDriver(capability);
			/* driver.manage().window().setSize(new Dimension(1920,1080)); */
			System.out.println("=========" + "Chorme Driver " + "==========");
			// driver.setFileDetector(new LocalFileDetector());
		}
		
		else if (targetBrowser.contains("ie11")) {
			
			System.out.println("=========" + " IE11 Driver " + "==========");
			System.setProperty("webdriver.ie.driver", "Resources/IEDriverServer.exe");
			capability = DesiredCapabilities.internetExplorer();
			capability.setCapability("requireWindowFocus", true);
			capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capability.setCapability("ignoreZoomSetting", true);
			
		//	capability.setCapability("platform", "WIN10"); 
			capability.setCapability("version", 11); 
		//	capability.setCapability("browserName", "internet explorer");
			capability.setCapability("ignoreProtectedModeSettings",true);
			capability.setCapability("nativeEvents",false);
			capability.setCapability("ignoreZoomSetting", true);
			capability.setCapability("requireWindowFocus",true);
			
			InternetExplorerOptions IEoptions = new InternetExplorerOptions(capability);
			//driver = new InternetExplorerDriver();
			driver = new InternetExplorerDriver(capability);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		else if (targetBrowser.contains("m-headless")) {
			   
			FirefoxProfile profile = new FirefoxProfile();
			   File file = new File("Resources/geckodriver_0_22_64BIT.exe");

			   FirefoxBinary binary=new FirefoxBinary();
			   binary.addCommandLineOptions("--headless");
			   
			   System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());

			    FirefoxOptions options=new FirefoxOptions();
			    options.setBinary(binary);
			    options.addArguments("--window-size=1920x1080");
			   
			    
			    profile.setPreference("geo.enabled", false);
				profile.setPreference("geo.provider.use_corelocation", false);
				profile.setPreference("geo.prompt.testing", false);
				profile.setPreference("geo.prompt.testing.allow", false);
				
				capability = DesiredCapabilities.firefox();
			//	capability.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
				capability.setCapability(FirefoxDriver.PROFILE, profile);
				
			   driver=new FirefoxDriver(options);
			   
			  
			   System.out.println("=========" + "Firefox Headless Driver " + "==========");
			   // driver.setFileDetector(new LocalFileDetector());
			  }
	
		else if (targetBrowser.contains("Android_Chrome")) {
			
			DesiredCapabilities cap= new DesiredCapabilities();
			URL remote_grid1 = null ;
			try {
			remote_grid1 = new URL("http://" + "localhost" + ":" + "4723" + "/wd/hub");
			} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			cap=new DesiredCapabilities();
			//File apkFile = new File("app/Notepad.apk");
			//File apkFile = new File("app/Facebook.apk");
		//	File apkFile = new File("app/redBus Online Bus Ticket Booking Hotel Booking_v6.6.5_apkpure.com.apk");

		//	cap.setCapability("app",apkFile.getAbsolutePath());
			cap.setCapability("platformVersion", "6.0");
			cap.setCapability("platformName", "Android");
			cap.setCapability("deviceName", "TA09407DYT");
			cap.setCapability("browserName", "Chrome");
			/*cap.setCapability("appPackage", "com.app.workpulse.task");
			cap.setCapability("appActivity", "com.workpulse.task.SplashActivity");*/
			cap.setCapability("autoGrantPermissions", true);
			cap.setCapability("noReset", false);//change by vipul
			cap.setCapability("newCommandTimeout", 600);
			cap.setCapability("unicodeKeyboard", true);
			cap.setCapability("resetKeyboard", true);

			//androidDriver = Factory.createAndroidDriver(remote_grid, cap);
		//	driver = new AndroidDriver<MobileElement>(remote_grid1, cap);
			driver = new RemoteWebDriver(remote_grid1, cap);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
		}	
		
		suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//driver.manage().window().maximize(); //--> Firefox
		testUrl = TestData.getURL();
		driver.get(testUrl);
		packageIndexpage = new PackageIndexpage(driver);
		packageVerification = new PackageVerification(driver);
	}

	/**
	 * Log For Failure Test Exception.
	 * 
	 * @param tests
	 */
	private void getShortException(IResultMap tests) {

		for (ITestResult result : tests.getAllResults()) {

			Throwable exception = result.getThrowable();
			List<String> msgs = Reporter.getOutput(result);
			boolean hasReporterOutput = msgs.size() > 0;
			boolean hasThrowable = exception != null;
			if (hasThrowable) {
				boolean wantsMinimalOutput = result.getStatus() == ITestResult.SUCCESS;
				if (hasReporterOutput) {
					log("<h3>" + (wantsMinimalOutput ? "Expected Exception" : "Failure Reason:") + "</h3>");
				}

				// Getting first line of the stack trace
				String str = Utils.stackTrace(exception, true)[0];
				Scanner scanner = new Scanner(str);
				String firstLine = scanner.nextLine();
				log(firstLine);
				scanner.close();
			}
		}
	}

	/**
	 * After Method
	 * 
	 * @param testResult
	 */

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult) {

		ITestContext ex = testResult.getTestContext();

		try {
			testName = testResult.getName();
			System.err.println("----------->         " + testResult.getTestName());
			if (!testResult.isSuccess()) {

				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("======================++++   >>  ");
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);

				/* Make a screenshot for test that failed */
				String screenshotName = Common.getCurrentTimeStampString() + testName;
				Reporter.log("<br> <b>Please look to the screenshot - </b>");
				Common.makeScreenshot(driver, screenshotName);
				// Reporter.log(testResult.getThrowable().getMessage());

				getShortException(ex.getFailedTests());
				Common.logStatus("Fail");
				driver.close();
				driver.quit();

			} else {
				try {
					Common.pause(5);
					Common.pause(5);
				} catch (Exception e) {
					log("<br></br> Not able to perform logout");
				}

				System.out.println("TEST PASSED - " + testName + "\n");

				System.out.println("here is test status--------------------" + testResult.getStatus());

				driver.close();
				driver.quit();
			}

		} catch (Throwable throwable) {
			System.out.println("message from tear down" + throwable.getMessage());
		}
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) {
		System.out.println(msg);
		Reporter.log("<br></br>" + msg);
	}

}
