package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest {
	
	@AndroidFindBy (accessibility = "test-Username") private WebElement usernameTxtFld;
	@AndroidFindBy (accessibility = "test-Password") private WebElement passwordTxtFld;
	@AndroidFindBy (accessibility = "test-LOGIN") private WebElement loginBtn;
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") private WebElement errorMsg;
	
	public LoginPage enterUserName(String username)
	{
		clear(usernameTxtFld); //Required for iOS, but not necessary for Android
		System.out.println("Username: "+ username);
		sendKeys(usernameTxtFld,username);
		return this;
	}
	
	public LoginPage enterPassword(String password)
	{
		clear(passwordTxtFld); //Required for iOS, but not necessary for Android
		System.out.println("Password: "+ password);
		sendKeys(passwordTxtFld,password);
		return this;
	}
	
	public ProductsPage pressLoginBtn()
	{
		click(loginBtn);
		System.out.println("Press Login Button");
		return new ProductsPage();
	}
	
	public ProductsPage login(String username, String password)
	{
		enterUserName(username);
		enterPassword(password);
		return pressLoginBtn();
	}

	public String getErrorTxt()
	{
		//Will only work for Android
		//return getAttribute(errorMsg,"text");
		
		//Will work for both Android and iOS
		String err = getText(errorMsg);
		System.out.println("Error Text is: "+ getText(errorMsg));
		return err;
	}
}
