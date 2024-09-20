package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.GoogleSearchPage;
import WebDriverUtils.SeleniumUtils;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleSearchTest {

	public static void main(String[] args) {
		// Setup WebDriver
		// System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
		
		WebDriver driver= new ChromeDriver();
		WebDriverManager.chromedriver().setup();
		
		GoogleSearchPage googlePage = new GoogleSearchPage(driver);

			
		
		
		try {
			SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
			googlePage.navigateToGoogle("https://www.google.com");
			    // Other test steps
			
			// Perform Search
			// googlePage.searchFor("OpenAI ChatGPT");
			googlePage.enterSearchTerm("Open AI chatGPT");

			// Verify Search Result
			//boolean isSearchResultDisplayed = seleniumUtils.isElementPresent(By.id("search"));

			/*
			 * if (isSearchResultDisplayed) {
			 * System.out.println("Search performed successfully!"); } else {
			 * System.out.println("Search failed!"); }
			 */
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the browser
			driver.quit();
		}
	}}
