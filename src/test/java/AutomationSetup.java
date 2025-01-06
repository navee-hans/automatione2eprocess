import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class AutomationSetup {

    public WebDriver driver;

    @BeforeClass
    public void startBrowser(){
        driver = new ChromeDriver();
    }


    @Test
    public void test3(){

        driver.get("https://google.com");
        String title = driver.getTitle();
        Assert.assertEquals(title,"Google","title is invalid");

    }

    @Test
    public void test1(){
        driver.get("https://google.com");
        String title = driver.getTitle();
        Assert.assertEquals(title,"Google","title is invalid");
    }


    @Test
    public void test2(){
        driver.get("https://google.com");
        String title = driver.getTitle();
        Assert.assertEquals(title,"Google","title is invalid");
    }

    @AfterClass
    public void killChromeDriver(){
        driver.quit();
        driver = null;
    }
}
