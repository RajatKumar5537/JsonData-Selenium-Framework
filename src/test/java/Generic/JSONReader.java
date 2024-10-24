package Generic;

import org.json.JSONArray;
import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {

	@DataProvider(name = "jsonData")
    public Object[][] getJsonData() throws IOException {
        StringBuilder jsonData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/TestData.json"));
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line);
        }
        reader.close();
        JSONArray jsonArray = new JSONArray(jsonData.toString());
        return new Object[][]{{jsonArray}};
    }
}

