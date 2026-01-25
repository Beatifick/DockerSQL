package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private SQLHelper() {}

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static final String URL = "jdbc:mysql://localhost:3306/appdb";
    private static final String USER = "appuser";
    private static final String PASS = "apppass";

    public static String getVerificationCode() {
        String query = "SELECT code FROM auth_codes ac " +
                "JOIN users u ON u.id = ac.user_id " +
                "WHERE u.login='vasya' ORDER BY ac.created DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            return new QueryRunner().query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearAuthCodes() {
        String query = "DELETE FROM auth_codes";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            new QueryRunner().update(conn, query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanDatabase() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            runner.update(conn, "DELETE FROM card_transactions");
            runner.update(conn, "DELETE FROM auth_codes");
            runner.update(conn, "DELETE FROM cards");
            runner.update(conn, "DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при очистке базы данных");
        }
    }
}
