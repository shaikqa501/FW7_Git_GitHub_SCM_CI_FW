package com.meritpros.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase {
	
	WebDriver driver;
	
	public PageBase(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getTitle()
	{
		return driver.getTitle();
	}

}
