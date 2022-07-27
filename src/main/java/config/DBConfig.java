package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {

    private static final Connection connection;

    static {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=article", "postgres", "@MIRr@9137#!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DBConfig() throws SQLException {
    }

    public static Connection getConnection() {
        return connection;
    }
}
