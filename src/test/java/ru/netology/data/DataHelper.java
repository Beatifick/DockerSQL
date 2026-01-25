package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getValidUser() {
        // Пароль берем такой, какой хранится в базе (зашифрованный)
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidPasswordUser() {
        return new AuthInfo("vasya", "wrongPass");
    }
}
