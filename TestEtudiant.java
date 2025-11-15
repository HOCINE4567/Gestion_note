package test;

import models.Etudiant;
import models.Notation;
import database.DatabaseManager;
import java.sql.SQLException;

public class TestEtudiant {
    public static void main(String[] args) {
        try {
            System.out.println("TEST DU SYSTÈME DE GESTION DES NOTES\n");

            // 1. Créer un nouvel étudiant
            System.out.println("Création d'un étudiant:");
            Etudiant etudiant = new Etudiant("Dupont", "Marie", "ETU2024001");
            etudiant.ajouterEnBase();

            // 2. Ajouter des notes
            System.out.println("\nAjout des notes:");
            Notation note1 = new Notation(etudiant.getId(), "Mathématiques", 15.5, 3.0);
            Notation note2 = new Notation(etudiant.getId(), "Physique", 14.0, 2.5);
            Notation note3 = new Notation(etudiant.getId(), "Programmation", 17.0, 4.0);
            Notation note4 = new Notation(etudiant.getId(), "Anglais", 13.5, 2.0);

            note1.ajouterEnBase();
            note2.ajouterEnBase();
            note3.ajouterEnBase();
            note4.ajouterEnBase();

            // 3. Calculer et mettre à jour la moyenne
            System.out.println("\nCalcul de la moyenne:");
            etudiant.calculerMoyenne();
            System.out.println("Moyenne calculée : " + String.format("%.2f", etudiant.getMoyenne()));

            // 4. Afficher les informations complètes
            System.out.println("\nAffichage des informations depuis la base:");
            Etudiant etudiantRecupere = Etudiant.recupererParNumero("ETU2024001");
            if (etudiantRecupere != null) {
                etudiantRecupere.afficherInfos();
            }

            System.out.println("TEST TERMINÉ AVEC SUCCÈS.");

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseManager.closeConnection();
        }
    }
}
