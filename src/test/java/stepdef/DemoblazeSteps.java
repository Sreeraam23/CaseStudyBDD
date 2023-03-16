package stepdef;

import java.awt.AWTException; 
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import javax.swing.Action;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DemoblazeSteps {
	static WebDriver driver;
	static WebDriverWait wait;
	static Alert alert;
	static int prosize;
	static Robot robot;

	@BeforeAll
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	    ChromeOptions options = new ChromeOptions();
	    options.addArguments("--remote-allow-origins=*");
	    driver = new ChromeDriver(options);
	    driver.manage().window().maximize();
	    driver.get("https://www.demoblaze.com/index.html");
	}

	@Given("User is on Launch Page")
	public void user_is_on_launch_page() {
	    Assert.assertEquals(driver.getTitle(), "STORE");
	}

	@When("User log in the page")
	public void user_log_in_the_page() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofMinutes(1));
	    driver.findElement(By.id("login2")).click();
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
	    WebElement login = driver.findElement(By.id("loginusername"));
	    login.sendKeys("Tester12345");
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("loginpassword")));
	    driver.findElement(By.id("loginpassword")).sendKeys("testing");
	    driver.findElement(By.xpath("//a[text()='Welcome Tester12345']")).click();
	    wait.until(ExpectedConditions.invisibilityOf(login));
	    boolean isdisp = login.isDisplayed();
	    Assert.assertTrue(isdisp);
	}

	@Then("Display the home page")
	public void display_the_home_page() {
	    driver.findElement(By.xpath("(//li/a)[1]")).click(); //home btn
	    WebElement home = driver.findElement(By.xpath("(//a/span)[1]"));
	    boolean ispre = home.isDisplayed();
	    Assert.assertTrue(ispre);
	}

	@When("Add an item to cart {string}")
	public void add_an_item_to_cart(String string) throws InterruptedException {
		driver.findElement(By.xpath("(//li//a)[1]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//li//a)[1]")));
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
		WebElement product = driver.findElement(By.xpath("//a[text()='"+string+"']"));
		wait.until(ExpectedConditions.visibilityOf(product));
		wait.until(ExpectedConditions.elementToBeClickable(product));
		product.click();
		driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Thread.sleep(3000);
		
	}
	
	
	@Then("It must be added to cart")
	public void it_must_be_added_to_cart() {
		driver.findElement(By.xpath("//a[text()='Cart']")).click();
		List<WebElement> products = driver.findElements(By.xpath("//tr/td[2]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(products));
		prosize = products.size();
		boolean scheck = prosize>0;
		Assert.assertTrue(scheck);
		
	}
	
	@Given("Cart should display items")
	public void cart_should_display_items() {
		List<WebElement> prolist = driver.findElements(By.xpath("//td"));
		boolean list = driver.findElement(By.xpath("(//td/a)[1]")).isDisplayed();
		Assert.assertTrue(list);
	}
	@When("User delete an item")
	public void user_delete_an_item() throws InterruptedException {
	    driver.findElement(By.xpath("(//td/a)[1]")).click();
	    Thread.sleep(5000);
	}
	@Then("Item should be deleted")
	public void item_should_be_deleted() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		List<WebElement> currentpro = driver.findElements(By.xpath("//tr/td[2]"));
		int currentsize = currentpro.size();
		boolean chk = currentsize != prosize;
		Assert.assertTrue(chk);

	}
	@When("User place order")
	public void user_place_order() throws InterruptedException {
		WebElement placeOrdBtn = driver.findElement(By.xpath("//button[text()='Place Order']"));
		placeOrdBtn.click();		
		
	}
	@Then("Items should be purchased")
	public void items_should_be_purchased() throws AWTException, InterruptedException {	
		Thread.sleep(5000);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		WebElement name = driver.findElement((By.id("name")));		
		name.sendKeys("Tester12345");
		WebElement country = driver.findElement(By.id("country"));
		country.sendKeys("xxxx");
		WebElement city = driver.findElement(By.id("city"));
		city.sendKeys("yyyy");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)");
		WebElement card = driver.findElement(By.id("card"));
		card.sendKeys("00000001");
		WebElement month = driver.findElement(By.id("month"));
		month.sendKeys("March");
		WebElement year = driver.findElement(By.id("year"));
		year.sendKeys("2023");	
		driver.findElement(By.xpath("//button[text()='Purchase']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']"))); 				
		WebElement successMsg = driver.findElement(By.xpath("//h2[text()='Thank you for your purchase!']"));
		wait.until(ExpectedConditions.visibilityOf(successMsg));
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='OK']")));
	}
}
	
