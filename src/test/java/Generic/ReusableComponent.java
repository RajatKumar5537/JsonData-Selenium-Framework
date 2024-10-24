package Generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReusableComponent {
	public static String getCongigPropertyData(String key) throws IOException
	{
		FileInputStream fis=new FileInputStream("src\\test\\resources\\credential.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String data = prop.getProperty(key);
		return data;
	}
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public static synchronized ExtentReports getInstance() {
		if (extent == null) {
			extent = new ExtentReports();
		}
		 ExtentSparkReporter htmlReporter = new ExtentSparkReporter("ExtentReport.html");
	        extent.attachReporter(htmlReporter);
		return extent;
	}
	

    public static ExtentReports createInstance() {
    	extent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("ExtentReport.html");
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
