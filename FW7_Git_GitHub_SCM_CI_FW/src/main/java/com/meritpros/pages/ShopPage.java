package com.meritpros.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ShopPage extends PageBase {
	
	@FindBy(name="orderby")
	WebElement weSortDropDown;
	
	public ShopPage(WebDriver driver)
	{
		super(driver);
	}

	public ShopPage setSortOrder(String sortOrder)
	{
		Select sortList = new Select(weSortDropDown);
		sortList.selectByVisibleText(sortOrder);
		return new ShopPage(driver);
	}
	
	public String getSortOrder()
	{
		Select sortList = new Select(weSortDropDown);
		
		 return sortList.getFirstSelectedOption().getText();
	}

}
