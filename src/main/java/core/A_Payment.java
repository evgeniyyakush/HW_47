
//This one is for Safari
//Regex it's a Class #12 of our course

package core;

import org.openqa.selenium.*;
import org.openqa.selenium.safari.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import java.util.regex.*;

public class A_Payment {
	static WebDriver driver;

	public static void main(String[] args) throws InterruptedException {

		Logger.getLogger("").setLevel(Level.OFF);

		if (!System.getProperty("os.name").contains("Mac")) {
			throw new IllegalArgumentException("Safari is available only on Mac");
		}
		//WebPages we are going to search through
//		String url = "http://alex.academy/exe/payment/index.html";
//		String url = "http://alex.academy/exe/payment/index2.html";
//		String url = "http://alex.academy/exe/payment/index3.html";
//		String url = "http://alex.academy/exe/payment/index4.html";
		String url = "http://alex.academy/exe/payment/indexE.html";

		//All prerequisites 
		driver = new SafariDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);

		//startBenchmark
		final long start = System.currentTimeMillis();
		
		//Elements
		
		//Xpath for Monthly Payment Element: 
		//xpath: //*[@id="id_monthly_payment"]
		
		//Xpath for Annual Payment Element: 
		//xpath: //*[@id="id_annual_payment"]
		
		//Xpath for Validate Botton: 
		//xpath: //*[@id="id_validate_button"]
		
		//Xpath for Error/Correct Element: 
		//xpath: //*[@id="id_result"]
		
		//We create Regex that takes just numbers out of here
		//As with different variations we canNOT take it and multiply it by 12(and it's exactly what we need to do in here)
		//if we want to do any math operations with it it has to be able to be converted into: int, double or flout
		
		// we have to get rid of: "$", ","
		//dirty way, we could use "replace for it"
		
//		$1,654.55
//		$ 1,654.55
//		1,654.55
//		1654.55
//		$1,654.56
		

		// "$1,654.55";
		String string_monthly_payment = driver.findElement(By.id("id_monthly_payment")).getText();
		
		
		
		
		// -------------- Regex ----------//
		//String regex = "^" + "(?:\\$)?" + "(?:\\s*)?" + "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)" + "$";
		//"^" - beginning of the line
		//"$" - end of the line
		//this will let us go through all cases
		//Tool to work with:     http://alex.academy/exe/regex/
		//in java we command command after which we have "\\"
		
		//FirstRegex
		//"^" - it's beginning of the sentence
		String regex = "^"
                					+ "(?:\\$)?"
                					+ "(?:\\s*)?"
                					+ "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)"
                					+ "$";
		
//		//SecondRegex
//		String regex = "^"   
//                					  + "(?:.*?)?"
//                					  + "(?:\\$*)?"
//                					  + "(?:\\s*)?"
//                					  + "((?:\\d*)|(?:\\d*)(?:\\.)(?:\\d*))"
//                					  + "(?:\\s*)?"
//                					  + "(?:[/]*|,\\s*[A-Z]*[a-z]*\\s*[:]*)?"
//                					  + "(?:\\s*)?"
//                					  + "((?:\\d*)|(?:\\d*)(?:\\.)(?:\\d*))"
//                					  + "(?:\\s*)?"
//                					  + "(?:%)?"
//                					  + "(?:\\s*)?"
//                					  + "$";

		
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string_monthly_payment);
		m.find();
		
		// 1,654.55
		double monthly_payment = Double.parseDouble(m.group(1).replaceAll(",", ""));
		// 1654.55 * 12 = 19854.60
		double annual_payment = new BigDecimal(monthly_payment * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();
		// 19854.6

		DecimalFormat df = new DecimalFormat("0.00");
		String f_annual_payment = df.format(annual_payment);
		driver.findElement(By.id("id_annual_payment")).sendKeys(String.valueOf(f_annual_payment));
		//"click" does not work in here
		//driver.findElement(By.id("id_validate_button")).click();
		driver.findElement(By.id("id_validate_button")).submit();
		String actual_result = driver.findElement(By.id("id_result")).getText().trim();
		//String actual_result = driver.findElement(By.id("id_result")).getAttribute("value").toString().trim();


		System.out.println("String: \t" + m.group(0)); // capturing whole thing
		System.out.println("Annual Payment: " + f_annual_payment);
		System.out.println("Result: \t" + actual_result);

		
		//finishBenchmark
		final long finish = System.currentTimeMillis();
		System.out.println("Response time: \t" + (finish - start) + " milliseconds");

		driver.quit();
	}
}
