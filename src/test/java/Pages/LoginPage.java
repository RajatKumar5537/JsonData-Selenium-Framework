package Pages;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	WebDriver driver;

	@FindBy(xpath = "//summary[normalize-space()='Table Data']")
	WebElement tableDataButton;

	@FindBy(xpath  = "//textarea[@id='jsondata']") 
	WebElement inputTextBox;

	@FindBy(xpath = "//button[@id='refreshtable']") 
	WebElement refreshButton;

	@FindBy(xpath = "//table[@id='dynamictable']/tr") 
	List<WebElement> tableRows;

	@FindBy(xpath = "//table[@id='dynamictable']/tr/td")
	List<WebElement> tableCells;


	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}	

	// Method to insert data into the input text box and click the refresh button
	public void insertData(String jsonData) throws InterruptedException {
		tableDataButton.click();
		inputTextBox.clear(); 
		inputTextBox.sendKeys(jsonData); // Send JSON data into the text box

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", refreshButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", refreshButton);
	}

	// Method to retrieve data from the table in a 2D String array format
	public String[][] getTableData() {
		int numberOfRows = tableCells.size() / 3; 
		String[][] tableData = new String[numberOfRows][3];
		for (int i = 0; i < numberOfRows; i++) 
		{
			tableData[i][0] = tableCells.get(i * 3 + 1).getText(); // Name (2nd column)
	        tableData[i][1] = tableCells.get(i * 3 + 2).getText(); // Age (3rd column)
	        tableData[i][2] = tableCells.get(i * 3).getText();     // Gender (1st column)
		}
		return tableData; 
	}
}
