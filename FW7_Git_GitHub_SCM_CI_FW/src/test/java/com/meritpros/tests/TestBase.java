package com.meritpros.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.meritpros.pages.HomePage;
import com.meritpros.util.DataDrivenManager;
import com.meritpros.util.ExtentManager;
import com.meritpros.util.WebDriverManager;

public class TestBase {
	
	WebDriver driver;
	HomePage homePG;
	static Properties testConfig;
	
	static ExtentReports extentReport;
	static ThreadLocal erTestClassThread = new ThreadLocal();
	static ThreadLocal erTestThread = new ThreadLocal();
	ExtentTest erTestClass; // For TestNG Test Class
	ExtentTest erTest;  // For TestNG Test Method
	
	@BeforeSuite()
	public void suiteSetup() throws FileNotFoundException, IOException
	{
		testConfig = new Properties();
		testConfig.load(new FileInputStream("testConfig.properties"));
		
		String extentReportFilePath = "ExtentSparkReport.html";
		extentReport = ExtentManager.createInstance(extentReportFilePath );
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFilePath);
		extentReport.attachReporter(sparkReporter);
	}
	
	@BeforeMethod
	public void testSetup()
	{
		driver = WebDriverManager.createDriver(testConfig.getProperty("browser"));
		driver.get(testConfig.getProperty("baseURL"));
		homePG = new HomePage(driver);
	}
	
	@AfterMethod
	public void testTearDown() throws InterruptedException
	{
		Thread.sleep(2000);
		WebDriverManager.quitDriver(driver);
	}
	
	@DataProvider
	public Object[][] testDataProvider(Method method)
	{
		DataDrivenManager ddm = new DataDrivenManager(testConfig.getProperty("testdatafile"));
		Object[][] testData = ddm.getTestCaseDataSets(testConfig.getProperty("testdatasheet"), method.getName());
		
		return testData;
	}
	
	 @BeforeClass
	    public synchronized void extentReportBeforeClass() {
			
			// Create test class report entry for every TestNG test class in Extent Report
			erTestClass = extentReport.createTest(getClass().getName());
			erTestClassThread.set(erTestClass);
	      }

	    @BeforeMethod
	    public synchronized void extentReportBeforeMethod(Method method) {
	    	
	    	// Create test method report entry in respective test class report entry 
	    	// for every TestNG test method in Extent Report
	        erTest =  erTestClass.createNode(method.getName());
	        erTestThread.set(erTest);
	    }

	    @AfterMethod
	    public synchronized void extentReportAfterMethod(ITestResult result) throws IOException {
	    	// When we reach here or this method is called, we are sure of TestNG test result
	    	// & based on that we are going to set the same result for Extent Test
	    	
	        if (result.getStatus() == ITestResult.FAILURE)
	        	// Fail the erTest when TestNG test is failed
	            erTest.fail(result.getThrowable(), 
									MediaEntityBuilder
									.createScreenCaptureFromPath(getTestFailureScreenshot(result))
									.build());
	        
	        else if (result.getStatus() == ITestResult.SKIP)
	        	// Skip the erTest when TestNG test is skipped
	            erTest.skip(result.getThrowable());
	         
	        else
	        	// Pass the erTest when TestNG test is passed
	           erTest.pass("Test passed");

	        extentReport.flush();
	    }
	    
	    
		public String getTestFailureScreenshot(ITestResult result) throws IOException
		{
			String testFailureScreenshotPath = null;
			if (result.getStatus() == ITestResult.FAILURE)
			{
				// Gives path like TestFailureScreenshots\com.absoft.tests.HomePageTest.testPageTitle.png
				testFailureScreenshotPath = "TestFailureScreenshots/" 
														+ this.getClass().getName() // full class name - com.absoft.tests.HomePageTest
														+ "." 
														+ result.getName() // test method name - testPageTitle
														+ ".png";
				
				// Files, Paths classes are provided by java.nio.file package
				// Create the directory if doesn't exist
				if(Files.notExists(Paths.get("TestFailureScreenshots")))
				{
					Files.createDirectory(Paths.get("TestFailureScreenshots"));
				}
				
				// Delete the old file if exists
				Files.deleteIfExists(Paths.get(testFailureScreenshotPath));
				
				// Create new test failure screenshot file
				WebDriverManager.getScreenshot(driver, testFailureScreenshotPath);
			}
			return testFailureScreenshotPath;
		}

}
