package selenium.web.test;

import static org.testng.AssertJUnit.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Function;

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
			driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
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
		element = getFluentWait().until(getFinderByLocatorXpath("//*[@id='form1:itemTable:0:j_idt15']/div[1]"));
		AssertJUnit.assertNotNull(element);
	}
	
	@Test
	public void TestB_update() throws InterruptedException, AWTException{
		element = getFluentWait().until(getFinderByLocatorXpath("//*[@id='form1:itemTable:0:j_idt30']/span[1]"));
		element.click();
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
		element = getFluentWait().until(getFinderByLocatorXpath("//*[@id='form1:itemTable:0:j_idt30']/span[1]"));
		element.click();
		driver.findElement(By.xpath("//*[@id='form1:itemTable:0:j_idt30']/span[3]")).click();
		driver.get("http://hostjboss:8080/MavenPrimefacesWebTest2/");
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
	
	public FluentWaitElementFinder fluentFinder = new FluentWaitElementFinder(null, driver);
	public FluentWaitElementFinder getFinderByLocatorXpath(String idOfComponent) {
		fluentFinder.setLocator(By.xpath(idOfComponent));
		return fluentFinder;
	}
	public class FluentWaitElementFinder implements Function<WebDriver,WebElement>{
		private By locator;
		private WebDriver driver;
		private int count = 0;
		public FluentWaitElementFinder() {
			super();
		}

		public FluentWaitElementFinder(By locator,WebDriver driver) {
			super();
			this.locator = locator;
			this.driver = driver;
		}

//		@Override
		public WebElement apply(WebDriver driver) {
			return driver.findElement(this.locator);
		}

		public By getLocator() {
			return locator;
		}

		public void setLocator(By locator) {
			this.locator = locator;
		}

		public WebDriver getDriver() {
			return driver;
		}

		public void setDriver(WebDriver driver) {
			this.driver = driver;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}
	@SuppressWarnings("rawtypes")
	public Wait<WebDriver> getFluentWait(){
		@SuppressWarnings("unchecked")
		Wait<WebDriver> fluentWait = new FluentWait(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS).ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return fluentWait;
	}
}
