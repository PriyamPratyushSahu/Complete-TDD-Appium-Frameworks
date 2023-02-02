package com.qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
public class BaseTest {	
	//************************ All local parameters are converted into local objects
	
	//Driver of the super class
	protected static ThreadLocal <AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
	//Object of Properties class to use the config.properties
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();

	//For specific platform name(Android,iOS)
	protected static ThreadLocal <String> platform = new ThreadLocal<String>();
	//To fetch date 
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();
	//For strings.xml
	protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	//To handle log files of respective device
	protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
	private static AppiumDriverLocalService server;
	TestUtils utils = new TestUtils();
	
	//Type 1
	//For log4j
		//static Logger log =  LogManager.getLogger(BaseTest.class.getName());
	
	//Type 2 in TestUtils.java
	
	//********************* Getter and Setters for Thread local objects
	
	
	public AppiumDriver getDriver()
	{
		return driver.get();
		/*
		 * get() Method helps in - 
		 * Returns the value in the current thread's copy of this thread-local variable.
		 *  If the variable has no value for the current thread, it is first initialized to the value returned 
		 *  by an invocation of the initialValue method.
		 */
	}
	
	public void setDriver(AppiumDriver driver2)
	{
		driver.set(driver2);
		/*
		 * set() Method helps in -
		 * Sets the current thread's copy of this thread-local variable to the specified value.
		 *  Most subclasses will have no need to override this method, relying solely on the initialValuemethod
		 *  to set the values of thread-locals.
		 */
	}
	
	public Properties getProps() {
		  return props.get();
	  }
	  
	  public void setProps(Properties props2) {
		  props.set(props2);
	  }
	  
	  public HashMap<String, String> getStrings() {
		  return strings.get();
	  }
	  
	  public void setStrings(HashMap<String, String> strings2) {
		  strings.set(strings2);
	  }
	  
	  public String getPlatform() {
		  return platform.get();
	  }
	  
	  public void setPlatform(String platform2) {
		  platform.set(platform2);
	  }
	  
	  public String getDateTime() {
		  return dateTime.get();
	  }
	  
	  public void setDateTime(String dateTime2) {
		  dateTime.set(dateTime2);
	  }
	  
	  public String getDeviceName() {
		  return deviceName.get();
	  }
	  
	  public void setDeviceName(String deviceName2) {
		  deviceName.set(deviceName2);
	  }
	
	public BaseTest()
	{
		PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		
		//To record the screen
		((CanRecordScreen) getDriver()).startRecordingScreen();
	}
	
