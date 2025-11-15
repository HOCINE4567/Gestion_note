package models;

import database.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notation {
    private int id;
    private int etudiantId;
    private String matiere;
    private double note;
    private double coefficient;

    // Constructeur pour création
    public Notation(int etudiantId, String matiere, double note, double coefficient) {
        this.etudiantId = etudiantId;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
    }

    // Constructeur pour récupération depuis BD
    public Notation(int id, int etudiantId, String matiere, double note, double coefficient) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
    }

    // Ajouter la note dans la BD
    public boolean ajouterEnBase() throws SQLException {
        String sql = "INSERT INTO notations (etudiant_id, matiere, note, coefficient) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, etudiantId);
            pstmt.setString(2, matiere);
            pstmt.setDouble(3, note);
            pstmt.setDouble(4, coefficient);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                System.out.println("✓ Note ajoutée : " + matiere + " = " + note);
                return true;
            }
        }
        return false;
    }

    // Récupérer toutes les notes d'un étudiant
    public static List<Notation> recupererNotesEtudiant(int etudiantId) throws SQLException {
        List<Notation> notes = new ArrayList<>();
        String sql = "SELECT * FROM notations WHERE etudiant_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, etudiantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                notes.add(new Notation(
                        rs.getInt("id"),
                        rs.getInt("etudiant_id"),
                        rs.getString("matiere"),
                        rs.getDouble("note"),
                        rs.getDouble("coefficient")
                ));
            }
        }
        return notes;
    }

    @Override
    public String toString() {
        return String.format("%s : %.2f/20 (coef %.2f)", matiere, note, coefficient);
    }

    // Getters
    public int getId() { return id; }
    public int getEtudiantId() { return etudiantId; }
    public String getMatiere() { return matiere; }
    public double getNote() { return note; }
    public double getCoefficient() { return coefficient; }
}
