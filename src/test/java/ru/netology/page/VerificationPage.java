package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement verificationForm = $("[data-test-id='verification']");
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public void validVerify(String code) {
        verificationForm.shouldBe(visible); // ждем видимости формы
        codeField.setValue(code);
        verifyButton.click();
    }

    public void shouldBeVisible() {
        verificationForm.shouldBe(visible);
    }
}
