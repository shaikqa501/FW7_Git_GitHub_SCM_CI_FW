package com.meritpros.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.meritpros.util.DataDrivenManager;


public class ShopPageTests extends TestBase {
	
	@Test(priority = 1)
	public void testPageTitle()
	{
		String actualTitle = homePG
								.clickShopLink()
								.getTitle();
		
		Assert.assertEquals(actualTitle, "Products | MeritPros Test1 Site | E-Commerce Test Web Site for MeritPros Students", "ERROR: Title is wrong");
	}

	  @Test(dataProvider="testDataProvider") // All good
	  public void testApplyingSortOrder(String sortOrder)
	  {
		  String actualSortOrder =  homePG
				  						.clickShopLink()
							  			.setSortOrder(sortOrder)
							  			.getSortOrder();
		  
		  Assert.assertTrue(actualSortOrder.equals(sortOrder), "ERROR: Sort order is not applied properly");
	  }
	  
}
