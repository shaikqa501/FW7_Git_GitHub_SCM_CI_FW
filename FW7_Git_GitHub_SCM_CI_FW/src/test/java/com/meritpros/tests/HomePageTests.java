package com.meritpros.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.meritpros.pages.HomePage;

public class HomePageTests extends TestBase {
	
	@Test
	public void testPageTitle()
	{
		String actualTitle = homePG
								.getTitle();
		String expectedTitle= "MeritPros Test1 Site | E-Commerce Test Web Site for MeritPros Students | Home Page_FAIL_";
		
		Assert.assertEquals(actualTitle, expectedTitle, "Page title is not correct");
	}

}
