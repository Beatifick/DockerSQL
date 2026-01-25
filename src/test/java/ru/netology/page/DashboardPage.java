package ru.netology.page;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private final static String DASHBOARD_SELECTOR = "[data-test-id='dashboard']";

    public void shouldBeVisible() {
        $(DASHBOARD_SELECTOR).shouldBe(visible);
    }
}
