package betBot.betingBot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import System.Bet;
import System.BotSystem;

/**
 * Hello world!
 *
 */
public class App {
	private static String username;
	private static String password;
	
	public static void makeBet(boolean b1, boolean b2, boolean b3, BotSystem b) throws InterruptedException {
		// TODO Auto-generated method stub
				// setting the driver executable
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\Bruno\\Downloads\\selenium\\chromedriver.exe");

				// Initiating your chromedriver
				WebDriver driver = new ChromeDriver();

				// Applied wait time
				// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				// maximize window
				driver.manage().window().maximize();

				// open browser with desried URL
				driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
				driver.get("https://www.betclic.pt/sport/");
				driver.findElement(By.id("login-username")).sendKeys(username);
				driver.findElement(By.id("login-password")).sendKeys(password);
				driver.findElement(By.id("login-submit")).submit();
				TimeUnit.SECONDS.sleep(5);
				for (int i = 0; i < 3; i++) {
					if((i == 0 && b1) || (i == 1 && b2) || (i == 2 && b3)) {
						ArrayList<Bet> bet = b.getBet(i);
						Iterator<Bet> it = bet.iterator();
						while (it.hasNext()) {
							TimeUnit.SECONDS.sleep(1);
							Bet j = it.next();
							driver.get(j.getUrl());
							List<WebElement> odds = driver.findElements(By.className("odd-button"));
							odds.get(j.getIndexToBet()).click();
						}
						TimeUnit.SECONDS.sleep(1);
						driver.findElement(By.id("MultipleStake")).sendKeys("0.50");
						TimeUnit.SECONDS.sleep(3);
						driver.findElement(By.id("PlaceBet")).click();
					}
				}
				//driver.close();
	}
	public static void setLogin(String um, String pw) {
		username = um;
		password = pw;
	}
}
