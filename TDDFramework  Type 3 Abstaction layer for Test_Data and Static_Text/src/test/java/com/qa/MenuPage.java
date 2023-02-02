package com.qa;

import org.openqa.selenium.WebElement;

import com.qa.pages.LoginPage;
import com.qa.pages.SettingsPage;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPage extends BaseTest{
	
@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView") 
private WebElement settingsBtn;
	
	public SettingsPage  pressSettingsBtn()
	{
		System.out.println("Press Settings Button");
		int attempts = 0;
		 
	    while(attempts < 2) {
	        try {
	    		click(settingsBtn);
	            break;
	        } catch(Exception e) {
	        	System.out.println(e);
	        }
	        }
//		click(settingsBtn);
		return new SettingsPage();
	}

}
