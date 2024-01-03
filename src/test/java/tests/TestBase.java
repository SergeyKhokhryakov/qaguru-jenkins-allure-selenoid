package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase {
    private final static String BASE_URL = "https://demoqa.com";
    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = BASE_URL;
        Configuration.startMaximized = true;
        Configuration.pageLoadTimeout = 60000;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("enableVNC", true);

        Configuration.browserCapabilities = capabilities;
        String s = "s",
                password = System.getProperty("psw");
        if (password == null) {
            password = "";
            s = "";
        }
        Configuration.remote = "http" + s + "://" + password + System.getProperty("selenoidRemoteAddress", "selenoid.autotests.cloud/wd/hub");
//        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub/";
//        Configuration.remote = System.getProperty("remote_driver_url", "http://46.101.108.21:4444/wd/hub/");
    }

    @BeforeEach
    void setupEach(){
//        open("https://demoqa.com");
    }

    @AfterEach
    public void tearDown() {
//        String sessionId = getSessionId();

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        closeWebDriver();

//        Attach.addVideo(sessionId);
    }

    public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

}




