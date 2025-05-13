//class hethi t5os my sql  bech na3mlou modification w suppression mise a jour fi west el base
import java.sql.*; // Importe toutes les classes nécessaires pour interagir avec une base de données SQL (Connection, Statement, ResultSet, etc.)
import java.util.ArrayList; // Importe la classe ArrayList pour stocker les contacts
import java.util.List; // Importe l'interface List, utilisée pour retourner une liste de contacts

public class ContactDAO { // Déclaration de la classe ContactDAO, responsable de la gestion des contacts dans la base de données

    // Ajouter un contact dans la base de données
    public void addContact(Contact contact) {
        String sql = "INSERT INTO contacts (name, phone, email) VALUES (?, ?, ?)"; // Requête SQL pour insérer un nouveau contact avec des valeurs paramétrées (sécurisé contre les injections SQL)
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Ouvre une connexion à la base de données et prépare la requête SQL
            stmt.setString(1, contact.getName()); // Définit le 1er paramètre avec le nom
            stmt.setString(2, contact.getPhone()); //Définit le 2ème paramètre avec le téléphone
            stmt.setString(3, contact.getEmail()); // Définit le 3ème paramètre avec l'email
            stmt.executeUpdate(); // Exécute la requête d'insertion
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l'erreur SQL s'il y a un problème
        }
    }

    // Récupérer tous les contacts de la base de données
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>(); // Liste qui contiendra tous les contacts récupérés
        String sql = "SELECT * FROM contacts"; // Requête SQL pour récupérer toutes les lignes de la table "contacts"
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // Crée une connexion, un statement, et exécute la requête (résultat dans ResultSet)
            while (rs.next()) { // Parcourt chaque ligne du résultat bech tjib les contacts ml base w n7othom fel interface
                contacts.add(new Contact( // Crée un nouvel objet Contact avec les valeurs récupérées
                        rs.getString("name"), // Récupère la colonne "name"
                        rs.getString("phone"),  // Récupère la colonne "phone"
                        rs.getString("email") // Récupère la colonne "email"
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche l’erreur SQL si une exception est levée
        }
        return contacts; // Retourne la liste complète des contacts
    }

    // Supprimer un contact de la base de données
    public void deleteContact(String name) {
        String sql = "DELETE FROM contacts WHERE name = ?";  // Requête SQL pour supprimer un contact à partir de son nom
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name); // Définit le paramètre "name"
            stmt.executeUpdate(); // Exécute la suppression
        } catch (SQLException e) {
            e.printStackTrace(); // Affiche une erreur s'il y en a une
        }
    }

    // Mettre à jour un contact dans la base de données
    public void updateContact(String oldName, Contact newContact) {
        String sql = "UPDATE contacts SET name = ?, phone = ?, email = ? WHERE name = ?"; // Requête SQL pour modifier les informations d'un contact existant, identifié par son ancien nom
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newContact.getName()); // n7ot Nouveau nom
            stmt.setString(2, newContact.getPhone()); // Nouveau phone
            stmt.setString(3, newContact.getEmail()); // Nouveau email
            stmt.setString(4, oldName); // condition where : nom d'origine
            stmt.executeUpdate(); // exécute la requete de mise a jour
        } catch (SQLException e) {
            e.printStackTrace(); // affiche les erreurs SQL
        }
    }
}



