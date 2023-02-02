package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends BaseTest {
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") private WebElement productTitleTxt;
	
	public String getTitle()
	{
		return getAttribute(productTitleTxt, "text");
		
	}
	
	
}
