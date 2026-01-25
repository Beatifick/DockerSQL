package ru.netology;

import java.sql.*;

public class DbUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASS = "pass";

    public static void cleanDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM card_transactions");
            stmt.executeUpdate("DELETE FROM auth_codes");
            stmt.executeUpdate("DELETE FROM cards");
            stmt.executeUpdate("DELETE FROM users");
        }
    }

    public static String getAuthCode() throws SQLException {
        String sql =
                "SELECT code " + "FROM auth_codes " + "ORDER BY created DESC " + "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getString("code");
            }
            return null;
        }
    }

    public static String getUserStatus(String login) throws SQLException {
        String sql = "SELECT status FROM users WHERE login = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("status");
        }
    }
}
