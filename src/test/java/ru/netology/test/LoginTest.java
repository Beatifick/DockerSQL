package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;
import ru.netology.page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @AfterAll
    public static void tearDown() {
        SQLHelper.clearDatabase();
    }

    @Test
    void shouldLoginWithVerificationCode() {
        var authInfo = DataHelper.getValidUser();
        LoginPage loginPage = open("http://localhost:9999", LoginPage.class);

        VerificationPage verificationPage = loginPage.validLogin(authInfo);

        verificationPage.shouldBeVisible();

        String codeFromDB = SQLHelper.getVerificationCode(authInfo.getLogin());
        var verificationCode = new DataHelper.VerificationCode(codeFromDB);

        verificationPage.verifyWithCode(verificationCode);

        DashboardPage dashboard = new DashboardPage();
        dashboard.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var user = DataHelper.getInvalidPasswordUser();

        for (int i = 0; i < 2; i++) {
            loginPage.invalidLogin(user);
            loginPage.shouldShowError(LoginPage.error);
        }

        loginPage.invalidLogin(user);
        loginPage.shouldShowError(LoginPage.blocked);
    }
}
