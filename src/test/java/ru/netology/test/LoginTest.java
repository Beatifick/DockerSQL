package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    @AfterAll
    void tearDown() {
        SQLHelper.clearDatabase();
    }

    @Test
    void shouldGoToVerificationPageAfterLogin() {
        var authInfo = DataHelper.getValidUser();
        var loginPage = open("http://localhost:9999", LoginPage.class);

        var verificationPage = loginPage.validLogin(authInfo);

        verificationPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var user = DataHelper.getInvalidPasswordUser();

        for (int i = 0; i < 2; i++) {
            loginPage.invalidLogin(user);
            loginPage.shouldShowWrongPasswordError();
        }

        loginPage.invalidLogin(user);
        loginPage.shouldShowBlockedUserError();
    }

}
