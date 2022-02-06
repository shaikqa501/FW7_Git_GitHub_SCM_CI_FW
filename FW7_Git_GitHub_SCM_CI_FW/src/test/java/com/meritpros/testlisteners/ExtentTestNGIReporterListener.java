package com.meritpros.testlisteners;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentTestNGIReporterListener implements IReporter {
    
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "Extent.html";
    
    private ExtentReports extentReport;

   // @Override
    public void generateReport(List xmlSuites, List suites, String outputDirectory) {
        init();
        
        for (Object suite : suites) {
            Map result = ((ISuite)suite).getResults();
            
            for (Object r : result.values()) {
                ITestContext context = ((ISuiteResult )r).getTestContext();
                
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
                buildTestNodes(context.getPassedTests(), Status.PASS);
                
            }
        }
        
        for (String s : Reporter.getOutput()) {
            extentReport.addTestRunnerOutput(s);
        }
        
        extentReport.flush();
    }
    
    private void init() {
        ExtentSparkReporter sparkReporter= new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        sparkReporter.config().setDocumentTitle("ExtentReports - Created by TestNG Listener");
        sparkReporter.config().setReportName("ExtentReports - Created by TestNG Listener");
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        extentReport.setReportUsesManualConfiguration(true);
    }
    
    private void buildTestNodes(IResultMap tests, Status status) {
        ExtentTest test;
        
        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extentReport.createTest(result.getMethod().getMethodName());
                
                for (String group : result.getMethod().getGroups())
                    test.assignCategory(group);

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                }
                else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }
                
                test.getModel().setStartTime(getTime(result.getStartMillis()));
                test.getModel().setEndTime(getTime(result.getEndMillis()));
            }
        }
    }
    
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();      
    }
}
