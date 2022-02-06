package com.meritpros.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.meritpros.pages.HomePage;
import com.meritpros.util.DataDrivenManager;

public class MyAccountPageTests extends TestBase {
	
	@Test
	public void testPageTitle()
	{
		String actualTitle = homePG
								.clickMyAccountLink()
								.getTitle();
		String expectedTitle= "My Account | MeritPros Test1 Site | E-Commerce Test Web Site for MeritPros Students";
		
		Assert.assertEquals(actualTitle, expectedTitle, "Page title is not correct");
	}
	
	@Test(dataProvider="testDataProvider")
	public void testSuccessfulLogin(String username, String password)
	{
		boolean isLoginSuccessful = homePG
										.clickMyAccountLink()
										.loginAs(username, password)
										.isLoginSuccessfulAs(username);
		Assert.assertTrue(isLoginSuccessful, "Login failed for the user");
		

		}
}
