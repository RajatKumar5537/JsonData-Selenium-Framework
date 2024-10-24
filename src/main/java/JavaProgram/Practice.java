package JavaProgram;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Practice {

	public static void main(String [] args) {

		WebDriverManager.chromedriver().setup();
//		ChromeOptions option=new ChromeOptions();

		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://www.flipkart.com/");
		driver.findElement(By.xpath("//input[@class='Pke_EE']")).sendKeys("Samsung ");
		WebElement listOfMobile = driver.findElement(By.xpath("//a[@class='wjcEIp']"));

		Select select = new Select(listOfMobile);

		List<WebElement> allOption =select.getOptions();

		for ( WebElement opt: allOption) {
			String text = opt.getText();
			System.out.println(text);
		}
		driver.quit();

	}
}
