//For testing using try catch and listeners

package com.qa.tests;

import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoginTestsType2 extends BaseTest{
	
	LoginPage loginpage;
	ProductsPage productpage;
	
	@BeforeClass
	  public void beforeClass() {
	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  loginpage = new LoginPage();
		  System.out.println("\n" + "******* STARTING TEST: "+ m.getName() + " *******\n"); //getName() gives the method name currently executing
	  }

	  @AfterMethod
	  public void afterMethod() {
	  }
	  
	  @Test
	  public void invalidUserName() {
			
		  /*
			 * Exceptional Handling Type - 1 (Using Try Catch Block)
			  
			  try { System.out.println("Test No: 1\nInvalid Username");
			  
			  loginpage.enterUserName("invalidusername");
			  loginpage.enterPassword("secret_sauce"); loginpage.pressLoginBtn();
			  
			  String actualerrormsg = loginpage.getErrorTxt(); String expectederrormsg =
			  "Username and password do not match any user in this service.";
			  System.out.println("Actual text error: " + actualerrormsg);
			  System.out.println("Expected text error: " + expectederrormsg);
			  assertEquals(actualerrormsg,expectederrormsg ); } catch (Exception e) {
			  
			  StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);
			  e.printStackTrace(pw); System.out.println(sw.toString()); //Printing Stack
			  Assert.fail(sw.toString()); //Sending to TestNG }
			 */
		  
		  
		  
			// Exceptional Handling Type - 2 (Using Test Listeners)
		  loginpage.enterUserName("invalidusername");
		  loginpage.enterPassword("secret_sauce"); 
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt(); 
		  String expectederrormsg ="Username and password do not match any user in this service.";
		  System.out.println("Actual text error: " + actualerrormsg);
		  System.out.println("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg ); 	  	  
		  
	  }
	  
	  @Test
	  public void invalidPassword() {
		  System.out.println("Test No: 2\nInvalid Password");
		  

		  loginpage.enterUserName("standard_user");
		  loginpage.enterPassword("invalidpassword");
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt();
		  String expectederrormsg = "Username and password do not match any user in this service.";
		  System.out.println("Actual text error: " + actualerrormsg);
		  System.out.println("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg );
		    
	  }
	  
	  @Test
	  public void successfulLogin() {

		  System.out.println("Test No: 3\nSuccessful Login");
		  

		  loginpage.enterUserName("standard_user");
		  loginpage.enterPassword("secret_sauce");
		  productpage = loginpage.pressLoginBtn();
		  
		  String actualproducttitle = productpage.getTitle();
		  String expectedproducttitle = "PRODUCTS";
		  System.out.println("Actual title: " + actualproducttitle);
		  System.out.println("Expected title: " + expectedproducttitle);
		  
		 //assertEquals(actualproducttitle,expectedproducttitle );
		  System.out.println("String Match: " + actualproducttitle.equals(expectedproducttitle));
		  
		  
	  }
}
