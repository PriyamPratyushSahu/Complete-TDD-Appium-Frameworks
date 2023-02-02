
//For fetching username and password from JSON file

package com.qa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;

public class LoginTestsType3 extends BaseTest{
	
	LoginPage loginpage;
	ProductsPage productpage;
	InputStream datais;
	JSONObject loginUsers;
	
	@BeforeClass
	  public void beforeClass() throws IOException {
		
		try {
			String dataFileName = "data\\loginUsers.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace(); //catching the exception
			throw e; //throwing an exception, the reason is the testNG will know the exception 
			//else it will consider it as successful and continue with test cases
		}
		finally
		{
			if(datais != null)
				datais.close();
		}
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
			
		  // Exceptional Handling Type - 2 (Using Test Listeners)
		  loginpage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password")); 
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
		  

		  loginpage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
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
		  

		  loginpage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		  productpage = loginpage.pressLoginBtn();
		  
		  String actualproducttitle = productpage.getTitle();
		  String expectedproducttitle = "PRODUCTS";
		  System.out.println("Actual title: " + actualproducttitle);
		  System.out.println("Expected title: " + expectedproducttitle);
		  
		 //assertEquals(actualproducttitle,expectedproducttitle );
		  System.out.println("String Match: " + actualproducttitle.equals(expectedproducttitle));
		  
		  
	  }
}
