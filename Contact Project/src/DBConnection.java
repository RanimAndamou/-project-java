// class  hethi  bech na3mlou beha el connexion
import java.sql.Connection;
import java.sql.DriverManager; // kifech na3ml connexion
import java.sql.SQLException; //hethi nesta3mloha  bech na3rfo lifeh net3amlou m3a erreur

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ContactBD";  // URL de connexion à la base
    private static final String USER = "root"; // Utilisateur MySQL
    private static final String PASSWORD = "9hedia901"; // Mot de passe MySQL

    public static Connection getConnection() { // Méthode publique et statique pour obtenir une connexion à la base de données
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD); // Essaie de se connecter à la base de données avec les identifiants fournis
        } catch (SQLException e) { 
            e.printStackTrace(); // Affiche les détails de l'erreur SQL dans la console
            return null; // Retourne null en cas d'échec de connexion
        }
    }
}



 
