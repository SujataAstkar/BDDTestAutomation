package com.visionit.automation.PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Scenario;


public class LandingPageObjects {
	private static final Logger Logger =LogManager.getLogger(LandingPageObjects.class);
	
	private WebDriver driver;
    Scenario scn;
	
	public LandingPageObjects(WebDriver driver,Scenario scn)
	{
		this.driver=driver;
		this.scn=scn;
	}
	
	
	private By elementSearchBoxElement=By.id("twotabsearchtextbox");
	private By clickonsearchBTNElement=By.xpath("//input[@value='Go']");
	
	public void searchproduct(String productName)
	{
		WebDriverWait webDriverWait=new WebDriverWait(driver,20);
		WebElement elementserchbox=webDriverWait.until(ExpectedConditions.elementToBeClickable(elementSearchBoxElement));
		elementserchbox.sendKeys(productName);
		Logger.info("sending a keys as "+productName);
		driver.findElement(clickonsearchBTNElement).click();
		Logger.info("clicked on the product");
		
	
	
	
//	WebDriverWait webDriverWait = new WebDriverWait(driver,20);
//    WebElement elementSearchBox = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("twotabsearchtextbox")));
//
//    elementSearchBox.sendKeys(productName);
//    driver.findElement(By.xpath("//input[@value='Go']")).click();
//
	}
	public void ValidatingLangingPageTitle(String base_url)
	{
		
	String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
    String actual =driver.getTitle();
    Assert.assertEquals("Page Title validation",expected,actual);
    Logger.info ("base_url" +base_url +"Validatiom is done");
    scn.log("page title validation is successful");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
