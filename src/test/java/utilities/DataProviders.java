package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider
	
	@DataProvider(name="LoginData")
	
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\Opencart_LoginData.xlsx";   //taking xl file from utolity
		
		ExcelUtility xlutil=new ExcelUtility(path);
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String [totalrows][totalcols]; //created for 2 dimension array
		
		for(int i=1;i<=totalrows;i++)   //read data from xl storing in 2 dimentional array
		{
			for(int j=0;j<totalcols;j++) // i is row j is col
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
		}
			return logindata; //returning 2 dimentional data
		
		
		
		
	}
}
