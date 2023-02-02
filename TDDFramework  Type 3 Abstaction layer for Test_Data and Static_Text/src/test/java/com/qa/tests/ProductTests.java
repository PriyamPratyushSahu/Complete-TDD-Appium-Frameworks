


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

public class ProductTests extends BaseTest{
	
	LoginPage loginpage;
	ProductsPage productspage;
	SettingsPage settingspage;
	ProductsDetailsPage productsDetailsPage;
	
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
		  System.out.println("Log-in");
		  loginpage = new LoginPage();
		  System.out.println("\n" + "******* STARTING TEST: "+ m.getName() + "-*******\n"); //getName() gives the method name currently executing
		  
		  launchApp(platform);
		  productspage =  loginpage.login(loginUsers.getJSONObject("validUser").getString("username"),
				  loginUsers.getJSONObject("validUser").getString("password"));
	  }

	  @AfterMethod
	  public void afterMethod() {
		  
		  settingspage =  productspage.pressSettingsBtn();
		  loginpage = settingspage.pressLogoutBtn();
		  closeApp(platform);
		  System.out.println("Log-out");		  
	  }
	  
	  @Test
	  public void validateProductOnProductsPage() {
		  
		  SoftAssert sa = new SoftAssert();
		  String SLBTitle = productspage.getSLBTitle();
		  String SLBPrice = productspage.getSLBPrice();
		  
		  sa.assertEquals(SLBTitle, strings.get("products_page_slb_title"));		  
		  sa.assertEquals(SLBPrice, strings.get("products_page_slb_price"));
		  sa.assertAll();	
		  
		  System.out.println("\n\n******* validateProductOnProductsPage Successful ******* ");
	  }
	  
	  @Test
	  public void validateProductOnProductDetailsPage() {
		  
		  SoftAssert sa = new SoftAssert();
		  
		  productsDetailsPage =  productspage.pressSLBTitle();
		  
		  String SLBTitle = productsDetailsPage.getSLBTitle();
		  sa.assertEquals(SLBTitle, strings.get("product_details_page_slb_title"));
		  
		  productsDetailsPage.scrollToElement();
		  
		  String SLBTxt = productsDetailsPage.getSLBTxt();
		  sa.assertEquals(SLBTxt, strings.get("product_details_page_slb_txt"));
		  
		  String SLBPrice = productsDetailsPage.getSLBPrice();
		  sa.assertEquals(SLBPrice, strings.get("product_details_page_slb_price"));
		  
		  productspage  = productsDetailsPage.pressBackToProductBtn();
		  sa.assertAll();
		  System.out.println("\n\n****** validateProductOnProductDetailsPage Successful ******");
	  }
}