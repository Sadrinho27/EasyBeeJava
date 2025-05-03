# 🐝EasyBee - Gestion d'approvisionnement en matériel apicole

![Logo](logoEasybee.png)


## 🌍Contexte

EasyBee est une entreprise fictive spécialisée dans la vente de matériel apicole (ruches, combinaisons, pots, etc.). Face à divers problèmes rencontrés, l'entreprise a décidé d'optimiser son processus d'approvisionnement. Pour ce faire, une solution informatique sous la forme d'une application Java a été mise en place. Cette application, destinée aux salariés de l'entreprise, permet de passer des commandes d'approvisionnement auprès de l'entrepôt lorsqu'un produit est en rupture de stock en magasin.


## ⚙️Fonctionnalités 
 **Magasin** :  
  - Passer une commande pour un produit en rupture de stock.
  - Confirmer que la commande reçue est correcte.
  - Voir l'état d'une commande (En attente, En cours, Terminée).

**Entrepôt** : 
 - Voir la liste des commandes en attente.
 - Afficher les détails d'une commande pour la préparer.
 - Changer l'état d'une commande (En attente, En cours, Terminée). 


## 📋Prérequis
- Java 
- IDE compatible (Eclipse, Intellij, etc.)
- MySQL ou un autre serveur de base de données relationnelle



## 🚀Installation  
#### Base de données :
 1. Importez le fichier  *bdd.sql* dans votre serveur de base de données pour créer la structure de la base.   
 2. Importez le fichier *data.sql* pour y ajouter les données de test.  
#### Application :  
 1. Clonez ou téléchargez ce dépôt.
 2. Importez le code source dans votre IDE préféré :
    - Ouvrez votre IDE.
    - Sélectionnez l'option "Importer un projet existant" ou équivalent.
 3. Configurez les paramètres de connexion à la base de données dans le fichier *ConnexionBdd.java*.
#### Lancement :
 1. Ouvrez le fichier *pageConnexion.java*.
 2. Exécutez le code pour lancer l'application.
 3. Connectez-vous avec les identifiants présents dans les données de test. 



## 👤Auteur
Développé par [@Sadrinho27](https://github.com/Sadrinho27).

