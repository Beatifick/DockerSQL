package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.codeborne.selenide.Selenide.$;

public class LoginTest {

    @Test
    void shouldLoginUsingCodeFromDatabase() {
        var authInfo = DataHelper.getValidUser();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(authInfo);

        var verificationCode = DataHelper.getVerificationCodeFromDB();
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(verificationCode);

        // проверка успешного входа
        $("[data-test-id='dashboard']").shouldBe(visible);
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() {
        var loginPage = new LoginPage();
        for (int i = 0; i < 3; i++) {
            open("http://localhost:9999"); // открываем страницу заново
            loginPage.invalidLogin(DataHelper.getInvalidPasswordUser());
        }
        // после третьей попытки проверяем блокировку
        var errorMessage = $("[data-test-id='error-notification']").getText();
        assertEquals("Ошибка! Пользователь заблокирован", errorMessage);
    }
}
