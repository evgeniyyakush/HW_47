package core;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.regex.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

public class A_PaymentTax {

	public static void main(String[] cla) throws InterruptedException {
		String url = "http://alex.academy/exe/payment_tax/index.html";
		Logger logger = Logger.getLogger(""); logger.setLevel(Level.OFF);
		WebDriver driver;
		final long start = System.currentTimeMillis();	
             System.setProperty("webdriver.chrome.driver","/usr/local/bin/chrome");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions option = new ChromeOptions();
		option.addArguments("-start-fullscreen"); driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		driver.get(url);               // "Payment: $91.21, Tax: 8.25%";
String string_monthly_payment_and_tax = driver.findElement(By.id("id_monthly_payment_and_tax")).getText(); 
		      String regex = "^"    
		                + "(?:\\$)?"
		                + "(?:\\s*)?"
		                + "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)"
		                + "$";
		      
		    //Regex of Dima
		    //[0-9]+\\.[0-9]{2}
		      
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string_monthly_payment_and_tax); m.find();
		double monthly_payment = Double.parseDouble(m.group(1));
		double tax = Double.parseDouble(m.group(2));
		// (91.21 * 8.25) / 100 = 7.524825
  double monthly_and_tax_amount = new BigDecimal((monthly_payment * tax) / 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
		// 91.21 + 7.52 = 98.72999999999999
  double monthly_payment_with_tax = new BigDecimal(monthly_payment + monthly_and_tax_amount).setScale(2, RoundingMode.HALF_UP).doubleValue();
  double annual_payment_with_tax = new BigDecimal(monthly_payment_with_tax * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();
  driver.findElement(By.id("id_annual_payment_with_tax")).sendKeys(String.valueOf(annual_payment_with_tax));
		driver.findElement(By.id("id_validate_button")).submit();
		String actual_result = driver.findElement(By.id("id_result")).getText().trim();
		final long finish = System.currentTimeMillis();
		System.out.println("String: \t\t " + m.group(0));
		System.out.println("Annual Payment with Tax: " + annual_payment_with_tax);
		System.out.println("Result: \t\t " + actual_result);
		System.out.println("Response time: \t\t " + (finish - start) + " milliseconds");
		Thread.sleep(2000);
             driver.quit();
             }
}
