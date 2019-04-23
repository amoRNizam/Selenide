package pageobjects.conponents;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pageobjects.Component;
import pageobjects.pages.LoginPage;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Header extends Component {

    @Override
    public void validateComponentIsAvailable() {
        $(".header-area").should(exist);
    }

    private SelenideElement loginLink = $(By.xpath(".//div[@class='header-main-area']//a[@href='/login']"));

    private SelenideElement userAvatar = $(By.xpath("//div[@class= 'header-main-area']" +
            "//span[@class='my-account-personal-photo-placeholder']"));

    public LoginPage clickLoginLink() {
        loginLink.shouldBe(visible).click();

        return new LoginPage();
    }

    public void validateIsLoggedIn() {
        loginLink.shouldNotBe(visible);
        userAvatar.shouldBe(visible);
    }

    public void validateIsLoggedOut() {
        loginLink.shouldBe(visible);
        userAvatar.shouldNotBe(visible);
    }
}
