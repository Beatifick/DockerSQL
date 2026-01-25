package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {
    private final SelenideElement dashboardHeader = $("[data-test-id='dashboard']");

    public void shouldBeVisible() {
        dashboardHeader.shouldBe(visible)
                .shouldHave(com.codeborne.selenide.Condition.text("Личный кабинет"));
    }
}
