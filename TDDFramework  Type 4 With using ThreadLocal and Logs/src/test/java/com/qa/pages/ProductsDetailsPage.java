package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.MenuPage;
import com.qa.utils.TestUtils;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsDetailsPage extends MenuPage {
	
	TestUtils utils = new TestUtils();
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private WebElement SLBTitle;
	@AndroidFindBy(accessibility = "test-Price")
	private WebElement SLBPrice;
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]") 
	private WebElement SLBTxt;
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-BACK TO PRODUCTS\"]") 
	private WebElement backToProductBtn;

	
	public String getSLBTitle(){
		String title = getText(SLBTitle);
		utils.log().info("Title: "+ title);
		return title;
	}
	
	public String getSLBTxt(){
		String Txt = getText(SLBTxt);
		utils.log().info("Description: "+ Txt);
		return Txt;
	}
	public String getSLBPrice(){
		String Price = getText(SLBPrice);
		utils.log().info("Price: "+ Price);
		return Price;
	}
	
	public ProductsDetailsPage scrollToSLBPrice()
	{
		scrollToElement();
		return this;
	}
	
	public ProductsPage pressBackToProductBtn()
	{
		utils.log().info("Navigate back to product page.........");
		click(backToProductBtn);
		return new ProductsPage();
	}
	
}
