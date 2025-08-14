package com.yourorg.hotel.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class ConnectionManager {
    private static final String DEFAULT_HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String DEFAULT_PORT = System.getenv().getOrDefault("DB_PORT", "3306");
    private static final String DEFAULT_DB   = System.getenv().getOrDefault("DB_NAME", "hotelreservedb");
    private static final String DEFAULT_USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String DEFAULT_PASS = System.getenv().getOrDefault("DB_PASSWORD", "password");

    // Additional JDBC parameters for stable MySQL connections.
    private static final String JDBC_PARAMS = String.join(
            "&",
            "useSSL=false",               // disable if you're on localhost; enable with proper certs in prod
            "allowPublicKeyRetrieval=true", // needed when useSSL=false on newer MySQL
            "serverTimezone=UTC",
            "characterEncoding=UTF-8",
            "autoReconnect=true",
            "rewriteBatchedStatements=true"
    );

    private static volatile boolean driverLoaded = false;

    private ConnectionManager() {}

    /**
     * Returns a new JDBC Connection. Caller is responsible for closing it.
     */
    public static Connection getConnection() throws SQLException {
        ensureDriver();
        String url = String.format("jdbc:mysql://%s:%s/%s?%s", DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DB, JDBC_PARAMS);

        Properties props = new Properties();
        props.setProperty("user", DEFAULT_USER);
        props.setProperty("password", DEFAULT_PASS);
        // Optional timeouts (in milliseconds)
        props.setProperty("connectTimeout", "8000"); // 8s
        props.setProperty("socketTimeout", "15000"); // 15s

        return DriverManager.getConnection(url, props);
    }

    /**
     * Attempts to open and immediately close a connection to verify connectivity.
     * @return true if a connection can be established, false otherwise.
     */
    public static boolean healthCheck() {
        try (Connection c = getConnection()) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static synchronized void ensureDriver() {
        if (driverLoaded) return;
        try {
            // For MySQL Connector/J 8.x, the driver auto-registers, but loading explicitly is harmless
            Class.forName("com.mysql.cj.jdbc.Driver");
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC driver not found. Add mysql-connector-j to your classpath.", e);
        }
    }

    /**
     * Quick manual test: run this class to verify DB credentials.
     */
    public static void main(String[] args) {
        System.out.println("\n[ConnectionManager] Testing MySQL connection...");
        boolean ok = healthCheck();
        System.out.println("Result: " + (ok ? "SUCCESS ✅" : "FAILED ❌"));
        if (!ok) {
            System.out.println("Check your environment variables or constants in ConnectionManager.");
            System.out.println("Expected DB: host=" + DEFAULT_HOST + ", port=" + DEFAULT_PORT + ", db=" + DEFAULT_DB);
            System.out.println("User=" + DEFAULT_USER + ", Password length=" + (DEFAULT_PASS == null ? 0 : DEFAULT_PASS.length()));
        }
    }

}
