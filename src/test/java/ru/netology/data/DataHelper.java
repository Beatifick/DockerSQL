package ru.netology.data;

import lombok.Value;

import java.sql.*;

public class DataHelper {

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static AuthInfo getValidUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidPasswordUser() {
        return new AuthInfo("vasya", "wrongPass");
    }

    public static VerificationCode getVerificationCodeFromDB() {
        String url = "jdbc:mysql://localhost:3306/appdb";
        String user = "appuser";
        String pass = "apppass";
        String code = "";
        String query = "SELECT code FROM auth_codes ac " +
                "JOIN users u ON u.id = ac.user_id " +
                "WHERE u.login='vasya' " +
                "ORDER BY ac.created DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                code = rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new VerificationCode(code);
    }
}
