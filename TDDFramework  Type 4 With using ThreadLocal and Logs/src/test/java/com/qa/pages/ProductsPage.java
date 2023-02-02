package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.MenuPage;
import com.qa.utils.TestUtils;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage {
	
	TestUtils utils = new TestUtils();
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView") 
	private WebElement productTitleTxt;
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]") 
	private WebElement SLBTitle;
	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]") 
	private WebElement SLBPrice;
	
	public String getTitle(){
		String title =  getText(productTitleTxt);
		utils.log().info("Product Page Title is: "+ title);
		return title;
	}
	
	public String getSLBTitle(){
		String title = getText(SLBTitle);
		utils.log().info("Product Page Title: "+ title);
		return title;
	}
	
	public String getSLBPrice(){
		String Price = getText(SLBPrice);
		utils.log().info("Product Price: "+ Price);
		return Price;
	}
	
	public ProductsDetailsPage pressSLBTitle()
	{
		utils.log().info("Press SLB title link");
		click(SLBTitle);
		return new ProductsDetailsPage();
	}
	
}
