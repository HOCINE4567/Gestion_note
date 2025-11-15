# 📚 Système de Gestion des Notes d'Étudiants

Application Java de gestion des notes d'étudiants avec base de données MySQL.

## 🎯 Fonctionnalités

- ✅ Ajout d'étudiants dans la base de données
- ✅ Gestion des notes par matière avec coefficients
- ✅ Calcul automatique de la moyenne pondérée
- ✅ Affichage des informations complètes
- ✅ Architecture MVC avec JDBC

## 🛠️ Technologies

- Java (JDK 8+)
- MySQL / MariaDB
- JDBC (MySQL Connector/J)

## 🚀 Installation

### 1. Base de données

Exécute ce script SQL dans phpMyAdmin :
```sql
CREATE DATABASE gestion_notes;
USE gestion_notes;

CREATE TABLE etudiants (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    numero_etudiant VARCHAR(50) UNIQUE NOT NULL,
    moyenne DECIMAL(5,2) DEFAULT 0.0
);

CREATE TABLE notations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    etudiant_id INT NOT NULL,
    matiere VARCHAR(100) NOT NULL,
    note DECIMAL(5,2) NOT NULL,
    coefficient DECIMAL(3,2) DEFAULT 1.0,
    FOREIGN KEY (etudiant_id) REFERENCES etudiants(id) ON DELETE CASCADE
);
```

### 2. Configuration:

Modifie les credentials dans `database/DatabaseManager.java` :
```java
private static final String USER = "root";
private static final String PASSWORD = "ton_mot_de_passe";
```

### 3. Lancer:

Exécute la classe `test/TestEtudiant.java`

## 📂 Structure:
```
src/
├── database/
│   └── DatabaseManager.java
├── models/
│   ├── Etudiant.java
│   └── Notation.java
└── test/
    └── TestEtudiant.java
```

## 👤 Fait par :

YAHYA KAMAL / HOCINE REZAOUI 