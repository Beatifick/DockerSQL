package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public static final String error =
            "Неверно указан логин или пароль";
    public static final String blocked =
            "Ошибка! Пользователь заблокирован";

    private final SelenideElement loginField =
            $("[data-test-id='login'] input");
    private final SelenideElement passwordField =
            $("[data-test-id='password'] input");
    private final SelenideElement loginButton =
            $("[data-test-id='action-login']");
    private final SelenideElement errorNotification =
            $("[data-test-id='error-notification']");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        fillFields(authInfo);
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        fillFields(authInfo);
        loginButton.click();
    }

    public void shouldShowError(String expectedText) {
        errorNotification
                .shouldBe(visible)
                .shouldHave(text(expectedText));
    }

    public void fillFields(DataHelper.AuthInfo authInfo) {
        clearAndSetValue(loginField, authInfo.getLogin());
        clearAndSetValue(passwordField, authInfo.getPassword());
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    private void clearAndSetValue(SelenideElement field, String value) {
        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(Keys.BACK_SPACE);
        field.setValue(value);
    }
}
