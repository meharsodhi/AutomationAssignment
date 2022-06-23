package openCart;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class openCartTestCases extends DriverInitialization {
	
	@Parameters("browser")
	public openCartTestCases(String browser) {
		super(browser);
	}
	
	MainPage mp=new MainPage(driver);
	String email=mp.createRandomString()+"@gmail.com";
	String password=mp.createRandomString(); 
	ArrayList<Double> price;
	
  
  @Test
  public void verifyUserRegisteration() {
	  try {
	  driver.findElement(mp.myAccount).click();
	  Thread.sleep(1000);
	  driver.findElement(mp.register).click();
	  Thread.sleep(3000);
	  driver.findElement(mp.firstName).sendKeys(mp.createRandomString());
	  driver.findElement(mp.lastName).sendKeys(mp.createRandomString());
	  driver.findElement(mp.email).sendKeys(email);
	  driver.findElement(mp.telephone).sendKeys(mp.createRandomNumber());
	  driver.findElement(mp.password).sendKeys(password);
	  driver.findElement(mp.passwordConfirm).sendKeys(password);
	  driver.findElement(mp.privacyPolicy).click();
	  driver.findElement(mp.continueButton).click();
	  Thread.sleep(4000);
	  Assert.assertEquals(mp.verifyDisplayedText("Your Account Has Been Created!"), true);
	  driver.findElement(mp.myAccount).click();
	  Thread.sleep(1000);
	  driver.findElement(mp.logout).click();
	  Thread.sleep(3000);
	  driver.findElement(mp.continueLink).click();
	  Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
  }

  @Test
  public void verifyUserLogin() {
	  try {
	  driver.findElement(mp.myAccount).click();
	  Thread.sleep(1000);
	  driver.findElement(mp.login).click();
	  Thread.sleep(3000);
	  driver.findElement(mp.login_email).sendKeys(email);
	  driver.findElement(mp.login_password).sendKeys(password);
	  driver.findElement(mp.login_button).click();
	  Thread.sleep(3000);
	  Assert.assertEquals(mp.verifyDisplayedText("My Account"), true);
	  Assert.assertEquals(mp.verifyDisplayedText("View your order history"), true);
	  driver.findElement(mp.myAccount).click();
	  Thread.sleep(1000);
	  driver.findElement(mp.logout).click();
	  Thread.sleep(3000);
	  driver.findElement(mp.continueLink).click();
	  Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  
  }
  
  @Test
  public void verifyMostExpensiveLaptop() {
	  
	 price=mp.extractItemPrices();
     int maxIndex=price.indexOf(Collections.max(price))+1;
     String maxItemXpath="(//div[@class='caption']//a)["+maxIndex+"]";
     Assert.assertEquals(driver.findElement(By.xpath(maxItemXpath)).getText(),"MacBook");
    
	  
  }
  
  @Test
  public void verifyCalculatedCostOfProducts() throws InterruptedException {  
	  price=mp.extractItemPrices(); 
	  List<WebElement> elements=driver.findElements(By.xpath("(//span[text()='Add to Cart']/preceding-sibling::i)"));
	  double total=0.0;
	  for(int x=0;x<2;x++) {
		 elements.get(x).click();
		 Thread.sleep(1000);
		 total=total+price.get(x);
	  }
	  
	  String extractedPrice=driver.findElement(mp.totalPrice).getText();
	  extractedPrice=extractedPrice.substring(extractedPrice.indexOf("$")+1);
	  double calculatedPrice=Double.parseDouble(extractedPrice);
	  System.out.println("total"+total);
	  System.out.println("calculatedPrice"+calculatedPrice);
	  Assert.assertEquals(total, calculatedPrice);
  }
  
  @Test(dataProvider="Credentials")
  public void verifyLoggingInWithInvalidCredentials(String username, String password) throws InterruptedException {  
	 
		  driver.findElement(mp.myAccount).click();
		  Thread.sleep(1000);
		  driver.findElement(mp.login).click();
		  Thread.sleep(3000);
		  driver.findElement(mp.login_email).sendKeys(username);
		  driver.findElement(mp.login_password).sendKeys(password);
		  driver.findElement(mp.login_button).click();  
		  Thread.sleep(3000);
		  Assert.assertEquals(mp.verifyDisplayedText("Warning: "), true);
	  
  }
  
 
  @DataProvider(name="Credentials")
	public Object[][] readCredentials() throws IOException
	{
	FileInputStream fileInputStream= new FileInputStream("Credentials.xlsx"); //Excel sheet file location get mentioned here
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	DataFormatter formatter= new DataFormatter();	
	workbook = new XSSFWorkbook (fileInputStream); //get my workbook 
	worksheet=workbook.getSheet("Sheet1");// get my sheet from workbook
 	    XSSFRow Row=worksheet.getRow(0);   	 //get my Row which start from 0   
 	
  	int RowNum = worksheet.getPhysicalNumberOfRows();// count my number of Rows
  	int ColNum= Row.getLastCellNum(); // get last ColNum 
  	
  	Object Data[][]= new Object[RowNum-1][ColNum]; // pass my  count data in array
  	
  		for(int i=0; i<RowNum-1; i++) //Loop work for Rows
  		{  
   			XSSFRow row= worksheet.getRow(i+1);
  			
  			for (int j=0; j<ColNum; j++) //Loop work for colNum
  			{
  				if(row==null)
  					Data[i][j]= "";
  				else 
  				{
  					XSSFCell cell= row.getCell(j);
  					if(cell==null)
  						Data[i][j]= ""; //if it get Null value it pass no data 
  					else
  					{
  						String value=formatter.formatCellValue(cell);
  						Data[i][j]=value; //This formatter get my all values as string i.e integer, float all type data value
  					}
  				}
  			}
  		}

  	return Data;
	
  }
  

  
  
}
