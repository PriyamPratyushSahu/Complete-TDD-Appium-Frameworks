package com.qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.screenrecording.CanRecordScreen;
public class BaseTest {
	
	//Driver of the super class
	protected static AppiumDriver driver;
	
	//Object of Properties class to use the config.properties
	protected static Properties props;

	//To load the config.properties file
	InputStream inputStream;
	
	//For specific platform name(Andriod,iOS)
	protected static String platform;
	
	//To fetch date 
	protected static String dateTime;
	
	//For strings.xml
	protected static HashMap<String,String> strings = new HashMap<String,String>();
	InputStream stringsis;
	TestUtils utils;
	
	public BaseTest()
	{
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@BeforeMethod
	public void beforeMethod() {
		
		//To record the screen
		((CanRecordScreen) driver).startRecordingScreen();
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

	
	//parameter annotation provided by TestNG to read the parameters from testing.xml
	@Parameters({ "emulator","platformName", "platformVersion", "udid", "deviceName"})
  
  @BeforeTest
  public void beforeTest(String emulator, String platformName, String platformVersion, String udid, String deviceName) throws Exception {
		utils = new TestUtils();
		dateTime = utils.dateTime();
		URL Url;
		platform = platformName;
		
	  try {
		  
		  props = new Properties();
		  String propFileName = "config.properties"; //Complete file path is not required since the file is in classpath [src/main/resources]
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  //To load the properties file
		  props.load(inputStream);
		  
			//For strings.xml
		   String xmlFileName = "strings\\strings.xml";
		   stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		   
		   strings = utils.parseStringXML(stringsis);
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName",platformName);
			caps.setCapability("platformVersion",platformVersion);
			caps.setCapability("deviceName",deviceName);
			switch(platformName)
			{
			case "Android":
				
				System.out.println("\n\n#*#* Testing for Android #*#*");
				caps.setCapability("automationName","uiAutomator2");
				caps.setCapability("automationName",props.getProperty("androidAutomationName"));		
				caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
				caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
				if(emulator.equalsIgnoreCase("true"))
				{
					caps.setCapability("avd",deviceName); //To start the avd if not started
				}
				else
					caps.setCapability("udid",udid);
				/*
				 * getResource(): It will fetch the complete path of the test resources package and append the relative path present in the config.properties
				 */
				
				//*************** APK FILE PATH **********************
				
				//Type 1: Using Url
				//URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
				//System.out.println("URL: "+ appUrl);
				//caps.setCapability("app",appUrl);
				
				//Else Type 2: String using System.getProperty
				
				String appUrl1 = System.getProperty("user.dir")+File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.7.1.apk";
				System.out.println("String: "+ appUrl1);
				caps.setCapability("app",appUrl1);
				
				Url = new URL(props.getProperty("appiumURL"));
				
				driver = new AndroidDriver(Url,caps);
				String sessionID = driver.getSessionId().toString();
				System.out.println("Successful");
				
				break;
			}
		
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
	public AppiumDriver getDriver()
	{
		return driver;
	}
	
	public String getDateTime()
	{
		return dateTime;
	}
	
	
	public void waitForVisibility(WebElement e)
	{
		//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10)); //Duration is a class from java.time
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(TestUtils.WAIT));
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
		switch(platform)
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
				  ((InteractsWithApps) driver).terminateApp(props.getProperty("androidAppPackage"));
				  break;
			  case "iOS":
				  ((InteractsWithApps) driver).terminateApp(props.getProperty("iOSBundleId"));
		  }
	  }
	
	  public void launchApp(String platformName) {
		  System.out.println("Launch App Method Invoked---------------------------------------------------------------");
		  switch(platformName){
			  case "Android":
				  ((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
				  break;
			  case "iOS":
				  ((InteractsWithApps) driver).activateApp(props.getProperty("iOSBundleId"));
		  }
	  }
	  
	  public WebElement scrollToElement() {
		  
		  return driver.findElement(AppiumBy.androidUIAutomator(
				  "new UiScrollable(new UiSelector()" + ".description(\"test-Inventory item page\")).scrollIntoView("
						  + "new UiSelector().description(\"test-Price\"));"));
		  
		  
//		  return driver.findElement(AppiumBy.androidUIAutomator(
//				  "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
//						  + "new UiSelector().description(\"test-Price\"));"));
	  }
	  

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
