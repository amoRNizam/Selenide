package util;

import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.open;

public class WebTestRunner {

    @BeforeClass
    public void setUp() {
        Context.initialize();
        open("/");
    }
}
