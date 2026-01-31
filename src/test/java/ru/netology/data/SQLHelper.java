package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/appdb";
    private static final String USER = "app";
    private static final String PASS = "apppass";

    private SQLHelper() {
    }

    public static String getVerificationCode(String login) {
        var query = "SELECT code FROM auth_codes ac " +
                "JOIN users u ON u.id = ac.user_id " +
                "WHERE u.login=? " +
                "ORDER BY ac.created DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            return new QueryRunner().query(conn, query, new ScalarHandler<String>(), login);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            var runner = new QueryRunner();
            runner.update(conn, "DELETE FROM auth_codes");
            runner.update(conn, "DELETE FROM card_transactions");
            runner.update(conn, "DELETE FROM cards");
            runner.update(conn, "DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
