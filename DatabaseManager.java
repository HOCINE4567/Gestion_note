package database;

import java.sql.*;

public class DatabaseManager {
    // 👇 MODIFIE ces valeurs si nécessaire
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_notes";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Vide par défaut, change si besoin

    private static Connection connection = null;

    // Obtenir la connexion (singleton)
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✓ Connexion à la base de données réussie");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC introuvable", e);
            }
        }
        return connection;
    }

    // Fermer la connexion
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Connexion fermée");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}