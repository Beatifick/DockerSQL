package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");


    private final SelenideElement errorNotification =
            $("[data-test-id='error-notification']");

    public void shouldShowBlockedUserError() {
        errorNotification
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    public void shouldShowWrongPasswordError() {
        $("[data-test-id='error-notification']")
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    private void enterCredentials(DataHelper.AuthInfo authInfo) {
        loginField.clear();
        loginField.setValue(authInfo.getLogin());
        passwordField.clear();
        passwordField.setValue(authInfo.getPassword());
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        enterCredentials(authInfo);
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        enterCredentials(authInfo);
        loginButton.click();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
