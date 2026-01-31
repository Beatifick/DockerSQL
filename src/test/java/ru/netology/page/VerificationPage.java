package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public void verifyWithCode(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
    }

    public void shouldBeVisible() {
        codeField.shouldBe(com.codeborne.selenide.Condition.visible);
    }
}
