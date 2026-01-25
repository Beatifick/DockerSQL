package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @Test
    void shouldLoginUsingCodeFromDatabase() {
        var authInfo = DataHelper.getValidUser();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(authInfo);

        var verificationPage = new VerificationPage();
        verificationPage.shouldBeVisible();

        var verificationCode = DataHelper.getVerificationCodeFromDB();
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        dashboardPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() throws InterruptedException {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(DataHelper.getInvalidPasswordUser());

        // три нажатия на кнопку с задержкой 1 сек
        for (int i = 0; i < 3; i++) {
            loginPage.clickLoginButton();
        }

        loginPage.shouldBeBlocked();
    }
}
