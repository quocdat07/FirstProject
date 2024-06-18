package DAO_UI;

import javax.swing.JOptionPane;
import java.sql.*;

public class Database {
    private static String url = "jdbc:oracle:thin:@localhost:1521:";
    
    public static String database = "QLThuVien";
    public static String user = "";
    public static String pass = "";
    public static Connection conn;

    public static Connection GetConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                if (user.equalsIgnoreCase("sys")) {
                    user += " as sysdba";
                }
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(url + database, user, pass);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Oracle JDBC driver not found", e);
            }
        }
        return conn;
    }

    public static void CloseConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            } finally {
                conn = null;
            }
        }
    }
}
