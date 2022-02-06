package com.meritpros.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends PageBase {
	
	@FindBy(id="login")
	WebElement weLoginButton;
	
	@FindBy(id="username")
	WebElement weUsernameBox;
	
	@FindBy(id="password")
	WebElement wePasswordBox;
	
	@FindBy(id="user_info")
	WebElement weUserInfoText;
	
	public MyAccountPage(WebDriver driver) 
	{
		super(driver);
	}
	
	public MyAccountPage loginAs(String username, String password) 
	{
		weUsernameBox.sendKeys(username);
		wePasswordBox.sendKeys(password);
		weLoginButton.click();
		
		return new MyAccountPage(driver);
	}

	public boolean isLoginSuccessfulAs(String username) 
	{
		return weUserInfoText.getText().contains(username);
	}

}
