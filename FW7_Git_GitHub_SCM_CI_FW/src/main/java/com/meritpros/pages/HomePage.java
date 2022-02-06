package com.meritpros.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends PageBase{
	
	@FindBy(linkText = "My Account")
	WebElement weMyAccountLink;
	
	@FindBy(linkText = "Shop")
	WebElement weShopLink;
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
	}

	public MyAccountPage clickMyAccountLink()
	{
		weMyAccountLink.click();
		return new MyAccountPage(driver);
	}
	
	public ShopPage clickShopLink()
	{
		this.weShopLink.click();
		return new ShopPage(driver);
	}
}
