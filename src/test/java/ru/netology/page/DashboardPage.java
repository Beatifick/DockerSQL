package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {

    private final SelenideElement heading = $("[data-test-id='code']");

    public void shouldBeVisible() {
        heading.shouldBe(visible)
                .shouldHave(text("Код из SMS или Push"));
    }
}
