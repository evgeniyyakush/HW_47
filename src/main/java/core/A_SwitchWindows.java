
//Switching between Windows

package core;

import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class A_SwitchWindows {
	public static void main(String[] cla) throws InterruptedException {

		String url = "http://alex.academy/exe/links/";
		
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);

		WebDriver driver;
		
//ForChrome
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chrome");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-infobars");
		option.addArguments("--disable-notifications");
		option.addArguments("-start-fullscreen");
		driver = new ChromeDriver(option);

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(url);

		//--------------------------------------------   Process  -------------------------------------------------- //
		
		System.out.println("01. Title: " + driver.getTitle() + ";\t URL: " + driver.getCurrentUrl() + ";\t Handle: " + driver.getWindowHandle());
		driver.findElement(By.id("id_link_top")).click();
		
		System.out.println("02. Title: " + driver.getTitle() + ";\t\t\t\t URL: " + driver.getCurrentUrl() + ";\t\t Handle: " + driver.getWindowHandle());
		driver.navigate().back();
		driver.findElement(By.id("id_link_blank")).click();
		
//WindowSwitch
//toSecond 
//if we would comment "WindowSwitch" then after going to the different tab Selenium would have stayed on the same/old/previous page. 
//instead of String we will put the number of the Handle
//all other browser windows should be closed as it will grab their handles as well	
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		// "\t" - it's TAB
		System.out.println("03. Title: " + driver.getTitle() + ";\t\t\t\t URL: " + driver.getCurrentUrl() + ";\t\t Handle: " + driver.getWindowHandle());
		//if I close now, I will close the handle that is active now
		driver.close();
		

//WindowSwitch	
//toFirst
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
		System.out.println("---------------------------------------------------------");
//here we see all handles of all open windows		
		System.out.println("All Handles: " + driver.getWindowHandles());
		
		//closes all browserss
		driver.quit();
	}
}
