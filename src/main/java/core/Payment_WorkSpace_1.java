
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

public class Payment_WorkSpace_1 {
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
		String url = "http://alex.academy/exe/payment/index4.html";
//		String url = "http://alex.academy/exe/payment/indexE.html";

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
		String string_monthly_payment = driver.findElement(By.id("id_monthly_payment")).getText().trim();
		//in here we simply go to the web page, grab Element and simply print it out
		
		/////////////////////////////////////////////  -  KnolendgeBase - //////////////////////////////////////
																																    //
//		#String to float 
//		#will convert in "primitive float"
//		--> Float.parseFloat()
//
//		String s="23.6";  
//		float f=Float.parseFloat("23.6");
//
//		#if we write, it will convert it into "Object"
//		--> Float.valueOf()
																																	 //
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//here we convert "String" into "Float"
		//it will take String and will conver it into the Float
		//"$1,654.56" we canNOT convert this into the Float due to "$" and ","
		//System.out.println(Float.parseFloat(string_monthly_payment) * 12);
		//now we replace "$" and ","
		
		//Examples of replacements
		//System.out.println(string_monthly_payment);
		//System.out.println(string_monthly_payment.replaceAll("\\$", ""));
		//System.out.println(string_monthly_payment.replaceAll("\\$", "").replaceAll(",", ""));
		
		//".trim()" - removes all white spaces(space, tab, new line)
		System.out.println(Float.parseFloat(string_monthly_payment.replaceAll("\\$", "").replaceAll(",", "").trim()) * 12);
		
		
		
//		String regex = "^" + "(?:\\$)?" + "(?:\\s*)?" + "((?:\\d{1,3})(?:\\,)?(?:\\d{3})?(?:\\.)?(\\d{0,2})?)" + "$";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(string_monthly_payment);
//		m.find();
//		
//		// 1,654.55
//		double monthly_payment = Double.parseDouble(m.group(1).replaceAll(",", ""));
//		// 1654.55 * 12 = 19854.60
		
		//here we just typed what we were printing into a command line
		//so in here we are going to have float 
		
		
		//we do this in order not to use Regex
		double monthly_payment = Float.parseFloat(string_monthly_payment.replaceAll("\\$", "").replaceAll(",", "").trim());
		//we convert into a BigDecimal() in order to have a certain number of digits after the "." we do it using by ".setScale(2, and here the way we do it) then we convert it back into a "double" by "doubleValue()"
		double annual_payment = new BigDecimal(monthly_payment * 12).setScale(2, RoundingMode.HALF_UP).doubleValue();
		// 19854.6

		DecimalFormat df = new DecimalFormat("0.00");
		String f_annual_payment = df.format(annual_payment);
		driver.findElement(By.id("id_annual_payment")).sendKeys(String.valueOf(f_annual_payment));
		driver.findElement(By.id("id_validate_button")).click();
		String actual_result = driver.findElement(By.id("id_result")).getText().trim();


		//System.out.println("String: \t" + m.group(0)); // capturing whole thing
		System.out.println("Annual Payment: " + f_annual_payment);
		System.out.println("Result: \t" + actual_result);

		
		//finishBenchmark
		final long finish = System.currentTimeMillis();
		System.out.println("Response time: \t" + (finish - start) + " milliseconds");

		driver.quit();
	}
}
