//For fusing scroll to

package com.qa.tests;

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
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.TestUtils;

public class ProductTests2 extends BaseTest{
	
	
	
	LoginPage loginpage;
	ProductsPage productspage;
	SettingsPage settingspage;
	ProductsDetailsPage productsDetailsPage;
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
		  utils.log().info("\n" + "******* STARTING TEST: "+ m.getName() + "-*******\n"); //getName() gives the method name currently executing
		  
	  }
 
	  @Test
	  public void validateProductOnProductsPage() {
		  
		  utils.log().info("\n\n******* validateProductOnProductsPage  ******* ");
		  productspage = loginpage.login(
				  loginUsers.getJSONObject("validUser").getString("username"), 
				  loginUsers.getJSONObject("validUser").getString("password"));
		  
		  SoftAssert sa = new SoftAssert();
		  String SLBTitle = productspage.getSLBTitle();
		  String SLBPrice = productspage.getSLBPrice();
		  
		  sa.assertEquals(SLBTitle, getStrings().get("products_page_slb_title"));		  
		  sa.assertEquals(SLBPrice, getStrings().get("products_page_slb_price"));
		  sa.assertAll();			  
	  }
	  
	  @Test
	  public void validateProductOnProductDetailsPage() {
		  
		  utils.log().info("\n\n****** validateProductOnProductDetailsPage  ******");
		  
		  productspage = loginpage.login(
				  loginUsers.getJSONObject("validUser").getString("username"), 
				  loginUsers.getJSONObject("validUser").getString("password"));
		  
		  SoftAssert sa = new SoftAssert();
		  productsDetailsPage =  productspage.pressSLBTitle();
		  
		  String SLBTitle = productsDetailsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, getStrings().get("product_details_page_slb_title"));
		  
		  
		  String SLBTxt = productsDetailsPage.scrollToSLBTxtAndGetSLBTxt() ;
		  sa.assertEquals(SLBTxt, getStrings().get("product_details_page_slb_txt"));

		  String SLBPrice = productsDetailsPage.getSLBPrice();
		  sa.assertEquals(SLBPrice, getStrings().get("product_details_page_slb_price"));
		  
		  // -> Commented as this is causing stale element exception for the Settings icon
		  //productspage  = productsDetailsPage.pressBackToProductBtn();
		  sa.assertAll();
	  }
	  @AfterMethod
	  public void afterMethod() {
		  
		  settingspage =  productspage.pressSettingsBtn();
		  loginpage = settingspage.pressLogoutBtn();
		  
		  utils.log().info("Log-out");
		  
		  
	  }
}