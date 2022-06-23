package openCart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Parameters;

/**
 * Hello world!
 *
 */
public class DriverInitialization 

{
	WebDriver driver;
	
	@Parameters("browser")
	public DriverInitialization(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	    	driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("edge")){
			System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
			 driver = new EdgeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			 driver = new FirefoxDriver();
		}
		
    	driver.get("https://naveenautomationlabs.com/opencart/index.php?route=common/home");
    	driver.manage().window().maximize();
    	try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
