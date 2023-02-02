package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

public class TestListener implements ITestListener{

	TestUtils utils = new TestUtils();
	
	public void onTestFailure(ITestResult result )
	{
		if(result.getThrowable() != null)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			utils.log(sw.toString());
		}
		
		BaseTest base = new BaseTest();
		File file = base.getDriver().getScreenshotAs(OutputType.FILE);
		
		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Map <String,String> parameters= new HashMap <String,String>();
		parameters = result.getTestContext().getCurrentXmlTest().getAllParameters();
		
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
		ExtentReport.getTest().fail("Test Failed",
				MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
		ExtentReport.getTest().fail("Test Failed",
				MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());
		ExtentReport.getTest().fail(result.getThrowable());
		
	}
		
		@Override
		public void onTestStart(ITestResult result) {
			utils.log().info("onTestStart ");
			BaseTest base = new BaseTest();
			ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
			.assignCategory(base.getPlatform() + "_" + base.getDeviceName())
			.assignAuthor("Priyam_Pratyush_Sahu");		
		}

		@Override
		public void onTestSuccess(ITestResult result) {
			utils.log().info("onTestSuccess ");
			ExtentReport.getTest().log(Status.PASS, "Test Passed");
			
		}

		@Override
		public void onTestSkipped(ITestResult result) {
			utils.log().info("onTestSkipped ");
			ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
			
		}

		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStart(ITestContext context) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFinish(ITestContext context) {
			ExtentReport.getReporter().flush();		
		}
		
}
