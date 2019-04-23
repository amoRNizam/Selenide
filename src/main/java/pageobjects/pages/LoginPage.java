package pageobjects.pages;

import org.openqa.selenium.By;
import org.testng.Assert;
import pageobjects.PageObject;
import pageobjects.conponents.Header;
import util.Context;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class LoginPage extends PageObject {

    public Header header = new Header();

    @Override
    public void validateIsExpectedPage() {
        Assert.assertTrue(url().contains("login"));
    }

    public void fillLoginDetails() throws Exception {
        if (!Context.get().getAccount().isInitialized()) {
            throw new Exception("Предоставленные учетные данные не действительны.");
        }

        $(By.id("frm-email")).shouldBe(visible).val(Context.get().getAccount().username);
        $(By.id("frm-password")).shouldBe(visible).val(Context.get().getAccount().password);
    }

    public HomePage clickLogin() {
        $(By.name("loginEmailPhone")).shouldBe(visible).click();

//        header.validateIsLoggedIn();

        return new HomePage();
    }
}
