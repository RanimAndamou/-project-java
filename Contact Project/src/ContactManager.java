import javax.swing.*; // Importe toutes les classes de la bibliothèque Swing (pour les interfaces graphiques)
import java.awt.*;// Importe les classes pour la gestion de la mise en page (layout) et les composants graphiques

import java.util.List; // Importe la classe List de la bibliothèque des collections Java

public class ContactManager extends JFrame { // Déclare la classe ContactManager qui hérite de JFrame (fenêtre principale)
    private JTextField nameField, phoneField, emailField;  // Champs de texte pour nom, téléphone et emai
    private DefaultListModel<Contact> contactListModel;   // Modèle de données pour la liste des contacts
    private JList<Contact> contactList; // Composant graphique pour afficher la liste des contacts
    private ContactDAO contactDAO; // Objet permettant d'accéder aux données des contacts

    public ContactManager() { // Constructeur de la classe ContactManager
        setTitle("Gestionnaire de Contacts"); // Définit le titre de la fenêtre
        setSize(500, 400); // Définit la taille de la fenêtre (largeur x hauteur)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme l'application quand la fenêtre est fermée
        setLocationRelativeTo(null);  // Centre la fenêtre sur l'écran

        contactDAO = new ContactDAO(); // Initialise l'objet de gestion des contacts (DAO)
        contactListModel = new DefaultListModel<>(); // Initialise l'objet de gestion des contacts (DAO)
        contactList = new JList<>(contactListModel); // Initialise la JList avec le modèle

        nameField = new JTextField(15); // crée le champ de texte pour le nom
        phoneField = new JTextField(15); // Crée un champ de texte pour le téléphone
        emailField = new JTextField(15); // Crée un champ de texte pour l'email

        // Panel des champs
        JPanel inputPanel = new JPanel(new GridLayout(3, 2)); // Crée un panneau avec une grille 3x2
        inputPanel.add(new JLabel("Nom:")); // Ajoute une étiquette "Nom"
        inputPanel.add(nameField);  // Ajoute le champ de texte pour le nom
        inputPanel.add(new JLabel("Téléphone:"));

        
        inputPanel.add(phoneField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);

        // Boutons
        JButton addButton = new JButton("Ajouter"); // Crée un bouton "Ajouter"
        JButton updateButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");
        // Action pour le bouton Ajouter
        addButton.addActionListener(e -> {
            addContact(); // Appelle la méthode pour ajouter un contact
            refreshContactList(); // Rafraîchit la liste après ajout
            clearFields();  // Vide les champs de saisie
        });
         // Action pour le bouton Modifier
        updateButton.addActionListener(e -> {
            updateContact(); // Appelle la méthode pour modifier un contact
            refreshContactList(); // Rafraîchit la liste après modification
            clearFields(); // Vide les champs de saisie
        });
        // Action pour le bouton Supprimer
        deleteButton.addActionListener(e -> {
            deleteContact(); // Appelle la méthode pour supprimer un contact
            refreshContactList(); // Rafraîchit la liste après suppression
            clearFields(); // Vide les champs de saisie
        });

        JPanel buttonPanel = new JPanel(); // Crée un panneau pour les boutons
        buttonPanel.add(addButton); // Ajoute le bouton Ajouter
        buttonPanel.add(updateButton); // Ajoute le bouton Modifier
        buttonPanel.add(deleteButton); // Ajoute le bouton Supprimer

        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Autorise la sélection d’un seul contact à la fois
        contactList.addListSelectionListener(e -> fillFieldsFromSelected());  // Remplit les champs quand un contact est sélectionné

        setLayout(new BorderLayout()); // Définit un layout de type BorderLayout pour la fenêtre
        add(inputPanel, BorderLayout.NORTH); // Place le panneau de saisie en haut
        add(new JScrollPane(contactList), BorderLayout.CENTER); // Place la liste au centre avec barre de défilement
        add(buttonPanel, BorderLayout.SOUTH); // Place les boutons en bas

        refreshContactList(); // Chargement la liste des contacts au démarrage
    }

    private void addContact() { // Ajoute un contact
        String name = nameField.getText(); // Récupère le nom
        String phone = phoneField.getText();
        String email = emailField.getText();
        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) { 
            contactDAO.addContact(new Contact(name, phone, email)); // Ajoute le contact via DAO
        } else {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.");  // Alerte si un champ est vide
        }
    }

    private void updateContact() { //modifier le contact
        Contact selected = contactList.getSelectedValue();  // Récupère le contact sélectionné
        if (selected != null) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                contactDAO.updateContact(selected.getName(), new Contact(name, phone, email)); // Met à jour le contact
            } else {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis."); // Alerte si un champ est vide
            }
        }
    }

    private void deleteContact() { //supprimer le contact
        Contact selected = contactList.getSelectedValue();
        if (selected != null) {
            contactDAO.deleteContact(selected.getName()); // Supprime via le DAO
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un contact à supprimer."); // Alerte si rien n'est sélectionné
        }
    }

    private void fillFieldsFromSelected() {  // Remplit les champs avec les infos du contact sélectionné
        Contact selected = contactList.getSelectedValue();
        if (selected != null) {
            nameField.setText(selected.getName());
            phoneField.setText(selected.getPhone());
            emailField.setText(selected.getEmail());
        }
    }

    private void refreshContactList() { // Rafraîchit le modèle de la liste avec les données du DAO
        contactListModel.clear(); // Vide la liste actuelle
        List<Contact> contacts = contactDAO.getAllContacts(); // Récupère tous les contacts
        for (Contact contact : contacts) {
            contactListModel.addElement(contact); // Ajoute chaque contact à la liste
        }
    }

    private void clearFields() { // Vide les champs de saisie
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) { // Point d’entrée du programme
        SwingUtilities.invokeLater(() -> new ContactManager().setVisible(true)); // Lance l'interface graphique de façon sûre
    }
}


