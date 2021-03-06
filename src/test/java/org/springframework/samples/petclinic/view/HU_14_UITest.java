
package org.springframework.samples.petclinic.view;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class HU_14_UITest {

	@LocalServerPort
	private int				port;
	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		String pathToGeckoDriver = System.getenv("webdriver.gecko.driver");
		System.setProperty("webdriver.gecko.driver", pathToGeckoDriver + "\\geckodriver.exe");
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testPositive() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/myAnimalShelter')]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Add Appointment')]")).click();
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'28')]")).click();
		this.driver.findElement(By.id("cause")).click();
		this.driver.findElement(By.id("cause")).clear();
		this.driver.findElement(By.id("cause")).sendKeys("No anda bien");
		this.driver.findElement(By.name("vet_id")).click();
		new Select(this.driver.findElement(By.name("vet_id"))).selectByVisibleText("LindaDouglas");
		this.driver.findElement(By.name("vet_id")).click();
		this.driver.findElement(By.name("urgent")).click();
		new Select(this.driver.findElement(By.name("urgent"))).selectByVisibleText("Yes");
		this.driver.findElement(By.name("urgent")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//html")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a/strong")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();
		this.driver.findElement(By.xpath("//form[@action='/logout']")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("vet3");
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		Assert.assertEquals("Pichú Animales Shelter", this.driver.findElement(By.xpath("//table[@id='appointmentsTable']/tbody/tr/td")).getText());
		Assert.assertEquals("2021-03-28", this.driver.findElement(By.xpath("//table[@id='appointmentsTable']/tbody/tr/td[3]")).getText());

	}
	//En este test se ha tomado un escenario negativo en el que se deja un campo vacío
	//y por tanto falla, ya que el mencionado en el documento no puedes realizarlo en la web
	@Test
	public void testNegative() throws Exception {
		this.driver.get("http://localhost:" + this.port);
		this.driver.findElement(By.xpath("//a[contains(text(),'Login')]")).click();
		this.driver.findElement(By.xpath("//div")).click();
		this.driver.findElement(By.id("username")).click();
		this.driver.findElement(By.id("username")).clear();
		this.driver.findElement(By.id("username")).sendKeys("shelter1");
		this.driver.findElement(By.id("password")).clear();
		this.driver.findElement(By.id("password")).sendKeys("shelter1");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[4]/a/span[2]")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'Add Appointment')]")).click();
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//a[contains(text(),'25')]")).click();
		this.driver.findElement(By.name("urgent")).click();
		new Select(this.driver.findElement(By.name("urgent"))).selectByVisibleText("No");
		this.driver.findElement(By.name("urgent")).click();
		this.driver.findElement(By.name("vet_id")).click();
		this.driver.findElement(By.name("vet_id")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//body/div")).click();
		this.driver.findElement(By.xpath("//h2")).click();
		Assert.assertEquals("no puede estar vacío", this.driver.findElement(By.xpath("//form[@id='appointment']/div/div[2]/div/span[2]")).getText());
	}

	@AfterEach
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assert.fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}
