
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
import com.qa.utils.TestUtils;

public class LoginTestsType4 extends BaseTest{
	
	LoginPage loginpage;
	ProductsPage productspage;
	JSONObject loginUsers;
	TestUtils utils = new TestUtils();
	
	
	@BeforeClass
	  public void beforeClass() throws IOException {
		
		InputStream datais = null;
		
		try {
			String dataFileName = "data\\loginUsers.json";
			datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
			JSONTokener tokener = new JSONTokener(datais);
			loginUsers = new JSONObject(tokener);
		} catch (Exception e) {
			e.printStackTrace();
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
		  closeApp();
			launchApp();
			utils.log().info("Log-in");
		  loginpage = new LoginPage();
		  utils.log().info("\n" + "******* STARTING TEST: "+ m.getName() + " -*******\n"); //getName() gives the method name currently executing
		  
		  
	  }

	  
	  
	  @Test
	  public void invalidUserName() {
			
		  // Exceptional Handling Type - 2 (Using Test Listeners)
		  loginpage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password")); 
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt(); 
		  String expectederrormsg = getStrings().get("err_invalid_username_or_password");
		  utils.log().info("Actual text error: " + actualerrormsg);
		  utils.log().info("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg ); 	  	  
		  
	  }
	  
	  @Test
	  public void invalidPassword() {
		  utils.log().info("Test No: 2\nInvalid Password");
		  

		  loginpage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
		  loginpage.pressLoginBtn();
		  
		  String actualerrormsg = loginpage.getErrorTxt();
		  String expectederrormsg = getStrings().get("err_invalid_username_or_password");
		  utils.log().info("Actual text error: " + actualerrormsg);
		  utils.log().info("Expected text error: " + expectederrormsg);
		  assertEquals(actualerrormsg,expectederrormsg );
		    
	  }
	  
	  @Test
	  public void successfulLogin() {

		  utils.log().info("Test No: 3\nSuccessful Login");
		  

		  loginpage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		  loginpage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		  productspage = loginpage.pressLoginBtn();
		  
		  String actualproducttitle = productspage.getTitle();
		  String expectedproducttitle = getStrings().get("product_title");
		  utils.log().info("Actual title: " + actualproducttitle);
		  utils.log().info("Expected title: " + expectedproducttitle);
		  
		 //assertEquals(actualproducttitle,expectedproducttitle );
		  utils.log().info("String Match: " + actualproducttitle.equals(expectedproducttitle));
		  
		  
	  }
	  @AfterMethod
	  public void afterMethod() {
	  }
}
