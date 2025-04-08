package OCRIP_Unifie;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

import static java.lang.System.setProperty;

public class Test_OCRIP_Orange {

    public static void main(String[] args) throws InterruptedException, IOException {
        setProperty("webdriver.chrome.driver", "C:\\Users\\t.thabet\\Desktop\\Automation\\SeleniumWebdriverJavaEspaceCollectivité\\EspaceCollectivite\\chromedriver.exe");
        Thread.sleep(2000);

        WebDriver driver = new ChromeDriver();

        driver.get("https://espace-rip.orange-business.com/");
        Thread.sleep(2000);

        driver.manage().window().maximize();









}
}
