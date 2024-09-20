package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import WebDriverUtils.SeleniumUtils;

public class GoogleSearchPage {

	private WebDriver driver;

	public static final By searchBar = By.name("q");
	public static final By searchButton = By.name("btnK");
	public static final By feelingLuckyButton = By.name("btnI");


	public GoogleSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	

	SeleniumUtils seleniumUtils = new SeleniumUtils(driver);
	public void navigateToGoogle(String url) {

		seleniumUtils.openUrl(url);
	}

	public void enterSearchTerm(String searchTerm) {

		seleniumUtils.sendKeys(searchBar, searchTerm);

		// driver.findElement(searchBar).sendKeys(searchTerm);
	}

	public void clickSearchButton() {
		// driver.findElement(searchButton).click();
		seleniumUtils.click(searchButton);
	}

	public String getTitle() {

		return seleniumUtils.getPageTitle();
	}

	public void clickFeelingLuckyButton() {
		// driver.findElement(feelingLuckyButton).click();
		seleniumUtils.click(feelingLuckyButton);

	}
}
