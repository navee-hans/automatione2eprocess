import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutomationSetup {

    private static AutomationSetup instance;
    private static ThreadLocal<WebDriver> driverManager = new ThreadLocal<>();
    private static WebDriver driver;

    private AutomationSetup(){}

    private void initBrowser(String browser) {
        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                String headless = System.getProperty("headless", "false");
                if (headless.equalsIgnoreCase("true")) {
                    options.addArguments("--headless");
                }
                driverManager.set(new ChromeDriver(options));
                break;

            default:
                new IllegalArgumentException("invalid browser name: " + browser);

        }
    }

    public static AutomationSetup getInstance(String browser){
        if(instance==null){
            synchronized (AutomationSetup.class){
                if(instance==null)
                {
                    instance = new AutomationSetup();
                }
            }
        }

        if(driverManager.get()==null){
            instance.initBrowser(browser);
        }
           return instance;
        }

        public WebDriver getDriver(){
        return driverManager.get();
        }

        @BeforeMethod
        public void setup(){
        driver = AutomationSetup.getInstance("chrome").getDriver();
        driver.get("https://google.com");
        }

        @Test
        public void test1(){
            Assert.assertEquals(driver.getTitle(), "Google","not found");
        }

        @Test
        public void test2(){
            Assert.assertEquals(driver.getTitle(), "Google","not found");
        }

        @AfterMethod
        public void killdriver(){
        if(driverManager.get()!=null)
        {
            driverManager.get().quit();
            driverManager.remove();
        }
    }
}
