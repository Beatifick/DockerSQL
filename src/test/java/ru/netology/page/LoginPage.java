package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");

    // Универсальный метод для ввода данных
    private void fillLoginForm(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
    }

    public void validLogin(DataHelper.AuthInfo authInfo) {
        fillLoginForm(authInfo);
        loginButton.click();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        fillLoginForm(authInfo);
        loginButton.click();
    }

    // Для теста блокировки: просто нажать кнопку
    public void clickLoginButton() {
        loginButton.click();
    }

    // Проверка заблокированного пользователя
    public void shouldBeBlocked() {
        $("[data-test-id='error-notification']")
                .shouldBe(com.codeborne.selenide.Condition.visible)
                .shouldHave(com.codeborne.selenide.Condition.text("Ошибка! Пользователь заблокирован"));
    }
}
