package Generic;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import Pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver ;
	LoginPage loginPage;
	
	
	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	}
	
	@BeforeClass
	public void beforeTestMethod() throws IOException, InterruptedException 
	{
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html"); // Update with the actual URL
		loginPage = new LoginPage(driver);
	}
	
	@AfterClass
	public void afterTestMethod() 
	{
		System.out.println("Logout from the Application");
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
