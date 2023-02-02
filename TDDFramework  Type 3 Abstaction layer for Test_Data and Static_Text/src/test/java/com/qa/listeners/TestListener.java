package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.qa.BaseTest;

public class TestListener implements ITestListener{

	
	public void onTestFailure(ITestResult result )
	{
		if(result.getThrowable() != null)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			System.out.println(sw.toString());
		}
		
		BaseTest base = new BaseTest();
		File file = base.getDriver().getScreenshotAs(OutputType.FILE);
		
		Map <String,String> parameters= new HashMap <String,String>();
		parameters = result.getTestContext().getCurrentXmlTest().getAllParameters();
		
//		String imagePath = "Screenshots" + File.separator + parameters.get("platformName") + "_" + parameters.get("platformVersion")
//		+ "_" + parameters.get("deviceName") + File.separator + base.getDateTime() 
//		+ File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
		
		String imagePath = "Screenshots" + File.separator + parameters.get("platformName") 
		+ "_" + parameters.get("deviceName") + File.separator + base.getDateTime() + File.separator 
		+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";
		System.out.println(imagePath);
		
		//For attaching test fail screenshot to the report
		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

		
		try {
			System.out.println("**************************** Screenshot Taken ***********************************");
			FileUtils.copyFile(file, new File(imagePath));
			Reporter.log("This is a sample screenshot");
			
			Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		
	}
}