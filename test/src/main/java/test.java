import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class test {


    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\lucky.sello\\Downloads\\geckodriver-v0.21.0-win64\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.toolsqa.com");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
