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
		sendKeys(usernameTxtFld,username);
		return this;
	}
	
	public LoginPage enterPassword(String password)
	{
		sendKeys(passwordTxtFld,password);
		return this;
	}
	
	public ProductsPage pressLoginBtn()
	{
		click(loginBtn);
		return new ProductsPage();
	}

	public String getErrorTxt()
	{
		return getAttribute(errorMsg,"text");
	}
}
