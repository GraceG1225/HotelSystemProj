package authenticatorapp;

import java.sql.*;

public class SQLite {

    private static final String DB_URL = "jdbc:sqlite:authenticatorapp.db";

    // Connect to the database (creates file if it doesn't exist)
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create users table
    public static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "email TEXT UNIQUE NOT NULL," +
                     "password TEXT NOT NULL" +
                     ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create admin table and insert default admin if not exists
    public static void createAdminTable() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS admins (" +
                                "id TEXT PRIMARY KEY," +
                                "password TEXT NOT NULL" +
                                ");";
        String checkAdminExistsSql = "SELECT COUNT(*) AS count FROM admins WHERE id = ?";
        String insertAdminSql = "INSERT INTO admins (id, password) VALUES (?, ?)";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);

            try (PreparedStatement pstmtCheck = conn.prepareStatement(checkAdminExistsSql)) {
                pstmtCheck.setString(1, "John123!");
                ResultSet rs = pstmtCheck.executeQuery();
                int count = rs.getInt("count");
                if (count == 0) {
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(insertAdminSql)) {
                        pstmtInsert.setString(1, "John123!");
                        pstmtInsert.setString(2, "John123!");
                        pstmtInsert.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert new user into users table will returns true if success and also  false if email exists
    public static boolean insertUser(String email, String password) {
        String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // If emailis wrong then will get false
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                return false;
            }
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Check if user exists with email and password on the login
    public static boolean checkUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            // login will work if user is found
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Check if admin exists with id and password for the adminlogin
    public static boolean checkAdmin(String id, String password) {
        String sql = "SELECT * FROM admins WHERE id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
          // will login if admin is in the databse
            return rs.next(); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
