package com.Practice;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.CommonMethods;

public class BhtTest extends CommonMethods {
	
	WebElement element;
	

	@BeforeClass
	public void setUp() {
		setUpDriver("chrome",
				"http://secure.smartbearsoftware.com/samples/TestComplete11/WebOrders/Login.aspx?ReturnUrl=%2fsamples%2fTestComplete11%2fWebOrders%2fDefault.aspx");
	}
	
	
	@DataProvider()
	public Object[][] userOneData(){
		
		Object[][]data=new Object[1][10];
		
		data[0][0]="FamilyAlbum";
		data[0][1]="20";
		data[0][2]="Bob";
		data[0][3]="2307 39th Place";
		data[0][4]="Queens";
		data[0][5]="MA";
		data[0][6]="11104";
		data[0][7]="4455667789903456";
		data[0][8]="12/22";
		data[0][9]="Visa";
			
		return data;
		
		
	}
	
	@DataProvider()
	public Object[][] userTwoData(){
		
		Object[][]data=new Object[1][10];
	data[0][0]="MyMoney";
	data[0][1]="5";
	data[0][2]="Moss";
	data[0][3]="3035 47 ave";
	data[0][4]="Woodside";
	data[0][5]="CA";
	data[0][6]="11114";
	data[0][7]="4455667783303456";
	data[0][8]="10/22";
	data[0][9]="MasterCard";
	return data;
	
	
	}
	@Test
	public void logIn() {

		sendText(findByCSS("input#ctl00_MainContent_username"), "Tester");
		sendText(findByCSS("input#ctl00_MainContent_password"), "test");
		click(findByCSS("input#ctl00_MainContent_login_button"));

		Assert.assertTrue(findByCSS("div.login_info").isDisplayed());

	}

	@Test(priority = 1,dataProvider="userOneData")
	public void creatingUserOne(String product,String quantiti,String firstName,String address,String city,String state,String zip,String ccCard,String ccExp,String ccType) {

		click(driver.findElement(By.linkText("Order")));
		
		 
		
		selectValueFromDD(driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct")),product);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")),quantiti);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")),firstName);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")),address);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")), city);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")), state);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")),zip);

		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")), ccCard);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")), ccExp);
		List <WebElement> radio=driver.findElements(By.xpath("//input[@type='radio']"));
		 radioButtonComparingValue(radio, ccType);
		click(driver.findElement(By.linkText("Process")));

	}

	@Test(priority=2,dependsOnMethods = "creatingUserOne",dataProvider="userTwoData")
	public void creatingUserTwo(String product,String quantiti,String firstName,String address,String city,String state,String zip,String creditcard,String expiration,String cardType) {

		click(driver.findElement(By.linkText("Order")));
		
		 
		
		selectValueFromDD(driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct")),product);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")),quantiti);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")),firstName);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")),address);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")), city);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")), state);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")),zip);

		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")), creditcard);
		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")), expiration);
		List <WebElement> radio=driver.findElements(By.xpath("//input[@type='radio']"));
		 radioButtonComparingValue(radio, cardType);
		click(driver.findElement(By.linkText("Process")));

	}

	@Test(priority = 3,dependsOnMethods = "creatingUserOne")
	public void userOneVerification() {

		click(driver.findElement(By.linkText("View all orders")));
		
		List<WebElement> row = driver.findElements(By.xpath("//table[@class='SampleTable']/tbody/tr"));
		
		for (int i = 1; i < row.size(); i++) {
			if (driver.findElement(By.xpath("//table[@class='SampleTable']/tbody/tr["+i+"]")).getText()
					.contains("Bob")) {
								
				click(driver.findElement(By.xpath("//table[@class='SampleTable']/tbody/tr["+i+"]/td[13]")));
				break;
			}
		}

		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")), "NY");
		
		Assert.assertTrue(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).isDisplayed());
				
		click(driver.findElement(By.linkText("Update")));
	}

	
	@Test(priority = 4,dependsOnMethods = "creatingUserTwo")
	public void userTwoVerification() {

		click(driver.findElement(By.linkText("View all orders")));

		List<WebElement> row = driver.findElements(By.xpath("//table[@class='SampleTable']/tbody/tr"));
		for (int i = 1; i < row.size(); i++) {

			if (driver.findElement(By.xpath("//table[@class='SampleTable']/tbody/tr[" + i + "]")).getText()
					.contains("Moss")) {
				click(driver.findElement(By.xpath("//table[@class='SampleTable']/tbody/tr["+i+"]/td[13]")));
				break;
			}
		}

		sendText(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")), "Santa Monica");
		
		Assert.assertTrue(driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).isDisplayed());

		
		click(driver.findElement(By.linkText("Update")));
	}

	@Test(priority = 5,dependsOnMethods = "creatingUserOne")
	public void validationOfTheFirstUser() {
		
		click(driver.findElement(By.linkText("View all orders")));

		List<WebElement> cell = driver.findElements(By.xpath("//table[@class='SampleTable']/tbody/tr/td"));
		for(WebElement run:cell) {
			String value=run.getText();
			if(value.equals("Bob")) {
				
				Assert.assertEquals(value, "Bob", "Bob is present");
				break;
			}}
			
		}
		
	
		@Test(priority = 6,dependsOnMethods = "creatingUserTwo")
		public void validationOfTheSecondUser() {
			
			click(driver.findElement(By.linkText("View all orders")));

			List<WebElement> cell = driver.findElements(By.xpath("//table[@class='SampleTable']/tbody/tr/td"));
			for(WebElement run:cell) {
				String value=run.getText();
				if(value.equals("Moss")) {
					
					Assert.assertEquals(value, "Moss", "Moss is present");
					break;
				}}
		
	

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
