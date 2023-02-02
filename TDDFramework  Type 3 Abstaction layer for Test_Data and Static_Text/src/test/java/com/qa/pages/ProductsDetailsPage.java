package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.MenuPage;

import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsDetailsPage extends MenuPage {
	
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
		System.out.println("Title: "+ title);
		return title;
	}
	
	public String getSLBTxt(){
		String Txt = getText(SLBTxt);
		System.out.println("Description: "+ Txt);
		return Txt;
	}
	public String getSLBPrice(){
		String Price = getText(SLBPrice);
		System.out.println("Price: "+ Price);
		return Price;
	}
	
	public ProductsDetailsPage scrollToSLBPrice()
	{
		scrollToElement();
		return this;
	}
	
	public ProductsPage pressBackToProductBtn()
	{
		System.out.println("Navigate back to product page.........");
		click(backToProductBtn);
		return new ProductsPage();
	}
	
}
