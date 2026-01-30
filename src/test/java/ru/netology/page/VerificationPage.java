package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement heading = $("[data-test-id='code']");

    public void shouldBeOnVerificationPage() {
        heading.shouldBe(visible).shouldHave(text("Код из SMS или Push"));
        codeField.shouldBe(visible);
    }

    public void shouldBeVisible() {
        codeField.shouldBe(visible);
    }
}