	//stop video capturing and create *.mp4 file
		@AfterMethod
		public synchronized void afterMethod(ITestResult result) throws Exception {
			String media = ((CanRecordScreen) getDriver()).stopRecordingScreen();
			
			//Screen Recording will take place for every condition(Even if test case pass or fail)
//			Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();		
//			String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
//			+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
//			
//			File videoDir = new File(dirPath);
//			
//			//synchronized(videoDir){
//				if(!videoDir.exists()) {
//					videoDir.mkdirs();
//				}	
//			//}
//			FileOutputStream stream = null;
//			try {
//				stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
//				stream.write(Base64.decodeBase64(media));
//				//stream.close();
//				//utils.log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
//			} catch (Exception e) {
//				//utils.log().error("error during video capture" + e.toString());
//			} finally {
//				if(stream != null) {
//					stream.close();
//				}
//			}
			
			//Only record screen when test case fails
			
			if(result.getStatus() == 2) //when test case failed 2 is returned
			{
				Map <String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();		
				String dirPath = "videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") 
				+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName();
				
				File videoDir = new File(dirPath);
				
				//synchronized(videoDir){
					if(!videoDir.exists()) {
						videoDir.mkdirs();
					}	
				//}
				FileOutputStream stream = null;
				try {
					stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
					stream.write(Base64.decodeBase64(media));
					//stream.close();
					//utils.log().info("video path: " + videoDir + File.separator + result.getName() + ".mp4");
				} catch (Exception e) {
					//utils.log().error("error during video capture" + e.toString());
				} finally {
					if(stream != null) {
						stream.close();
					}
				}
			}
		}

		@BeforeSuite
		public void beforeSuite() throws Exception, Exception {
			ThreadContext.put("ROUTINGKEY", "ServerLogs");
			server = getAppiumServerDefault(); 
			if(!checkIfAppiumServerIsRunnning(4723)) {
				server.start();
				//server.clearOutPutStreams(); // -> Comment this if you want to see server logs in the console
				utils.log().info("Appium server started");
			} else {
				utils.log().info("Appium server already running");
			}	
		}
		
		public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
		    boolean isAppiumServerRunning = false;
		    ServerSocket socket;
		    try {
		        socket = new ServerSocket(port);
		        socket.close();
		    } catch (IOException e) {
		    	System.out.println("1");
		        isAppiumServerRunning = true;
		    } finally {
		        socket = null;
		    }
		    return isAppiumServerRunning;
		}
		
		@AfterSuite (alwaysRun = true)
		public void afterSuite() {
			  if(server.isRunning()){
				  server.stop();
				  utils.log().info("Appium server stopped");
			  }
		}

		// for Windows
		public AppiumDriverLocalService getAppiumServerDefault() {
			return AppiumDriverLocalService.buildDefaultService();
		}

		
	
	//parameter annotation provided by TestNG to read the parameters from testing.xml
		  @Parameters({"emulator", "platformName", "udid", "deviceName", "systemPort", 
			  "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
  
  @BeforeTest
  public void beforeTest(@Optional("androidOnly")String emulator, String platformName, String udid, String deviceName, 
		  @Optional("androidOnly")String systemPort, @Optional("androidOnly")String chromeDriverPort, 
		  @Optional("iOSOnly")String wdaLocalPort, @Optional("iOSOnly")String webkitDebugProxyPort) throws Exception
		  {
		  
		
		setDateTime(utils.dateTime());
		URL Url;
		setPlatform(platformName);
		setDeviceName(deviceName);
		
		//****************** Class level variables like inputStream & stringsis must be used as local variables not global
		//To load the config.properties file
		InputStream inputStream = null;
		InputStream stringsis = null;
		Properties props = new Properties();
		AppiumDriver driver = null;
		
		String strFile = "logs" + File.separator + platformName + "_" + deviceName;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		//route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("log path: " + strFile);
		
	  try {
		  
		  props = new Properties();
		  String propFileName = "config.properties"; //Complete file path is not required since the file is in classpath [src/main/resources]
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  //To load the properties file
		  props.load(inputStream);
		  setProps(props);
		  
			//For strings.xml
		   String xmlFileName = "strings\\strings.xml";
		   stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		   
		   setStrings(utils.parseStringXML(stringsis));
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName",platformName);
			caps.setCapability("deviceName",deviceName);
			switch(platformName)
			{
			case "Android":
				
				
				
				System.out.println("\n\n#*#* Testing for Android #*#*");
				caps.setCapability("automationName","uiAutomator2");
				caps.setCapability("automationName",props.getProperty("androidAutomationName"));		
				caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
				caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
				caps.setCapability("udid", udid);
				caps.setCapability("systemPort", systemPort);
				caps.setCapability("chromeDriverPort", chromeDriverPort);
				if(emulator.equalsIgnoreCase("true")) {
					caps.setCapability("avd", deviceName);
					caps.setCapability("avdLaunchTimeout", 120000);
				}
				/*
				 * getResource(): It will fetch the complete path of the test resources package and append the relative path present in the config.properties
				 */
				
				//*************** APK FILE PATH **********************
				
				//Type 1: Using Url
				//URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
				//System.out.println("URL: "+ appUrl);
				//caps.setCapability("app",appUrl);
				
				//Else Type 2: String using System.getProperty
				
				String appUrl1 = System.getProperty("user.dir")+File.separator + "src" + File.separator + "test" +
				File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
				utils.log().info("App Url: "+ appUrl1);
				caps.setCapability("app",appUrl1);
				
				Url = new URL(props.getProperty("appiumURL"));//+ "4723/wd/hub");
				
				driver = new AndroidDriver(Url,caps);
				String sessionID = driver.getSessionId().toString();
				System.out.println("Successful");
				
				break;
			}
		setDriver(driver);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	  finally
	  {
		  if(inputStream != null)
		  {
			  inputStream.close();
		  }
		  
		  if(stringsis != null)
		  {
			  stringsis.close();
		  }
		  
	  }
	  
  }
	
	public void waitForVisibility(WebElement e)
	{
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)); //Duration is a class from java.time
		WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(TestUtils.WAIT));
		wait.until(ExpectedConditions.visibilityOf(e));
		System.out.println("Wait for Visibility invoked");
	}
	
	public void click(WebElement e)
	{
		waitForVisibility(e);
		e.click(); //automation will first check the visibility of the element and if it is visible then only it will click on it
		System.out.println("Clicked");
	}
	
	public void sendKeys(WebElement e,String txt)
	{
		waitForVisibility(e);
		e.sendKeys(txt);
		System.out.println("Send keys");
	}
	
	public String getAttribute(WebElement e, String attribute)
	{
		waitForVisibility(e);
		System.out.println("Attribute");
		return e.getAttribute(attribute);
		
	}
	
	public String getText(WebElement e)
	{
		switch(getPlatform())
		{
		case "Android":
			return getAttribute(e, "text");
		case "iOS":
			return getAttribute(e, "label"); //iOS does not consider text
		}
		return null;
	}
	
	public void clear(WebElement e)
	{
		waitForVisibility(e);
		e.clear();
	}
	
	
	public void closeApp(String platformName) {
		System.out.println("Close App Method Invoked");
		  switch(platformName){
			  case "Android":
				  ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("androidAppPackage"));
				  break;
			  case "iOS":
				  ((InteractsWithApps) getDriver()).terminateApp(getProps().getProperty("iOSBundleId"));
		  }
	  }
	
	  public void launchApp(String platformName) {
		  System.out.println("Launch App Method Invoked---------------------------------------------------------------");
		  switch(getPlatform()){
			  case "Android":
				  ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("androidAppPackage"));
				  break;
			  case "iOS":
				  ((InteractsWithApps) getDriver()).activateApp(getProps().getProperty("iOSBundleId"));
		  }
	  }
	  
	  public WebElement scrollToElement() {
		  
		  return getDriver().findElement(AppiumBy.androidUIAutomator(
				  "new UiScrollable(new UiSelector()" + ".description(\"test-Inventory item page\")).scrollIntoView("
						  + "new UiSelector().description(\"test-Price\"));"));
		  
		  
//		  return driver.findElement(AppiumBy.androidUIAutomator(
//				  "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
//						  + "new UiSelector().description(\"test-Price\"));"));
	  }
	  

  @AfterTest
  public void afterTest() {
	  getDriver().quit();
  }

}
