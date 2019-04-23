package pageobjects.pages;

import org.openqa.selenium.By;
import pageobjects.PageObject;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class HomePage extends PageObject {

    @Override
    public void validateIsExpectedPage() {
        $(By.className("home")).should(exist);
    }
}
