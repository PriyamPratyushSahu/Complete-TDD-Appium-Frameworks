package com.qa;

import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BaseTest {
	
	//Driver of the super class
	protected static AppiumDriver driver;
	
	//Object of Properties class to use the config.properties
	protected static Properties props;
	//To load the config.properries file
	InputStream inputStream;
	
	public BaseTest()
	{
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	//parameter annotation provided by TestNG to read the parameters from testing.xml
	@Parameters({ "platformName", "platformVersion", "deviceName"})
  
  @BeforeTest
  public void beforeTest(String platformName, String platformVersion, String deviceName) throws Exception {
	  try {
		  
		  props = new Properties();
		  String propFileName = "config.properties"; //Complete file path is not required since the file is in classpath [src/main/resources]
		  
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  
		  //To load the properties file
		  props.load(inputStream);
			
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("platformName",platformName);
			caps.setCapability("platformVersion",platformVersion);
			caps.setCapability("deviceName",deviceName);	
			caps.setCapability("automationName","uiAutomator2");
			caps.setCapability("automationName",props.getProperty("androidAutomationName"));		
			caps.setCapability("appPackage",props.getProperty("androidAppPackage"));
			caps.setCapability("appActivity",props.getProperty("androidAppActivity"));
			
			URL appUrl = getClass().getClassLoader().getResource(props.getProperty("androidAppLocation"));
			/*
			 * getResource(): It will fetch the complete path of the test resources package and append the relative path present in the config.properties
			 */
			caps.setCapability("app",appUrl);
					
			
			URL Url = new URL(props.getProperty("appiumURL"));
			
			driver = new AndroidDriver(Url,caps);
			String sessionID = driver.getSessionId().toString();
			System.out.println("Successful");
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	  
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

  @AfterTest
  public void afterTest() {
	  driver.quit();
  }

}
