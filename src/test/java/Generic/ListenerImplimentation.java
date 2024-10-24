package Generic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

public class ListenerImplimentation extends BaseClass implements ITestListener {
	ExtentReports report;
	ExtentTest test;

	public ListenerImplimentation() throws IOException {
		report = ReusableComponent.getInstance(); // Using the singleton , for single single report
		report.setSystemInfo("OS", System.getProperty("os.name"));
		report.setSystemInfo("Java Version", System.getProperty("java.version"));
		report.setSystemInfo("Base Url", ReusableComponent.getCongigPropertyData("url"));
	}

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		setupTest(methodName, result.getTestContext()); // Pass the ITestContext to setupTest
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getName();
		long timestamp = System.currentTimeMillis();
		File dest = new File(System.getProperty("user.dir") + "/ScreenShot/" + testName + "_" + timestamp + ".png");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StackTraceElement[] stackTrace = result.getThrowable().getStackTrace();
		int lineNumber = -1;

		for (StackTraceElement element : stackTrace) {
			String className = element.getClassName();
			if (className.startsWith("CAW Studios") || className.startsWith("CAW Studios")) {
				lineNumber = element.getLineNumber();
				break;
			}
		}
		test.log(Status.FAIL, "Test Failed at Line " + lineNumber + " - Check screenshot and error message below:",
				MediaEntityBuilder.createScreenCaptureFromPath(dest.getAbsolutePath()).build())
				.fail(MarkupHelper.createCodeBlock(result.getThrowable().getMessage()));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		setupTest(methodName, result.getTestContext());
		String skipMessage = "Test skipped - Method: " + methodName;
		if (result.getThrowable() != null) {
			skipMessage += " - Reason: " + result.getThrowable().getMessage();
			String codeBlock = MarkupHelper.createCodeBlock(result.getThrowable().getMessage()).getMarkup();
			test.log(Status.SKIP, codeBlock);
		} else {
			test.log(Status.SKIP, skipMessage);
		}
	}

	@Override
	public void onStart(ITestContext context) {
		String buildName = " CAW Studios";
		String reportName = "Automation Test Report - " + buildName;
		String timestamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
		String reportFileName = "ExtentReport_" + timestamp + ".html";

		ExtentSparkReporter spark = new ExtentSparkReporter("./ExtentReport/" + reportFileName);
		report = ReusableComponent.getInstance(); // Using the singleton instance
		report.attachReporter(spark); // Attach the HTML reporter to the existing report instance

		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("Automation Test Report for CAW Studios");
		spark.config().setReportName(reportName);
		spark.viewConfigurer().viewOrder()
				.as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.CATEGORY, ViewName.AUTHOR, }).apply();

	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	private void setupTest(String methodName, ITestContext context) {
		String suiteName = context.getSuite().getName();
		String testName = context.getCurrentXmlTest().getName();

		test = report.createTest(methodName);
		try {
			test.assignAuthor(ReusableComponent.getCongigPropertyData("author"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Assign author and category tags based on suite and test names
		test.assignCategory(suiteName, testName);
		test.assignDevice("Web Application");
		test.info("CAW Studios Automation Test: Verifying Dynamic HTML Table Data");
	}

}
