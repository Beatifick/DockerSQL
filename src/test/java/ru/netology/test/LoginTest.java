package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @Test
    void shouldLoginUsingCodeFromDatabase() {
        var authInfo = DataHelper.getValidUser();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(authInfo);

        var verificationCode = SQLHelper.getVerificationCode(); // используем SQLHelper
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(verificationCode);

        // проверка успешного входа через доменный метод страницы
        var dashboardPage = new DashboardPage();
        dashboardPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() throws InterruptedException {
        var loginPage = open("http://localhost:9999", LoginPage.class);

        // Вводим данные один раз
        loginPage.invalidLogin(DataHelper.getInvalidPasswordUser());

        // Три раза нажимаем кнопку с паузой 1 секунда
        for (int i = 0; i < 3; i++) {
            loginPage.clickLoginButton(); // создадим отдельный метод в LoginPage
        }

        // проверка блокировки через доменный метод
        loginPage.shouldBeBlocked();
    }
}
