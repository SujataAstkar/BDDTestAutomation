package com.visionit.automation.stepdefs;

import java.util.Iterator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.visionit.automation.PageObjects.LandingPageObjects;
import com.visionit.automation_core.WebDriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class stepDefs {
    private static final Logger logger = LogManager.getLogger(stepDefs.class);

    WebDriver driver;
    String base_url = "https://amazon.in";
    int implicit_wait_timeout_in_sec = 20;
    Scenario scn;
    LandingPageObjects landingPageObjects;

    @Before
	public void setup(Scenario scn) throws Exception
	{
    	this.scn=scn;
        String browserName = WebDriverFactory.getBrowserName();
        driver = WebDriverFactory.getWebDriverForBrowser(browserName);
        logger.info("Browser invoked.");
        
    	//driver = new ChromeDriver();
	    driver.manage().window().maximize();
	    scn.log("browser is maximized");
	    driver.manage().timeouts().implicitlyWait(implicit_wait_timeout_in_sec, TimeUnit.SECONDS);
	    logger.info("browser is maximized");
	    landingPageObjects = new LandingPageObjects(driver,scn);

	}
    @After(order=1)
    public void teardown()
    { 
    	WebDriverFactory.quitDriver();
    	scn.log("browser is clossed");
    	
    }
    @After(order=2) // this will execute first, higher the number, sooner it executes
    public void takeScreenShot(Scenario s) {
      if (s.isFailed()) {
          TakesScreenshot scrnShot = (TakesScreenshot)driver;
          byte[] data = scrnShot.getScreenshotAs(OutputType.BYTES);
          scn.attach(data, "image/png","Failed Step Name: " + s.getName());
      }else{
          scn.log("Test case is passed, no screen shot captured");
      }
    }



@Given("User opened browser")
public void user_opened_browser() {
	

//    driver = new ChromeDriver();
//    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(implicit_wait_timeout_in_sec, TimeUnit.SECONDS);

    }

@Given("User navigated to the home application url")
public void user_navigated_to_the_home_application_url() {

	WebDriverFactory.navigateToTheUrl(base_url);
//    String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
//    String actual =driver.getTitle();
//    Assert.assertEquals("Page Title validation",expected,actual);
//    scn.log("page title validation is successful");

	landingPageObjects.ValidatingLangingPageTitle(base_url);
}
@When("User Search for product {string}")
public void user_search_for_product(String productName) {
	landingPageObjects.searchproduct(productName);

//    //Wait and Search for product
//    WebDriverWait webDriverWait = new WebDriverWait(driver,20);
//    WebElement elementSearchBox = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
//
//    elementSearchBox.sendKeys(productName);
//    driver.findElement(By.xpath("//input[@value='Go']")).click();

 
}
@Then("Search Result page is displayed with title contains {string}")
public void search_result_page_is_displayed_with_title_contains(String productnameinTitle) {
    
 //Wait for titile
WebDriverWait webDriverWait1 = new WebDriverWait(driver,20);
webDriverWait1.until(ExpectedConditions.titleIs("Amazon.in : "+productnameinTitle));

//Assertion for Page Title
Assert.assertEquals("Page Title validation","Amazon.in : "+productnameinTitle, driver.getTitle());
scn.log("page title validation is successful .");
}


@Then("Browser is clossed")
public void browser_is_clossed() {
  
    //driver.quit();
}
@When("User click on any product")

    public void user_click_on_any_product() {
        //listOfProducts will have all the links displayed in the search box
        List<WebElement> listOfProducts = driver.findElements(By.xpath("((//div[@data-component-type='s-search-result'])[1]//a[@target='_blank'])[2]"));

        //But as this step asks click on any link, we can choose to click on Index 0 of the list
        listOfProducts.get(0).click();
    
    
}

@Then("Product Description is displayed in new tab")
public void product_description_is_displayed_in_new_tab() {
    //As product description click will open new tab, we need to switch the driver to the new tab
    //If you do not switch, you can not access the new tab html elements
    //This is how you do it
    Set<String> handles = driver.getWindowHandles(); // get all the open windows
    Iterator<String> it = handles.iterator(); // get the iterator to iterate the elements in set
    String original = it.next();//gives the parent window id
    String prodDescp = it.next();//gives the child window id

    driver.switchTo().window(prodDescp); // switch to product Descp

    //Now driver can access new driver window, but can not access the orignal tab
    //Check product title is displayed
    WebElement productTitle = driver.findElement(By.id("productTitle"));
    Assert.assertEquals("Product Title",true,productTitle.isDisplayed());
    scn.log("new tab is open");

    //WebElement addToCartButton = driver.findElement(By.xpath("//button[@title='Add to Shopping Cart']"));
    //Assert.assertEquals("Product Title",true,addToCartButton.isDisplayed());

    //Switch back to the Original Window, however no other operation to be done
    driver.switchTo().window(original);
    

   
}






}
