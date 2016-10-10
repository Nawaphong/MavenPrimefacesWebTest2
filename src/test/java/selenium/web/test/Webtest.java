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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Webtest {
	protected static RemoteWebDriver driver;
	protected static WebDriverWait wait;
	protected static WebElement element = null;
	protected static JavascriptExecutor jsExecutor;
	public static void assertTestUpdate(String name,String qty,String user){
		assertEquals("cat02", name);
		assertEquals("5", qty);
		assertEquals("name", user);
	}
	public static void assertTestDelete(String name){
		AssertJUnit.assertTrue(!"cat02".equals(name));
	}

	@BeforeClass
	public void initWebDriver() throws MalformedURLException{
		String Node = "http://hostjboss:5556/wd/hub";
		DesiredCapabilities cap = DesiredCapabilities.firefox();
//		cap.setCapability(FirefoxDriver.PROFILE);
		cap.setBrowserName("chrome");
		driver = new RemoteWebDriver(new URL(Node), cap);
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//		driver = new RemoteWebDriver(new URL("http://10.0.75.1:4444/wd/hub"), capabilities);
//		driver.manage().window().maximize();
	}	
	
	@Test
	public void TestA_insert() throws InterruptedException{
		driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
		driver.findElement(By.id("form1:item")).sendKeys("cat");
		driver.findElement(By.id("form1:qty")).sendKeys("3");
		driver.findElement(By.id("form1:user_inp")).click();
		driver.findElement(By.xpath("//*[@id='form1:user_inp_1']")).click();
		driver.findElement(By.xpath("//*[@id='form1:editPanel']/tfoot/tr/td/input")).click();
		element = getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form1:itemTable:0:j_idt15']/div[1]")));
		AssertJUnit.assertNotNull(element);
	}
	
	@Test
	public void TestB_update() throws InterruptedException, AWTException{
		Thread.sleep(2000);
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
		Thread.sleep(500);
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
		driver.get("http://localhost:8080/MavenPrimefacesWebTest2/");
		element = driver.findElement(By.xpath("//*[@id='form1:itemTable_data']/tr/td"));
		String name = element.getText();
		assertTestDelete(name);	
	}
	
	public WebDriverWait getWebDriverWait(){
		if(wait == null){
			wait = new WebDriverWait(driver, 10);
		}
		return wait;
	} 
}
