package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    @AfterAll
    void tearDown() {
        SQLHelper.clearDatabase(); // Очистка БД после всех тестов
    }

    @Test
    void shouldLoginUsingCodeFromDatabase() {
        var authInfo = DataHelper.getValidUser();
        var loginPage = open("http://localhost:9999", LoginPage.class);

        var verificationPage = loginPage.validLogin(authInfo);

        // Ждем появления страницы верификации
        sleep(1000);

        var verificationCode = SQLHelper.getVerificationCode(authInfo.getLogin());
        var dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode(verificationCode));

        dashboardPage.shouldBeVisible();
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() {
        var loginPage = open("http://localhost:9999", LoginPage.class);

        // Вводим неверный пароль один раз
        loginPage.invalidLogin(DataHelper.getInvalidPasswordUser());

        // Нажимаем кнопку логина еще 2 раза с задержкой
        for (int i = 0; i < 2; i++) {
            sleep(1000);
            loginPage.clickLoginButton();
        }

        // Проверяем блокировку через текст уведомления
        $("[data-test-id='error-notification']").shouldBe(visible)
                .shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}
