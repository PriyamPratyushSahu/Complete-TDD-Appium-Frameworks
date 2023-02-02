//For testing using try catch and listeners

package com.qa.tests;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;

public class LoginTestsType2 extends BaseTest{
	
	TestUtils utils = new TestUtils();
	
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
		  utils.log().info("\n" + "******* STARTING TEST: "+ m.getName() + " *******\n"); //getName() gives the method name currently executing
	  }

	  @AfterMethod
	  public void afterMethod() {
	  }
	  
	  @Test
	  public void invalidUserName() {
			
		  /*
			 * Exceptional Handling Type - 1 (Using Try Catch Block)
			  
			  try { utils.log().info("Test No: 1\nInvalid Username");
			  
			  loginpage.enterUserName("invalidusername");
			  loginpage.enterPassword("secret_sauce"); loginpage.pressLoginBtn();
			  
			  String actualerrormsg = loginpage.getErrorTxt(); String expectederrormsg =
			  "Username and password do not match any user in this service.";
			  utils.log().info("Actual text error: " + actualerrormsg);
			  utils.log().info("Expected text error: " + expectederrormsg);
			  assertEquals(actualerrormsg,expectederrormsg ); } catch (Exception e) {
			  
			  StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);
			  e.printStackTrace(pw); utils.log().info(sw.toString()); //Printing Stack
			  Assert.fail(sw.toString()); //Sending to TestNG }
			 */
		  
		  
		  
			// Exceptional Handling Type - 2 (Using Test Listeners)
		  loginpage.enterUserName("invalidusername");
		  loginpage.enterPassword("secret_sauce"); 
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt(); 
		  String expectederrormsg ="Username and password do not match any user in this service.";
		  utils.log().info("Actual text error: " + actualerrormsg);
		  utils.log().info("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg ); 	  	  
		  
	  }
	  
	  @Test
	  public void invalidPassword() {
		  utils.log().info("Test No: 2\nInvalid Password");
		  

		  loginpage.enterUserName("standard_user");
		  loginpage.enterPassword("invalidpassword");
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt();
		  String expectederrormsg = "Username and password do not match any user in this service.";
		  utils.log().info("Actual text error: " + actualerrormsg);
		  utils.log().info("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg );
		    
	  }
	  
	  @Test
	  public void successfulLogin() {

		  utils.log().info("Test No: 3\nSuccessful Login");
		  

		  loginpage.enterUserName("standard_user");
		  loginpage.enterPassword("secret_sauce");
		  productpage = loginpage.pressLoginBtn();
		  
		  String actualproducttitle = productpage.getTitle();
		  String expectedproducttitle = "PRODUCTS";
		  utils.log().info("Actual title: " + actualproducttitle);
		  utils.log().info("Expected title: " + expectedproducttitle);
		  
		 //assertEquals(actualproducttitle,expectedproducttitle );
		  utils.log().info("String Match: " + actualproducttitle.equals(expectedproducttitle));
		  
		  
	  }
}
