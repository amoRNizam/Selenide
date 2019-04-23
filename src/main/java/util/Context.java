package util;

import datamodels.Account;
import org.aeonbits.owner.ConfigFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Context {

    private static Context context = null;

    private Configuration configuration = null;

    private String sessionIdentifier = null;

    private Account account = null;

    protected Context() {
        configuration = ConfigFactory.create(Configuration.class, System.getProperties(), System.getenv());

        resetSessionIdentifier(null);
    }

    public static void initialize() {
        get().initializeSelenide();
        get().account = new Account();

        if (!Context.get().getConfiguration().projectDebug()) {
            // Деактивировать выход java.util.logging
            Logger logger = Logger.getLogger("");
            logger.setLevel(Level.OFF);
            logger.removeHandler(logger.getHandlers()[0]);
            logger.setUseParentHandlers(false);

            // Отключить вывод chromedriver
            if (get().getConfiguration().selenideBrowser().toLowerCase().equals("chrome")) {
                System.setProperty("webdriver.chrome.silentOutput", "true");
            }
        } else {
            // Настройка вывода журнала
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tT %4$s %5$s%6$s%n");
        }
    }

    public static Context get() {
        if (context == null) {
            context = new Context();
        }

        return context;
    }

    protected void initializeSelenide() {

        // URL
        com.codeborne.selenide.Configuration.baseUrl = configuration.siteUrl();

        // Браузер
        com.codeborne.selenide.Configuration.browser = configuration.selenideBrowser();

        // Режим запуска браузера (автономный режим)
        com.codeborne.selenide.Configuration.headless = configuration.selenideHeadless();

        // Оставлять ли открытым окно браузера после выполнения тестов
        com.codeborne.selenide.Configuration.holdBrowserOpen = configuration.selenideHoldBrowserOpen();

        // Окно браузера максимально развернуто при запуске.
        com.codeborne.selenide.Configuration.startMaximized = configuration.selenideStartMaximized();

        // Делать ли скриншот при неудачных тестах
        com.codeborne.selenide.Configuration.screenshots = configuration.selenideRecordScreenshots();

        // Расположение каталога, в котором будут сохранены скриншоты/отчеты из Selenide
        com.codeborne.selenide.Configuration.reportsFolder = configuration.selenideReportsFolder() + "/" + sessionIdentifier;

        // Обработка страницы после запуска события load
        // - normal - > возврат после события загрузки
        // - eager - > возврат после DOMContentLoaded
        // - none - > немедленно вернуться
        com.codeborne.selenide.Configuration.pageLoadStrategy = configuration.selenidePageLoadStrategy();

        // Сохранять ли исходный код страницы при неудачных тестах.
        com.codeborne.selenide.Configuration.savePageSource = configuration.selenideSavePageSource();

        // Тайм-аут в миллисекундах для неудачного теста, если условия все еще не выполнены.
        com.codeborne.selenide.Configuration.timeout = configuration.selenideTimeout();

        // Таймаут для поиска коллекций
        com.codeborne.selenide.Configuration.collectionsTimeout = configuration.selenideTimeout() * 2;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getSessionIdentifier() {
        return sessionIdentifier;
    }

    public void resetSessionIdentifier(String identifier) {
        if (identifier == null) {
            sessionIdentifier = "" + System.currentTimeMillis();
        } else {
            sessionIdentifier = identifier;
        }
    }

    public void setAccount(String username, String password) {
        account.username = username;
        account.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public String getWorkingDirectoryPath() {
        return Context.get().getConfiguration().projectDataDirectory() + "/" + Context.get().getSessionIdentifier() + "/";
    }

    public String getWorkingFilePath() {
        return getWorkingDirectoryPath() + Context.get().getConfiguration().projectDataFile();
    }
}
