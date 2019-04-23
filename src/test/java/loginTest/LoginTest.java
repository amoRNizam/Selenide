package loginTest;

import org.testng.annotations.Test;
import pageobjects.conponents.Header;
import pageobjects.pages.LoginPage;
import util.WebTestRunner;

public class LoginTest extends WebTestRunner {

    @Test
    public void userCanSearch() throws Exception {
        Header header = new Header();
        LoginPage loginPage = new LoginPage();

        /* Перейти на страницу авторизации*/
        header.clickLoginLink();

        /* Заполнить данными форму авторизации */
        loginPage.fillLoginDetails();

        /* Нажать на кнопку завершения авторизации*/
        loginPage.clickLogin();

        /* Проверить, что авторизация удалась*/
        header.validateIsLoggedIn();
    }
}
