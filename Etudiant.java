package models;

import database.DatabaseManager;
import java.sql.*;
import java.util.List;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private String numeroEtudiant;
    private double moyenne;

    // Constructeur pour création
    public Etudiant(String nom, String prenom, String numeroEtudiant) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.moyenne = 0.0;
    }

    // Constructeur avec ID (pour récupération depuis BD)
    public Etudiant(int id, String nom, String prenom, String numeroEtudiant, double moyenne) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.moyenne = moyenne;
    }

    // Ajouter l'étudiant dans la BD
    public boolean ajouterEnBase() throws SQLException {
        String sql = "INSERT INTO etudiants (nom, prenom, numero_etudiant, moyenne) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, numeroEtudiant);
            pstmt.setDouble(4, moyenne);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Récupérer l'ID généré
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                System.out.println("✓ Étudiant ajouté : " + nom + " " + prenom);
                return true;
            }
        }
        return false;
    }

    // Récupérer un étudiant depuis la BD par son numéro
    public static Etudiant recupererParNumero(String numeroEtudiant) throws SQLException {
        String sql = "SELECT * FROM etudiants WHERE numero_etudiant = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, numeroEtudiant);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Etudiant(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("numero_etudiant"),
                        rs.getDouble("moyenne")
                );
            }
        }
        return null;
    }

    // Mettre à jour la moyenne dans la BD
    public boolean mettreAJourMoyenne() throws SQLException {
        String sql = "UPDATE etudiants SET moyenne = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, moyenne);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;
        }
    }

    // Calculer la moyenne depuis les notes de la BD
    public void calculerMoyenne() throws SQLException {
        List<Notation> notes = Notation.recupererNotesEtudiant(this.id);

        if (notes.isEmpty()) {
            this.moyenne = 0.0;
            return;
        }

        double sommeNotes = 0;
        double sommeCoefficients = 0;

        for (Notation note : notes) {
            sommeNotes += note.getNote() * note.getCoefficient();
            sommeCoefficients += note.getCoefficient();
        }

        this.moyenne = sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0.0;
        mettreAJourMoyenne();
    }

    // Afficher les informations complètes
    public void afficherInfos() throws SQLException {
        System.out.println("\n========== INFORMATIONS ÉTUDIANT ==========");
        System.out.println("Nom complet : " + prenom + " " + nom);
        System.out.println("Numéro étudiant : " + numeroEtudiant);
        System.out.println("Moyenne générale : " + String.format("%.2f", moyenne));

        List<Notation> notes = Notation.recupererNotesEtudiant(this.id);
        System.out.println("\n--- Notes détaillées ---");
        for (Notation note : notes) {
            System.out.println(note);
        }
        System.out.println("==========================================\n");
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNumeroEtudiant() { return numeroEtudiant; }
    public double getMoyenne() { return moyenne; }
}