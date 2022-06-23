package openCart;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainPage {

	WebDriver driver;
    

	
	public MainPage(WebDriver driver) {
		
		  this.driver=driver;
	}
	
	By myAccount=By.xpath("//span[text()='My Account']");
	By register=By.xpath("//a[contains(text(),'Register')]");
	By login=By.xpath("//a[contains(text(),'Login')]");
	By logout=By.xpath("//a[contains(text(),'Logout')]");
	By login_email=By.xpath("//input[@name='email']");
	By login_password=By.xpath("//input[@name='password']");
	By login_button=By.xpath("//input[@value='Login']");
	By firstName=By.xpath("//input[@placeholder='First Name']");
	By lastName=By.xpath("//input[@placeholder='Last Name']");
	By email=By.xpath("//input[@placeholder='E-Mail']");
	By telephone=By.xpath("//input[@placeholder='Telephone']");
	By password=By.xpath("//input[@placeholder='Password']");
	By passwordConfirm=By.xpath("//input[@placeholder='Password Confirm']");
	By privacyPolicy=By.xpath("//input[@name='agree']");
	By continueButton=By.xpath("//input[@value='Continue']");
	By continueLink=By.xpath("//a[text()='Continue']");
	By totalPrice=By.xpath("//span[@id='cart-total']");
	
	public boolean verifyDisplayedText(String text) {
		String xpath="//*[contains(text(),'"+text+"')]";
		List<WebElement> elements=driver.findElements(By.xpath(xpath));
		if(elements.size()>0) 
			return true;
		else
			return false;
		
		
	}
	
	public String createRandomString() {
		String alphaString="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";
		String randString="";
		for(int x=0;x<10;x++) {
			int ch = (int)(alphaString.length() * Math.random());
			randString=randString+alphaString.charAt(ch);			
		}
		
		return randString;
		
		
	}
	
	public String createRandomNumber() {
		String alphaString="1234567890";
		String randString="";
		for(int x=0;x<10;x++) {
			int ch = (int)(alphaString.length() * Math.random());
			randString=randString+alphaString.charAt(ch);			
		}
		
		return randString;
		
		
	}
	
	public ArrayList<Double> extractItemPrices() {
		
		List<WebElement> elements=driver.findElements(By.xpath("//p[contains(@class,'price')]"));
		  ArrayList<Double> price=new ArrayList<Double>();
	     for(WebElement element: elements) {    	 
	    	 String s=element.getText();
	    	 String requiredString = s.substring(s.indexOf("$") + 1, s.indexOf("$",s.indexOf("$") + 1 )).trim();
	    	 if(requiredString.contains("Ex")) {
	    		 requiredString=requiredString.substring(0, requiredString.indexOf("Ex")).trim();
	    	 }
	    	 price.add(Double.parseDouble(requiredString));  	 
	     }
	     
	     return price;
		
	}
	
	
	
}
