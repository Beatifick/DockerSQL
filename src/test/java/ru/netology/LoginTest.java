package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginTest {

    @BeforeAll
    static void setup() {
        Configuration.headless = true;
        Configuration.browser = "chrome";
    }

    @BeforeEach
    void cleanDb() throws SQLException {
        DbUtils.cleanDatabase();
    }

    @Test
    void shouldLoginUsingCodeFromDatabase() throws SQLException {
        open("http://localhost:9999");

        $("[data-test-id=login] input").setValue("vasya");
        $("[data-test-id=password] input").setValue("qwerty123");
        $("[data-test-id=action-login]").click();

        String code = DbUtils.getAuthCode();

        $("[data-test-id=code] input").setValue(code);
        $("[data-test-id=action-verify]").click();

        $("h2").shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldBlockUserAfterThreeWrongPasswords() throws SQLException {
        open("http://localhost:9999");

        for (int i = 0; i < 3; i++) {
            $("[data-test-id=login] input").setValue("vasya");
            $("[data-test-id=password] input").setValue("wrong");
            $("[data-test-id=action-login]").click();
        }

        String status = DbUtils.getUserStatus("vasya");
        Assertions.assertEquals("blocked", status);
    }
}
