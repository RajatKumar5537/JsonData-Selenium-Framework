package Tests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import Generic.BaseClass;
import Generic.JSONReader;
import Pages.LoginPage;

public class LoginTest extends BaseClass{
	LoginPage loginPage;
	JSONObject testData;


	@Test(dataProvider = "jsonData", dataProviderClass = JSONReader.class)
	public void testInsertAndVerifyData(JSONArray jsonDataList) throws InterruptedException {
		loginPage = new LoginPage(driver);
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < jsonDataList.length(); i++) {
			JSONObject data = jsonDataList.getJSONObject(i);
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("name", data.get("name")); 
			jsonObject.put("age", data.get("age"));
			jsonObject.put("gender", data.get("gender"));
			jsonArray.put(jsonObject);
		}

		loginPage.insertData(jsonArray.toString());

		String[][] tableData = loginPage.getTableData();

		for (int i = 0; i < jsonDataList.length(); i++) {
			JSONObject jsonData = jsonDataList.getJSONObject(i);
			Assert.assertEquals(tableData[i][0], jsonData.get("name").toString(), "Name mismatch at row " + i);
		    Assert.assertEquals(tableData[i][1], jsonData.get("age").toString(), "Age mismatch at row " + i);
		    Assert.assertEquals(tableData[i][2], jsonData.get("gender").toString(), "Gender mismatch at row " + i);
		}
	}

}
