package utilities;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter; //UI of report
	public ExtentReports extent;  //popup common info
	public ExtentTest test;     //creating test case & report

	String repName;
	
	public void onStart(ITestContext testContext)
	{
		//String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String timeStamp =df.format(dt);
		
		repName="Test-Report" + timeStamp + ".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Report");
		sparkReporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("ENV", "QA");
		extent.setSystemInfo("Tester Name", "Yuvraj");
		extent.setSystemInfo("User Name",System.getProperty("user.name"));
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	    if(!includeGroups.isEmpty())
	    {
	    	extent.setSystemInfo("Groups", includeGroups.toString());
	    }
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName()); //create a new entry in report
		test.assignCategory(result.getMethod().getGroups());  //to display groups in report
		test.log(Status.PASS, result.getName()+"got successfully executed"); //update status
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+"got failed");
	    test.log(Status.INFO, result.getThrowable().getMessage()) ;
	    
	    try
	    {
	    	String imgpath = new BaseClass().captureScreen(result.getName());
	    	test.addScreenCaptureFromPath(imgpath);
	    }
	    catch(IOException e1)
	    {
	    	e1.printStackTrace();
	    }
	    
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, result.getName()+"got sjipped");
	    test.log(Status.INFO, result.getThrowable().getMessage()) ;
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
