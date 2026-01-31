package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginField =
            $("[data-test-id='login'] input");
    private final SelenideElement passwordField =
            $("[data-test-id='password'] input");
    private final SelenideElement loginButton =
            $("[data-test-id='action-login']");
    private final SelenideElement errorNotification =
            $("[data-test-id='error-notification']");

    private void fillAndSubmit(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        fillAndSubmit(authInfo);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        fillAndSubmit(authInfo);
        return new VerificationPage();
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void shouldShowWrongPasswordError() {
        errorNotification
                .shouldBe(visible)
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    public void shouldShowBlockedUserError() {
        errorNotification
                .shouldBe(visible)
                .shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}
