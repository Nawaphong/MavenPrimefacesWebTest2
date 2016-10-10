package selenium.web.test;

import static org.testng.AssertJUnit.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Webtest {
	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static WebElement element = null;
	protected static JavascriptExecutor jsExecutor;
	public static void assertTestUpdate(String name,String qty,String user){
		assertEquals("cat02", name);
		assertEquals("5", qty);
		assertEquals("test02", user);
	}
	public static void assertTestDelete(String name){
		AssertJUnit.assertTrue(!"cat02".equals(name));
	}
	@Parameters("browser")
	@BeforeClass
	public void initWebDriver(String browser) throws MalformedURLException{
		if (browser.equalsIgnoreCase("firefox")) {
			String Node = "http://hostjboss:5556/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.get("http://localhost:8080/MavenPrimefacesWebTest2/");
		} else if (browser.equalsIgnoreCase("chrome")) {
			String Node = "http://hostjboss:5556/wd/hub";
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			driver = new RemoteWebDriver(new URL(Node), cap);
			driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
		}
	}	
	
	@Test
	public void TestA_insert() throws InterruptedException{
		driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
		driver.findElement(By.id("form1:item")).sendKeys("cat");
		driver.findElement(By.id("form1:qty")).sendKeys("3");
		driver.findElement(By.id("form1:user_inp")).click();
		driver.findElement(By.xpath("//*[@id='form1:user_inp_1']")).click();
		driver.findElement(By.xpath("//*[@id='form1:editPanel']/tfoot/tr/td/input")).click();
		Thread.sleep(2000);
		element = driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt15']/div[1]"));
		AssertJUnit.assertNotNull(element);
	}
	
	@Test
	public void TestB_update() throws InterruptedException, AWTException{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt30']/span[1]")).click();
		element = driver.findElement(By.id("form1:itemTable:0:j_idt17"));
		element.clear();
		element.sendKeys("cat02");
		element = driver.findElement(By.id("form1:itemTable:0:j_idt22"));
		element.clear();
		element.sendKeys("5");
		driver.findElement(By.id("form1:itemTable:0:user_label")).click();
		driver.findElement(By.id("form1:itemTable:0:user_1")).click();
		driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt30']/span[2]")).click();
		Thread.sleep(1000);
		String name = driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt15']/div[1]")).getText();
		String qty = driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt20']/div[1]")).getText();
		String user = driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt25']/div[1]")).getText();
		assertTestUpdate(name, qty, user);		
	}

	@Test
	public void TestC_delete() throws InterruptedException, AWTException{
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt30']/span[1]")).click();
		driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt30']/span[3]")).click();
		driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
		element = driver.findElement(By.xpath("//*[@id='form1:itemTable_data']/tr/td"));
		String name = element.getText();
		assertTestDelete(name);	
	}
	
	@AfterTest
	public void afterTest(){
		driver.quit();
	}
	
	public WebDriverWait getWebDriverWait(){
		if(wait == null){
			wait = new WebDriverWait(driver, 10);
		}
		return wait;
	} 
}
